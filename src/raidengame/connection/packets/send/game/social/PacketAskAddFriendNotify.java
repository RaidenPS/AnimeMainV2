package raidengame.connection.packets.send.game.social;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.game.player.social.SocialObject;

// Protocol buffers
import raidengame.cache.protobuf.AskAddFriendNotifyOuterClass.AskAddFriendNotify;

/**
 * Packet to send notification about sending a friend request.
 */
public class PacketAskAddFriendNotify extends BasePacket {
    public PacketAskAddFriendNotify(SocialObject friendship) {
        super(PacketIds.AskAddFriendNotify);

        AskAddFriendNotify proto =
                AskAddFriendNotify.newBuilder()
                        .setTargetUid(friendship.getFriendId())
                        .setTargetFriendBrief(friendship.toProto())
                        .build();

        this.setData(proto);
    }
}