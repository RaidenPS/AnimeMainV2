package raidengame.connection.packets.receive.scene;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;

// Packets
import raidengame.connection.packets.send.scene.PacketEnterScenePeerNotify;
import raidengame.connection.packets.send.scene.PacketEnterSceneReadyRsp;

// Protocol buffers
import raidengame.cache.protobuf.EnterSceneReadyReqOuterClass.EnterSceneReadyReq;

/**
 * Handler for start loading the screen. (EnterSceneReadyRsp)
 */
@PacketOpcode(PacketIds.EnterSceneReadyReq)
public class HandlerEnterSceneReadyReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        EnterSceneReadyReq req = EnterSceneReadyReq.parseFrom(data);
        session.send(new PacketEnterScenePeerNotify(session.getPlayer()));
        session.send(new PacketEnterSceneReadyRsp(req.getEnterSceneToken()));
    }
}