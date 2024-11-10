package raidengame.connection.packets.send.scene;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;

// Protocol buffers
import raidengame.cache.protobuf.EnterSceneDoneRspOuterClass.EnterSceneDoneRsp;

/**
 * Response from EnterSceneDoneReq.
 */
public class PacketEnterSceneDoneRsp extends BasePacket {
    public PacketEnterSceneDoneRsp(int sceneToken, int retcode) {
        super(PacketIds.EnterSceneDoneRsp);

        EnterSceneDoneRsp proto =
                EnterSceneDoneRsp.newBuilder()
                        .setRetcode(retcode)
                        .setEnterSceneToken(sceneToken)
                        .build();

        this.setData(proto);
    }
}