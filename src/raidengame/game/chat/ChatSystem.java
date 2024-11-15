package raidengame.game.chat;

// Imports
import raidengame.configuration.*;
import raidengame.connection.base.PacketRetcodes;
import raidengame.game.player.Player;
import raidengame.misc.WordFilter;
import raidengame.misc.classes.Timestamp;
import raidengame.server.GameServer;
import lombok.Getter;
import java.util.*;
import java.util.regex.Pattern;

// Events
import raidengame.server.events.player.PlayerChatEvent;

// Packets
import raidengame.connection.packets.send.game.chat.PacketPrivateChatNotify;
import raidengame.connection.packets.send.game.chat.PacketPrivateChatRsp;
import raidengame.connection.packets.send.game.chat.PacketPullPrivateChatRsp;
import raidengame.connection.packets.send.game.chat.PacketPullRecentChatRsp;

// Protocol buffers
import raidengame.cache.protobuf.ChatInfoOuterClass.ChatInfo;

public class ChatSystem implements ChatSystemHandler {
    @Getter final GameServer server;
    private final Map<Integer, Map<Integer, List<ChatInfo>>> history = new HashMap<>();

    /**
     * Creates an instance of ChatSystem.
     * @param server The game server.
     */
    public ChatSystem(final GameServer server) {
        this.server = server;
    }

    /**
     * Adds a message in player's history.
     * @param uid The sender.
     * @param partnerId The receiver.
     * @param info The message.
     */
    private void addMessageInChatHistory(int uid, int partnerId, ChatInfo info) {
        this.history.computeIfAbsent(uid, _ -> new HashMap<>()).computeIfAbsent(partnerId, _ -> new ArrayList<>()).add(info);
    }

    /**
     * Creates a new message (instance of ChatInfo protobuf).
     * @param sender The person who is going to send.
     * @param receiver The person who is going to receive.
     * @param icon The icon/emote.
     * @return ChatInfo instance.
     */
    private ChatInfo createChatMessage(int sender, int receiver, int icon) {
        return ChatInfo.newBuilder()
                .setTime(Timestamp.getCurrentSeconds())
                .setUid(sender)
                .setToUid(receiver)
                .setIcon(icon)
                .build();
    }

    /**
     * Creates a new message (instance of ChatInfo protobuf).
     * @param sender The person who is going to send.
     * @param receiver The person who is going to receive.
     * @param message The message.
     * @return ChatInfo instance.
     */
    private ChatInfo createChatMessage(int sender, int receiver, String message) {
        return ChatInfo.newBuilder()
                .setTime(Timestamp.getCurrentSeconds())
                .setUid(sender)
                .setToUid(receiver)
                .setText(message)
                .build();
    }

    /**
     * Gets the chat history from given player.
     * @param player The given player.
     * @return The chat history.
     */
    public List<ChatInfo> getChatHistory(Player player, int targetId) {
        return this.history.get(player.getUid()).get(targetId);
    }

    /**
     * Handles the commands.
     * @param sender The sender.
     * @param target The target.
     * @param rawMessage The command.
     */
    private void handleCommand(Player sender, Player target, String rawMessage) {
        if (!Pattern.compile("[/!]").matcher(rawMessage.substring(0, 1)).matches()) {
            return;
        }

        for (String line : rawMessage.substring(1).split("\n[/!]"))
            CommandMap.getInstance().invoke(sender, target, line);
    }

    /**
     * Pulls the recent chat history.
     * @param player The player.
     * @param fromSequence The chat sequence.
     * @param pullNum Total pulls.
     */
    @Override
    public void handlePullRecentChatReq(Player player, int fromSequence, int pullNum) {
        if(this.isChatHistoryWithServerEmpty(player)) {
            this.sendPrivateMessageFromServer(player.getUid(), GameConstants.welcomeMessage);
        }

        var playerhist = this.history.get(player.getUid());
        List<ChatInfo> unreadMessages = new ArrayList<>();
        if(playerhist.isEmpty()) {
            return;
        }

        if(pullNum + fromSequence > playerhist.size()) {
            pullNum = playerhist.size() + fromSequence;
        }

        int finalPullNum = pullNum;
        playerhist.forEach((_, chatInfoList) -> {
            List<ChatInfo> lastUnreadElements = chatInfoList.stream()
                    .filter(chatInfo -> !chatInfo.getIsRead())
                    .skip(Math.max(0, chatInfoList.stream().filter(chatInfo -> !chatInfo.getIsRead()).count() - finalPullNum))
                    .toList();

            unreadMessages.addAll(lastUnreadElements);
        });

        player.sendPacket(new PacketPullRecentChatRsp(unreadMessages));
    }

