package raidengame.connection.packets.send.game;

// Imports
import static raidengame.connection.base.PacketRetcodes.RETCODE_SUCC;
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;

// Protocol buffers
import raidengame.cache.protobuf.PlayerGameKickRspOuterClass.PlayerGameKickRsp;

/**
 * Packet to kick the player from the game.
 */
public class PacketPlayerGameKickRsp extends BasePacket {
    public PacketPlayerGameKickRsp() {
        super(PacketIds.PlayerGameKickRsp);

        PlayerGameKickRsp proto =
                PlayerGameKickRsp.newBuilder()
                .setRetcode(RETCODE_SUCC)
                .build();

        this.setData(proto);
    }
}