package raidengame.connection.packets.send.game.world;

// Imports
import static raidengame.connection.base.PacketRetcodes.RETCODE_SUCC;
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;

// Protocol buffers
import raidengame.cache.protobuf.SkipPlayerGameTimeReqOuterClass.SkipPlayerGameTimeReq;
import raidengame.cache.protobuf.SkipPlayerGameTimeRspOuterClass.SkipPlayerGameTimeRsp;

/**
 * Packet to skip the time in the game.
 */
public class PacketSkipPlayerGameTimeRsp extends BasePacket {
    public PacketSkipPlayerGameTimeRsp(SkipPlayerGameTimeReq req) {
        super(PacketIds.SkipPlayerGameTimeRsp);

        SkipPlayerGameTimeRsp proto =
                SkipPlayerGameTimeRsp.newBuilder()
                        .setRetcode(RETCODE_SUCC)
                        .setGameTime(req.getGameTime())
                        .setClientGameTime(req.getClientGameTime())
                        .build();

        this.setData(proto);
    }
}