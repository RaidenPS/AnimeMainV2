package raidengame.connection.packets.receive.scene;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;

// Packets
import raidengame.connection.packets.send.scene.PacketGetSceneAreaRsp;

// Protocol buffers
import raidengame.cache.protobuf.GetSceneAreaReqOuterClass.GetSceneAreaReq;

/**
 * Handler for send scene area.
 */
@PacketOpcode(PacketIds.GetSceneAreaReq)
public class HandlerGetSceneAreaReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        GetSceneAreaReq req = GetSceneAreaReq.parseFrom(data);
        session.send(new PacketGetSceneAreaRsp(session.getPlayer(), req.getSceneId()));
    }
}