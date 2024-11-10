package raidengame.connection.packets.send.game.social;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;

// Protocol buffers
import raidengame.cache.protobuf.SetFriendRemarkNameRspOuterClass.SetFriendRemarkNameRsp;

/**
 * Packet to send a remark name to friend.
 */
public class PacketSetFriendRemarkNameRsp extends BasePacket {
    public PacketSetFriendRemarkNameRsp(int targetUid, String remarkName, int retcode) {
        super(PacketIds.SetFriendRemarkNameRsp);

        SetFriendRemarkNameRsp proto =
                SetFriendRemarkNameRsp.newBuilder()
                        .setRetcode(retcode)
                        .setUid(targetUid)
                        .setRemarkName(remarkName)
                        .setIsClearRemark(false)
                        .build();

        this.setData(proto);
    }
}