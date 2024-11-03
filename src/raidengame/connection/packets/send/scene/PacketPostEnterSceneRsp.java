package raidengame.connection.packets.send.scene;

// Imports
import static raidengame.connection.base.PacketRetcodes.RETCODE_SUCC;
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;

// Protocol buffers
import raidengame.cache.protobuf.PostEnterSceneRspOuterClass.PostEnterSceneRsp;

/**
 * Packet for send finish to enter the world.
 */
public class PacketPostEnterSceneRsp extends BasePacket {
    public PacketPostEnterSceneRsp(int scene_token) {
        super(PacketIds.PostEnterSceneRsp);

        PostEnterSceneRsp proto =
                PostEnterSceneRsp.newBuilder()
                        .setRetcode(RETCODE_SUCC)
                        .setEnterSceneToken(scene_token)
                        .build();

        this.setData(proto);
    }
}