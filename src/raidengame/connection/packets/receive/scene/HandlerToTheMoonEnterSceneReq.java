package raidengame.connection.packets.receive.scene;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;

// Packets
import raidengame.connection.packets.send.scene.PacketToTheMoonEnterSceneRsp;

// Protocol buffers
import raidengame.cache.protobuf.ToTheMoonEnterSceneReqOuterClass.ToTheMoonEnterSceneReq;

/**
 * Handler for send ToTheMoonEnterSceneRsp.
 */
@PacketOpcode(PacketIds.ToTheMoonEnterSceneReq)
public class HandlerToTheMoonEnterSceneReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        ToTheMoonEnterSceneReq req = ToTheMoonEnterSceneReq.parseFrom(data);
        if(session.getPlayer() == null) {
            session.send(new PacketToTheMoonEnterSceneRsp(PacketRetcodes.RET_TOTHEMOON_PLAYER_NOT_EXIST));
            return;
        }

        if(req.getSceneId() != session.getPlayer().getSceneId()) {
            session.send(new PacketToTheMoonEnterSceneRsp(PacketRetcodes.RET_TOTHEMOON_ERROR_SCENE));
            return;
        }

        session.send(new PacketToTheMoonEnterSceneRsp(PacketRetcodes.RETCODE_SUCC));
    }
}