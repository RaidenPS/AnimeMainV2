package raidengame.game.player.social;

// Imports
import raidengame.Main;
import raidengame.configuration.GameConstants;
import raidengame.connection.base.PacketRetcodes;
import raidengame.database.DatabaseHelper;
import raidengame.enums.game.social.DealAddFriendResultType;
import raidengame.game.chat.ServerBot;
import raidengame.game.player.BasePlayerManager;
import raidengame.game.player.Player;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import lombok.Getter;

// Packets
import raidengame.connection.packets.send.game.social.PacketAddBlacklistRsp;
import raidengame.connection.packets.send.game.social.PacketAskAddFriendNotify;
import raidengame.connection.packets.send.game.social.PacketAskAddFriendRsp;
import raidengame.connection.packets.send.game.social.PacketDealAddFriendRsp;
import raidengame.connection.packets.send.game.social.PacketDeleteFriendNotify;
import raidengame.connection.packets.send.game.social.PacketDeleteFriendRsp;
import raidengame.connection.packets.send.game.social.PacketRemoveBlacklistRsp;

public class SocialList extends BasePlayerManager {
    @Getter private final Int2ObjectMap<SocialObject> friends;
    @Getter private final Int2ObjectMap<SocialObject> pendingFriends;
    @Getter private final Int2ObjectMap<SocialObject> blockedPeople;
    private boolean loadDB = false;

    /**
     * Creates a new instance of SocialList.
     * @param player The given player.
     */
    public SocialList(Player player) {
        super(player);

        this.friends = new Int2ObjectOpenHashMap<>();
        this.pendingFriends = new Int2ObjectOpenHashMap<>();
        this.blockedPeople = new Int2ObjectOpenHashMap<>();
    }

    /**
     * Fetches a friend object, type friend by id.
     * @param id The player id.
     * @return SocialObject object.
     */
    public synchronized SocialObject getFriendById(int id) {
        return this.friends.get(id);
    }

    /**
     * Fetches a pendingFriend object by id.
     * @param id The player id.
     * @return SocialObject object.
     */
    private synchronized SocialObject getPendingFriendById(int id) {
        return this.pendingFriends.get(id);
    }

    /**
     * Fetches a blockedPerson object by id.
     * @param id The player id.
     * @return SocialObject object.
     */
    private synchronized SocialObject getBlockedPeopleById(int id) {
        return this.blockedPeople.get(id);
    }

    /**
     * Gets the amount of blocked players.
     * @return The total amount of blocked players.
     */
    public synchronized int getTotalBlockedPlayers() {
        return this.blockedPeople.size();
    }

    /**
     * Gets the total amount of friends.
     * @return The total amount of friends.
     */
    public synchronized int getTotalFriends() {
        return this.friends.size();
    }

    /**
     * Gets the total amount of friend requests.
     * @return The total amount of friend requests.
     */
    public synchronized int getTotalPendingFriends() {
        return this.pendingFriends.size();
    }

    /**
     * Checks if the current player has blocked another player.
     * @param uid The id.
     * @return True if current player blocked him.
     */
    public synchronized boolean isBlockedWith(int uid) {
        return this.blockedPeople.containsKey(uid);
    }

    /**
     * Checks if the current player is friended another player.
     * @param uid The id.
     * @return True if they are friends.
     */
    public synchronized boolean isFriendsWith(int uid) {
        return this.friends.containsKey(uid);
    }

    /**
     * Checks if the current player is pending friended another player.
     * @param uid The id.
     * @return True if they are pending friends.
     */
    public synchronized boolean isPendingFriendsWith(int uid) {return this.pendingFriends.containsKey(uid);}

