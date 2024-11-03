package raidengame.connection.packets.send.scene;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;

// Protocol buffers
import raidengame.cache.protobuf.PathfindingEnterSceneRspOuterClass.PathfindingEnterSceneRsp;

/**
 * Packet to send response from PathfindingEnterSceneReq.
 */
public class PacketPathfindingEnterSceneRsp extends BasePacket {
    public PacketPathfindingEnterSceneRsp(int retcode) {
        super(PacketIds.PathfindingEnterSceneRsp);

        PathfindingEnterSceneRsp proto =
                PathfindingEnterSceneRsp.newBuilder()
                        .setRetcode(retcode)
                        .build();

        this.setData(proto);
    }
}
