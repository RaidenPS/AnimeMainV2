package raidengame.connection.packets.send.scene;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;

// Protocol buffers
import raidengame.cache.protobuf.SceneInitFinishRspOuterClass.SceneInitFinishRsp;

/**
 * Packet to init the scene.
 */
public class PacketSceneInitFinishRsp extends BasePacket {
    public PacketSceneInitFinishRsp(int sceneToken, int retcode) {
        super(PacketIds.SceneInitFinishRsp);

        SceneInitFinishRsp proto =
                SceneInitFinishRsp.newBuilder()
                        .setRetcode(retcode)
                        .setEnterSceneToken(sceneToken)
                        .build();

        this.setData(proto);
    }
}