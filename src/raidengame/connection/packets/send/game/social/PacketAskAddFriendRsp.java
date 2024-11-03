package raidengame.connection.packets.send.game.social;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;

// Protocol buffers
import raidengame.cache.protobuf.AskAddFriendRspOuterClass.AskAddFriendRsp;

/**
 * Packet to send friend request.
 */
public class PacketAskAddFriendRsp extends BasePacket {
    public PacketAskAddFriendRsp(int targetUid, int retcode) {
        super(PacketIds.AskAddFriendRsp);

        AskAddFriendRsp proto =
                AskAddFriendRsp.newBuilder()
                        .setRetcode(retcode)
                        .setTargetUid(targetUid)
                        .setParam(1)
                        .build();

        this.setData(proto);
    }
}