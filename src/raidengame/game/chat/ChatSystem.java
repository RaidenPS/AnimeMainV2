package raidengame.game.chat;

// Imports
import raidengame.configuration.ConfigManager;
import raidengame.configuration.GameConstants;
import raidengame.connection.base.PacketRetcodes;
import raidengame.game.player.Player;
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
     * Gets the chat history from given player with the server account.
     * @param player The given player.
     * @return The chat history.
     */
    private List<ChatInfo> getChatHistoryWithServer(Player player) {
        return this.getChatHistory(player, ServerBot.UID);
    }

    /**
     * Handles the commands.
     * @param sender The sender.
     * @param target The target.
     * @param rawMessage The command.
     */
    private void handleCommand(Player sender, Player target, String rawMessage) {
        if (!Pattern.compile("[/!]").matcher(rawMessage.substring(0, 1)).matches()) {
            // not a command, skip.
            return;
        }

        /// Todo: Finish handleCommand
    }

    /**
     * Pulls the recent chat history.
     * @param player The player.
     * @param seq The chat sequence.
     * @param pull_num Total pulls.
     */
    @Override
    public void handlePullRecentChatReq(Player player, int seq, int pull_num) {
        if(this.isChatHistoryWithServerEmpty(player)) {
            this.sendPrivateMessageFromServer(player.getUid(), GameConstants.welcomeMessage);
        }

        List<ChatInfo> chatHistory = this.getChatHistoryWithServer(player);
        int chatHistorySize = chatHistory.size();
        player.sendPacket(new PacketPullRecentChatRsp(chatHistory.subList(Math.max(chatHistorySize - 3, 0), chatHistorySize)));
    }

    /**
     * Pulls the recent private chat history.
     * @param player The player.
     * @param partnerId The id of the other player.
     * @param seq The chat sequence.
     * @param pull_num Total pulls.
     */
    @Override
    public void handlePullPrivateChatReq(Player player, int partnerId, int seq, int pull_num) {
        var chatHistory = this.history.computeIfAbsent(player.getUid(), _ -> new HashMap<>()).computeIfAbsent(partnerId, _ -> new ArrayList<>());
        player.sendPacket(new PacketPullPrivateChatRsp(chatHistory));
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
            // Handle the commands.
            this.handleCommand(player, target, message);

            // Sends a message.
            ChatInfo msg = this.createChatMessage(player.getUid(), ServerBot.UID, message);
            player.sendPacket(new PacketPrivateChatNotify(msg));
            this.addMessageInChatHistory(player.getUid(), targetUid, msg);

            // response
            player.sendPacket(new PacketPrivateChatRsp(0, PacketRetcodes.RETCODE_SUCC));
            return;
        }

        // target is not found or message is empty.
        if(target == null || message.isEmpty()) {
            player.sendPacket(new PacketPrivateChatRsp(0, PacketRetcodes.RETCODE_FAIL));
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
        Player target = this.server.getPlayerByUid(targetUid);
        if(target == null || targetUid == ServerBot.UID) {
            player.sendPacket(new PacketPrivateChatRsp(0, PacketRetcodes.RETCODE_FAIL));
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
