package raidengame.connection.packets.send.scene;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;

// Protocol buffers
import raidengame.cache.protobuf.PostEnterSceneRspOuterClass.PostEnterSceneRsp;

/**
 * Packet for send finish to enter the world.
 */
public class PacketPostEnterSceneRsp extends BasePacket {
    public PacketPostEnterSceneRsp(int scene_token, int retcode) {
        super(PacketIds.PostEnterSceneRsp);

        PostEnterSceneRsp proto =
                PostEnterSceneRsp.newBuilder()
                        .setRetcode(retcode)
                        .setEnterSceneToken(scene_token)
                        .build();

        this.setData(proto);
    }
}