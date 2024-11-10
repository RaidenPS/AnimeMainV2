package raidengame.connection.packets.send.game.chat;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;

// Protocol buffers
import raidengame.cache.protobuf.PrivateChatRspOuterClass.PrivateChatRsp;

/**
 * Packet to send response from PrivateChatReq.
 */
public class PacketPrivateChatRsp extends BasePacket {
    public PacketPrivateChatRsp(int restrict_time, int retcode) {
        super(PacketIds.PrivateChatRsp);

        PrivateChatRsp proto =
                PrivateChatRsp.newBuilder()
                        .setRetcode(retcode)
                        .setChatForbiddenEndtime(restrict_time)
                        .build();

        this.setData(proto);
    }
}