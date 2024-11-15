package raidengame.connection.packets.receive.scene;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;

// Packets
import raidengame.connection.packets.send.scene.PacketPathfindingEnterSceneRsp;

// Protocol buffers
import raidengame.cache.protobuf.PathfindingEnterSceneReqOuterClass.PathfindingEnterSceneReq;

/**
 * Handler for send PathfindingEnterSceneRsp.
 */
@PacketOpcode(PacketIds.PathfindingEnterSceneReq)
public class HandlerPathfindingEnterSceneReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        PathfindingEnterSceneReq req = PathfindingEnterSceneReq.parseFrom(data);
        var player = session.getPlayer();
        if(player == null || req.getSceneId() != player.getSceneId()) {
            session.send(new PacketPathfindingEnterSceneRsp(PacketRetcodes.RET_PATHFINDING_ERROR_SCENE));
            return;
        }

        session.send(new PacketPathfindingEnterSceneRsp(PacketRetcodes.RETCODE_SUCC));
    }
}