    /**
     * Pulls the recent private chat history.
     * @param player The player.
     * @param targetId The id of the other player.
     * @param fromSequence The chat sequence.
     * @param pullNum Total pulls.
     */
    @Override
    public void handlePullPrivateChatReq(Player player, int targetId, int fromSequence, int pullNum) {
        if(!player.getFriendsList().isFriendsWith(targetId) && targetId != ServerBot.UID) {
            player.sendPacket(new PacketPullPrivateChatRsp(null, PacketRetcodes.RET_PRIVATE_CHAT_READ_NOT_FRIEND));
            return;
        }

        var chatHistory = this.history.computeIfAbsent(player.getUid(), _ -> new HashMap<>()).computeIfAbsent(targetId, _ -> new ArrayList<>());
        if(chatHistory.isEmpty()) {
            return;
        }

        if (pullNum + fromSequence > chatHistory.size()) {
            pullNum = chatHistory.size() - fromSequence;
        }

        player.sendPacket(new PacketPullPrivateChatRsp(chatHistory.subList(fromSequence, fromSequence + pullNum), PacketRetcodes.RETCODE_SUCC));
    }

    /**
     * Checks if the chat history from given player is empty.
     * @param player The given player.
     * @return True if chat history is empty.
     */
    public boolean isChatHistoryEmpty(Player player, int targetId) {
        return !this.history.computeIfAbsent(player.getUid(), _ -> new HashMap<>()).containsKey(targetId);
    }

    /**
     * Checks if the chat history from given player is empty with the server account.
     * @param player The given player.
     * @return True if chat history is empty.
     */
    private boolean isChatHistoryWithServerEmpty(Player player) {
        return this.isChatHistoryEmpty(player, ServerBot.UID);
    }

    /**
     * Sends a chat message from server to player.
     * @param targetUid The player.
     * @param message The message.
     */
    @Override
    public void sendPrivateMessageFromServer(int targetUid, String message) {
        Player target = this.server.getPlayerByUid(targetUid);
        if (target == null || message.isEmpty()) {
            return;
        }

        ChatInfo msg = this.createChatMessage(ServerBot.UID, targetUid, message);
        this.addMessageInChatHistory(targetUid, ServerBot.UID, msg);
        target.sendPacket(new PacketPrivateChatNotify(msg));
    }

