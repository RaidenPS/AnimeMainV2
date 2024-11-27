package raidengame.connection.packets.send.game.world;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;

// Protocol buffers
import raidengame.cache.protobuf.PlayerSetPauseRspOuterClass.PlayerSetPauseRsp;

/**
 * Packet to pause player's state in the game.
 */
public class PacketPlayerSetPauseRsp extends BasePacket {
    public PacketPlayerSetPauseRsp(int retcode) {
        super(PacketIds.PlayerSetPauseRsp);

        PlayerSetPauseRsp proto =
                PlayerSetPauseRsp.newBuilder()
                        .setRetcode(retcode)
                        .build();

        this.setData(proto);
    }
}