package raidengame.connection.packets.send.game;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;

// Protocol buffers
import raidengame.cache.protobuf.SetOpenStateRspOuterClass.SetOpenStateRsp;

/**
 * Packet to set player's open state.
 */
public class PacketSetOpenStateRsp extends BasePacket {
    public PacketSetOpenStateRsp(int key, int value, int retcode) {
        super(PacketIds.SetOpenStateRsp);

        SetOpenStateRsp proto =
                SetOpenStateRsp.newBuilder()
                        .setRetcode(retcode)
                        .setKey(key)
                        .setValue(value)
                        .build();

        this.setData(proto);
    }
}