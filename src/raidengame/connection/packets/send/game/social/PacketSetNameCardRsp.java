package raidengame.connection.packets.send.game.social;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;

// Protocol buffers
import raidengame.cache.protobuf.SetNameCardRspOuterClass.SetNameCardRsp;

/**
 * Packet to send current name card in profile.
 */
public class PacketSetNameCardRsp extends BasePacket {
    public PacketSetNameCardRsp(int nameCardId, int retcode) {
        super(PacketIds.SetNameCardRsp);

        SetNameCardRsp proto =
                SetNameCardRsp.newBuilder()
                        .setRetcode(retcode)
                        .setNameCardId(nameCardId)
                        .build();

        this.setData(proto);
    }
}