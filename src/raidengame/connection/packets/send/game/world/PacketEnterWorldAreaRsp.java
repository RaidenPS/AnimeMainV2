package raidengame.connection.packets.send.game.world;

// Imports
import static raidengame.connection.base.PacketRetcodes.RETCODE_SUCC;
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;

// Protocol buffers
import raidengame.cache.protobuf.EnterWorldAreaReqOuterClass.EnterWorldAreaReq;
import raidengame.cache.protobuf.EnterWorldAreaRspOuterClass.EnterWorldAreaRsp;

/**
 * Packet to send world's area id and type.
 */
public class PacketEnterWorldAreaRsp extends BasePacket {
    public PacketEnterWorldAreaRsp(EnterWorldAreaReq req) {
        super(PacketIds.EnterWorldAreaRsp);

        EnterWorldAreaRsp proto =
                EnterWorldAreaRsp.newBuilder()
                        .setRetcode(RETCODE_SUCC)
                        .setAreaId(req.getAreaId())
                        .setAreaType(req.getAreaType())
                        .build();

        this.setData(proto);
    }
}