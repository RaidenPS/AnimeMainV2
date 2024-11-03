package raidengame.connection.packets.send.scene;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;

// Protocol buffers
import raidengame.cache.protobuf.ToTheMoonEnterSceneRspOuterClass.ToTheMoonEnterSceneRsp;

/**
 * Packet to send response from ToTheMoonEnterSceneReq.
 */
public class PacketToTheMoonEnterSceneRsp extends BasePacket {
    public PacketToTheMoonEnterSceneRsp(int retcode) {
        super(PacketIds.ToTheMoonEnterSceneRsp);

        ToTheMoonEnterSceneRsp proto =
                ToTheMoonEnterSceneRsp.newBuilder()
                        .setRetcode(retcode)
                        .build();

        this.setData(proto);
    }
}
