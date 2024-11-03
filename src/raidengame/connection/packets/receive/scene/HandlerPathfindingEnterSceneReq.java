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
        if(session.getPlayer() == null) {
            session.send(new PacketPathfindingEnterSceneRsp(PacketRetcodes.RET_TOTHEMOON_PLAYER_NOT_EXIST));
            return;
        }

        if(req.getSceneId() != session.getPlayer().getSceneId()) {
            session.send(new PacketPathfindingEnterSceneRsp(PacketRetcodes.RET_TOTHEMOON_ERROR_SCENE));
            return;
        }

        session.send(new PacketPathfindingEnterSceneRsp(PacketRetcodes.RETCODE_SUCC));
    }
}