    /**
     * Sends a chat message.
     * @param player Sender.
     * @param targetUid Receiver.
     * @param message The message.
     */
    @Override
    public void sendPrivateMessage(Player player, int targetUid, String message) {
        Player target = this.server.getPlayerByUid(targetUid);
        if(targetUid == ServerBot.UID) {
            // Sends a message.
            ChatInfo msg = this.createChatMessage(player.getUid(), ServerBot.UID, message);
            player.sendPacket(new PacketPrivateChatNotify(msg));
            this.addMessageInChatHistory(player.getUid(), targetUid, msg);

            // response
            player.sendPacket(new PacketPrivateChatRsp(0, PacketRetcodes.RETCODE_SUCC));

            // Handle the commands.
            this.handleCommand(player, target, message);
            return;
        }

        // target is not found or message is empty.
        if(target == null || message.isEmpty()) {
            player.sendPacket(new PacketPrivateChatRsp(0, PacketRetcodes.RETCODE_FAIL));
            return;
        }

        // the message is too long.
        if(message.length() > GameConstants.CHAT_MESSAGE_MAX_SIZE) {
            player.sendPacket(new PacketPrivateChatRsp(0, PacketRetcodes.RET_PRIVATE_CHAT_CONTENT_TOO_LONG));
            return;
        }

        // player is timeout
        if(player.getChatRestrictionTime() > 0) {
            player.sendPacket(new PacketPrivateChatRsp(player.getChatRestrictionTime(), PacketRetcodes.RET_CHAT_FORBIDDEN));
            return;
        }

        // they can't chat if they are not friends
        if(!player.getFriendsList().isFriendsWith(targetUid)) {
            player.sendPacket(new PacketPrivateChatRsp(0, PacketRetcodes.RET_PRIVATE_CHAT_TARGET_IS_NOT_FRIEND));
            return;
        }

        // bad word.
        if(WordFilter.checkIsBadWord(message)) {
            player.sendPacket(new PacketPrivateChatRsp(0, PacketRetcodes.RET_RPIVATE_CHAT_INVALID_CONTENT_TYPE));
            return;
        }

        // cooldown timer.
        if(Timestamp.getCurrentSeconds() - player.getChatCurrentTimestamp() < ConfigManager.serverConfig.gameInfo.chatCooldown) {
            player.sendPacket(new PacketPrivateChatRsp(0, PacketRetcodes.RET_CHAT_FREQUENTLY));
            return;
        }
        else {
            player.setChatCurrentTimestamp(Timestamp.getCurrentSeconds());
        }

        // invoke the chat event
        var event = new PlayerChatEvent(player, message, target);
        event.call();
        if (event.isCanceled()) {
            player.sendPacket(new PacketPrivateChatRsp(0, PacketRetcodes.RETCODE_FAIL));
            return;
        }

        // packet to send the message.
        ChatInfo msg = this.createChatMessage(player.getUid(), event.getTargetUid(), event.getMessage());
        player.sendPacket(new PacketPrivateChatNotify(msg));
        target.sendPacket(new PacketPrivateChatNotify(msg));

        // history it.
        this.addMessageInChatHistory(player.getUid(), targetUid, msg);
        this.addMessageInChatHistory(targetUid, player.getUid(), msg);

        // response
        player.sendPacket(new PacketPrivateChatRsp(0, PacketRetcodes.RETCODE_SUCC));
    }

    /**
     * Sends a chat message.
     * @param player Sender.
     * @param targetUid Receiver.
     * @param icon The icon/emote.
     */
    @Override
    public void sendPrivateMessage(Player player, int targetUid, int icon) {
        if(targetUid == ServerBot.UID) {
            player.sendPacket(new PacketPrivateChatRsp(0, PacketRetcodes.RET_PRIVATE_CHAT_CONTENT_NOT_SUPPORTED));
            return;
        }

        Player target = this.server.getPlayerByUid(targetUid);
        if(target == null) {
            player.sendPacket(new PacketPrivateChatRsp(0, PacketRetcodes.RETCODE_FAIL));
            return;
        }

        // they can't chat if they are not friends
        if(!player.getFriendsList().isFriendsWith(targetUid)) {
            player.sendPacket(new PacketPrivateChatRsp(0, PacketRetcodes.RET_PRIVATE_CHAT_TARGET_IS_NOT_FRIEND));
            return;
        }

        // player is timeout
        if(player.getChatRestrictionTime() > 0) {
            player.sendPacket(new PacketPrivateChatRsp(player.getChatRestrictionTime(), PacketRetcodes.RET_CHAT_FORBIDDEN));
            return;
        }

        // cooldown timer.
        if(Timestamp.getCurrentSeconds() - player.getChatCurrentTimestamp() < ConfigManager.serverConfig.gameInfo.chatCooldown) {
            player.sendPacket(new PacketPrivateChatRsp(0, PacketRetcodes.RET_CHAT_FREQUENTLY));
            return;
        }
        else {
            player.setChatCurrentTimestamp(Timestamp.getCurrentSeconds());
        }

        // invoke the chat event
        var event = new PlayerChatEvent(player, icon, target);
        event.call();
        if (event.isCanceled()) {
            player.sendPacket(new PacketPrivateChatRsp(0, PacketRetcodes.RETCODE_FAIL));
            return;
        }

        // packet to send the message.
        ChatInfo msg = this.createChatMessage(player.getUid(), event.getTargetUid(), event.getMessageAsInt());
        player.sendPacket(new PacketPrivateChatNotify(msg));
        target.sendPacket(new PacketPrivateChatNotify(msg));

        // history it.
        this.addMessageInChatHistory(player.getUid(), targetUid, msg);
        this.addMessageInChatHistory(targetUid, player.getUid(), msg);

        // response
        player.sendPacket(new PacketPrivateChatRsp(0, PacketRetcodes.RETCODE_SUCC));
    }
}
