package raidengame.connection.packets.receive.scene;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;

// Packets
import raidengame.connection.packets.send.scene.PacketPostEnterSceneRsp;

// Protocol buffers
import raidengame.cache.protobuf.PostEnterSceneReqOuterClass.PostEnterSceneReq;

@PacketOpcode(PacketIds.PostEnterSceneReq)
public class HandlerPostEnterSceneReq  extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        PostEnterSceneReq req = PostEnterSceneReq.parseFrom(data);
        session.send(new PacketPostEnterSceneRsp(req.getEnterSceneToken()));
    }
}