    /**
     * Handler for sending friend requests.
     * @param targetUid The player to receive it.
     */
    public synchronized void handleSendFriendRequest(int targetUid) {
        Player target = this.player.getSession().getServer().getPlayerByUid(targetUid, true);
        if(targetUid == ServerBot.UID) {
            // Attempt to send friend request to the console.
            this.player.sendPacket(new PacketAskAddFriendRsp(targetUid, PacketRetcodes.RET_CANNOT_ADD_TARGET_FRIEND));
            return;
        }

        if(target == null) {
            // The player is not found
            this.player.sendPacket(new PacketAskAddFriendRsp(targetUid, PacketRetcodes.RET_PLAYER_NOT_EXIST));
            return;
        }

        if(this.player == target) {
            // You cant add yourself.
            this.player.sendPacket(new PacketAskAddFriendRsp(targetUid, PacketRetcodes.RET_CANNOT_ADD_SELF_FRIEND));
            return;
        }

        if(this.isBlockedWith(targetUid)) {
            // The player is blocked, cant send friend request.
            this.player.sendPacket(new PacketAskAddFriendRsp(targetUid, PacketRetcodes.RET_BLACKLIST_PLAYER_CANNOT_ADD_FRIEND));
            return;
        }

        if(this.pendingFriends.containsKey(targetUid)) {
            // You already sent a friend request.
            this.player.sendPacket(new PacketAskAddFriendRsp(targetUid, PacketRetcodes.RET_ALREADY_SENT_ADD_REQUEST));
            return;
        }

        if(this.isFriendsWith(targetUid)) {
            // Attempt to send friend request to already existing friend.
            this.player.sendPacket(new PacketAskAddFriendRsp(targetUid, PacketRetcodes.RET_PLAYER_ALREADY_IS_FRIEND));
            return;
        }

        if(this.getTotalFriends() > GameConstants.friendsMax) {
            // You have maximum friends.
            this.player.sendPacket(new PacketAskAddFriendRsp(targetUid, PacketRetcodes.RET_FRIEND_COUNT_EXCEEDED));
            return;
        }

        if(target.getFriendsList().getTotalFriends() > GameConstants.friendsMax) {
            // The target has maximum friends.
            this.player.sendPacket(new PacketAskAddFriendRsp(targetUid, PacketRetcodes.RET_TARGET_FRIEND_COUNT_EXCEED));
            return;
        }

        if(target.getFriendsList().getTotalPendingFriends() > GameConstants.friendRequestsMax || this.getTotalPendingFriends() > GameConstants.friendRequestsMax) {
            // The target has max friend requests.
            this.player.sendPacket(new PacketAskAddFriendRsp(targetUid, PacketRetcodes.RET_ASK_FRIEND_LIST_FULL));
            return;
        }

        SocialObject myFriendship = new SocialObject(this.player, target, this.player);
        SocialObject theirFriendship = new SocialObject(target, this.player, this.player);
        this.pendingFriends.put(myFriendship.getFriendId(), myFriendship);
        if (target.isOnline() && target.getFriendsList().loadDB) {
            target.getFriendsList().getPendingFriends().put(theirFriendship.getFriendId(), theirFriendship);
            target.sendPacket(new PacketAskAddFriendNotify(theirFriendship));
        }

        myFriendship.saveDatabase();
        theirFriendship.saveDatabase();

        this.player.sendPacket(new PacketAskAddFriendRsp(targetUid, PacketRetcodes.RETCODE_SUCC));
    }

    /**
     * Handler for deciding the friend request.
     * @param targetUid The target id.
     * @param result Deny or Accept it.
     */
    public synchronized void handleFriendRequest(int targetUid, DealAddFriendResultType result) {
        SocialObject myFriendship = this.getPendingFriendById(targetUid);
        Player target = this.player.getSession().getServer().getPlayerByUid(targetUid, true);
        if(this.isFriendsWith(targetUid) || myFriendship == null || target == null) { //  myFriendship.getAskerId() == this.player.getUid()
            this.player.sendPacket(new PacketDealAddFriendRsp(targetUid, result, PacketRetcodes.RET_PLAYER_NOT_ASK_FRIEND));
            return;
        }

        SocialObject theirFriendship = (target.isOnline()) ? target.getFriendsList().getPendingFriendById(this.getPlayer().getUid()) : DatabaseHelper.getReverseFriendship(myFriendship);
        if (theirFriendship == null) {
            this.pendingFriends.remove(myFriendship.getOwnerId());
            myFriendship.delete();
            this.player.sendPacket(new PacketDealAddFriendRsp(targetUid, result, PacketRetcodes.RET_PLAYER_NOT_ASK_FRIEND));
            return;
        }

        switch(result) {
            case DealAddFriendResultType.ACCEPT:
                myFriendship.setFriend(true);
                theirFriendship.setFriend(true);

                this.pendingFriends.remove(myFriendship.getOwnerId());
                this.friends.put(myFriendship.getFriendId(), myFriendship);

                if (target.isOnline()) {
                    target.getFriendsList().pendingFriends.remove(this.getPlayer().getUid());
                    target.getFriendsList().friends.put(theirFriendship.getFriendId(), theirFriendship);
                }

                myFriendship.saveDatabase();
                theirFriendship.saveDatabase();
                break;
            case DealAddFriendResultType.REJECT:
                this.pendingFriends.remove(myFriendship.getOwnerId());
                myFriendship.delete();

                if (target.isOnline()) {
                    theirFriendship = target.getFriendsList().getPendingFriendById(this.getPlayer().getUid());
                }
                theirFriendship.delete();
                break;
            default:
                Main.getLogger().warn("[SocialList] Unhandled deal friends result: " + result);
                break;
        }

        this.player.sendPacket(new PacketDealAddFriendRsp(targetUid, result, PacketRetcodes.RETCODE_SUCC));
    }

