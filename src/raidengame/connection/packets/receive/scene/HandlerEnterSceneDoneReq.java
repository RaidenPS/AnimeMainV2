package raidengame.connection.packets.receive.scene;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;
import raidengame.game.player.SceneLoadState;

// Packets
import raidengame.connection.packets.send.game.world.PacketPlayerTimeNotify;
import raidengame.connection.packets.send.game.world.PacketWorldPlayerLocationNotify;
import raidengame.connection.packets.send.game.world.PacketWorldPlayerRTTNotify;
import raidengame.connection.packets.send.scene.PacketEnterSceneDoneRsp;
import raidengame.connection.packets.send.scene.PacketScenePlayerLocationNotify;

// Protocol buffers
import raidengame.cache.protobuf.EnterSceneDoneReqOuterClass.EnterSceneDoneReq;

/**
 * Handler for send EnterSceneDoneRsp.
 */
@PacketOpcode(PacketIds.EnterSceneDoneReq)
public class HandlerEnterSceneDoneReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        EnterSceneDoneReq req = EnterSceneDoneReq.parseFrom(data);
        var player = session.getPlayer();
        int sceneToken = req.getEnterSceneToken();

        /// todo: Finish EnterSceneDoneReq

        player.setSceneLoadState(SceneLoadState.LOADED);
        if(sceneToken != player.getEnterSceneToken()) {
            //player.setSceneLoadState(SceneLoadState.NONE);
            session.send(new PacketEnterSceneDoneRsp(sceneToken, PacketRetcodes.RET_ENTER_SCENE_TOKEN_INVALID));
            return;
        }

        // send the post-packets.
        session.send(new PacketPlayerTimeNotify(player));
        session.send(new PacketWorldPlayerLocationNotify(player.getWorld()));
        session.send(new PacketScenePlayerLocationNotify(player.getScene()));
        session.send(new PacketWorldPlayerRTTNotify(player.getWorld()));

        session.send(new PacketEnterSceneDoneRsp(sceneToken, PacketRetcodes.RETCODE_SUCC));
    }
}