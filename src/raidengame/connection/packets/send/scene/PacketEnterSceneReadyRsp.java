package raidengame.connection.packets.send.scene;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;

// Protocol buffers
import raidengame.cache.protobuf.EnterSceneReadyRspOuterClass.EnterSceneReadyRsp;

/**
 * Response from EnterSceneReadyReq.
 */
public class PacketEnterSceneReadyRsp extends BasePacket {
    public PacketEnterSceneReadyRsp(int scene_token, int retcode) {
        super(PacketIds.EnterSceneReadyRsp);

        EnterSceneReadyRsp proto =
                EnterSceneReadyRsp.newBuilder()
                        .setRetcode(retcode)
                        .setEnterSceneToken(scene_token)
                        .build();

        this.setData(proto);
    }
}