package raidengame.connection.packets.receive.scene;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;

// Packets
import raidengame.connection.packets.send.scene.PacketGetScenePointRsp;

// Protocol buffers
import raidengame.cache.protobuf.GetScenePointReqOuterClass.GetScenePointReq;

/**
 * Handler for send scene point.
 */
@PacketOpcode(PacketIds.GetScenePointReq)
public class HandlerGetScenePointReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        GetScenePointReq req = GetScenePointReq.parseFrom(data);
        session.send(new PacketGetScenePointRsp(req.getSceneId(), req.getIsNewPlayer()));
    }
}