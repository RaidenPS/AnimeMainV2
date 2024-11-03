package raidengame.connection.packets.send.game.social;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;

// Protocol buffers
import raidengame.cache.protobuf.DeleteFriendRspOuterClass.DeleteFriendRsp;

/**
 * Packet to remove a person from friend list.
 */
public class PacketDeleteFriendRsp extends BasePacket {
    public PacketDeleteFriendRsp(int targetUid, int retcode) {
        super(PacketIds.DeleteFriendRsp);

        DeleteFriendRsp proto =
                DeleteFriendRsp.newBuilder()
                        .setRetcode(retcode)
                        .setTargetUid(targetUid)
                        .build();

        this.setData(proto);
    }
}