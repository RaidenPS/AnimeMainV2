package raidengame.connection.packets.receive.scene;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;

// Packets
import raidengame.connection.packets.send.scene.PacketPostEnterSceneRsp;

// Protocol buffers
import raidengame.cache.protobuf.PostEnterSceneReqOuterClass.PostEnterSceneReq;

/**
 * Handler for send PostEnterSceneRsp.
 */
@PacketOpcode(PacketIds.PostEnterSceneReq)
public class HandlerPostEnterSceneReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        PostEnterSceneReq req = PostEnterSceneReq.parseFrom(data);
        int sceneToken = req.getEnterSceneToken();
        if(sceneToken != session.getPlayer().getEnterSceneToken()) {
            session.logPacketRetcode(PacketRetcodes.RET_ENTER_SCENE_TOKEN_INVALID, "Scene", "PostEnterSceneRsp (RET_ENTER_SCENE_TOKEN_INVALID)");
            session.send(new PacketPostEnterSceneRsp(sceneToken, PacketRetcodes.RET_ENTER_SCENE_TOKEN_INVALID));
            return;
        }

        session.send(new PacketPostEnterSceneRsp(sceneToken, PacketRetcodes.RETCODE_SUCC));
    }
}