    /**
     * Handler for remove friend.
     * @param targetUid The player to remove from friend list.
     */
    public synchronized void handleRemoveFriend(int targetUid) {
        SocialObject myFriendship = this.getFriendById(targetUid);
        if (myFriendship == null || targetUid == ServerBot.UID) {
            this.player.sendPacket(new PacketDeleteFriendRsp(targetUid, PacketRetcodes.RET_NOT_FRIEND));
            return;
        }

        this.friends.remove(targetUid);
        myFriendship.delete();

        SocialObject theirFriendship;
        Player friend = myFriendship.getFriendProfile().getPlayer();
        if (friend != null) {
            theirFriendship = friend.getFriendsList().getFriendById(this.player.getUid());
            if (theirFriendship != null) {
                friend.getFriendsList().getFriends().remove(theirFriendship.getFriendId());
                theirFriendship.delete();
                friend.sendPacket(new PacketDeleteFriendNotify(theirFriendship.getFriendId()));
            }
        } else {
            theirFriendship = DatabaseHelper.getReverseFriendship(myFriendship);
            if (theirFriendship != null) {
                theirFriendship.delete();
            }
        }
        this.player.sendPacket(new PacketDeleteFriendRsp(targetUid, PacketRetcodes.RETCODE_SUCC));
    }

    /**
     * Handler for add player to block list.
     * @param targetUid The target id.
     */
    public synchronized void handlerAddBlockedPlayer(int targetUid) {
        Player target = this.player.getServer().getPlayerByUid(targetUid, true);
        if(target == null || targetUid == ServerBot.UID) {
            // player not found
            this.player.sendPacket(new PacketAddBlacklistRsp(ServerBot.getFriendObject(), PacketRetcodes.RET_PLAYER_NOT_EXIST));
            return;
        }

        if(this.isBlockedWith(targetUid)) {
            // already blocked
            this.player.sendPacket(new PacketAddBlacklistRsp(ServerBot.getFriendObject(), PacketRetcodes.RET_ALREADY_IN_BLACKLIST));
            return;
        }

        if(this.getTotalBlockedPlayers() > GameConstants.blockedsMax) {
            // reached maximum blocked people
            this.player.sendPacket(new PacketAddBlacklistRsp(ServerBot.getFriendObject(), PacketRetcodes.RET_PLAYER_BLACKLIST_FULL));
            return;
        }

        SocialObject myFriendship;
        SocialObject theirFriendship;
        if(this.isFriendsWith(targetUid)) {
            // you are friend with him, remove him
            myFriendship = this.getFriendById(targetUid);
            myFriendship.setRemarkName(target.getNickname()); // in case
            myFriendship.setFriend(false);
            this.friends.remove(targetUid);
        }
        else {
            // creates a new "friendship"
            myFriendship = new SocialObject(this.player, target, this.player);
        }

        this.blockedPeople.put(myFriendship.getFriendId(), myFriendship);
        myFriendship.setBlocked(true);

        Player friend = myFriendship.getFriendProfile().getPlayer();
        if (friend != null) {
            theirFriendship = friend.getFriendsList().getFriendById(this.player.getUid());
            if (theirFriendship != null) {
                // he is your friend, remove him.
                friend.getFriendsList().getFriends().remove(theirFriendship.getFriendId());
                theirFriendship.delete();
                friend.sendPacket(new PacketDeleteFriendNotify(theirFriendship.getFriendId()));
            }
            else {
                theirFriendship = DatabaseHelper.getReverseFriendship(myFriendship);
                if (theirFriendship != null) {
                    theirFriendship.delete();
                }
            }
        }
        else {
            theirFriendship = DatabaseHelper.getReverseFriendship(myFriendship);
            if (theirFriendship != null) {
                theirFriendship.delete();
            }
        }

        myFriendship.saveDatabase();

        this.player.sendPacket(new PacketAddBlacklistRsp(myFriendship.toProto(), PacketRetcodes.RETCODE_SUCC));
    }

    /**
     * Handler for remove player from block list.
     * @param targetUid The target id.
     */
    public synchronized void handlerRemoveBlockedPlayer(int targetUid) {
        SocialObject myFriendship = this.getBlockedPeopleById(targetUid);
        if(myFriendship == null || targetUid == ServerBot.UID) {
            this.player.sendPacket(new PacketRemoveBlacklistRsp(targetUid, PacketRetcodes.RET_PLAYER_NOT_IN_BLACKLIST));
            return;
        }

        this.blockedPeople.remove(targetUid);
        myFriendship.delete();

        this.player.sendPacket(new PacketRemoveBlacklistRsp(targetUid, PacketRetcodes.RETCODE_SUCC));
    }
}