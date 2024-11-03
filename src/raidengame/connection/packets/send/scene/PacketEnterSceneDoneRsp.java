package raidengame.connection.packets.send.scene;

// Imports
import static raidengame.connection.base.PacketRetcodes.RETCODE_SUCC;
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;

// Protocol buffers
import raidengame.cache.protobuf.EnterSceneDoneRspOuterClass.EnterSceneDoneRsp;

/**
 * Response from EnterSceneDoneReq.
 */
public class PacketEnterSceneDoneRsp extends BasePacket {
    public PacketEnterSceneDoneRsp(int scene_token) {
        super(PacketIds.EnterSceneDoneRsp);

        EnterSceneDoneRsp proto =
                EnterSceneDoneRsp.newBuilder()
                        .setRetcode(RETCODE_SUCC)
                        .setEnterSceneToken(scene_token)
                        .build();

        this.setData(proto);
    }
}