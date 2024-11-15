package raidengame.connection.packets.receive.scene;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;
import raidengame.game.player.SceneLoadState;

// Packets
import raidengame.connection.packets.send.game.PacketServerTimeNotify;
import raidengame.connection.packets.send.game.avatar.PacketSyncTeamEntityNotify;
import raidengame.connection.packets.send.game.lua.PacketWindSeedType1Notify;
import raidengame.connection.packets.send.game.world.PacketHostPlayerNotify;
import raidengame.connection.packets.send.game.world.PacketPlayerGameTimeNotify;
import raidengame.connection.packets.send.scene.PacketPlayerWorldSceneInfoListNotify;
import raidengame.connection.packets.send.game.world.PacketWorldDataNotify;
import raidengame.connection.packets.send.game.world.PacketWorldPlayerInfoNotify;
import raidengame.connection.packets.send.scene.PacketPlayerEnterSceneInfoNotify;
import raidengame.connection.packets.send.scene.PacketSceneAreaWeatherNotify;
import raidengame.connection.packets.send.scene.PacketSceneForceUnlockNotify;
import raidengame.connection.packets.send.scene.PacketSceneInitFinishRsp;
import raidengame.connection.packets.send.scene.PacketScenePlayerInfoNotify;
import raidengame.connection.packets.send.scene.PacketSceneTeamUpdateNotify;
import raidengame.connection.packets.send.scene.PacketSceneTimeNotify;
import raidengame.connection.packets.send.scene.PacketSyncScenePlayTeamEntityNotify;

// Protocol buffers
import raidengame.cache.protobuf.SceneInitFinishReqOuterClass.SceneInitFinishReq;

/**
 * Handler for send important packets while init the scene (world).
 */
@PacketOpcode(PacketIds.SceneInitFinishReq)
public class HandlerSceneInitFinishReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        SceneInitFinishReq req = SceneInitFinishReq.parseFrom(data);
        var player = session.getPlayer();
        var world = session.getPlayer().getWorld();
        int sceneToken = req.getEnterSceneToken();

        player.setSceneLoadState(SceneLoadState.INIT);
        if(sceneToken != player.getEnterSceneToken()) {
            session.logPacketRetcode(PacketRetcodes.RET_ENTER_SCENE_TOKEN_INVALID, "Scene", "SceneInitFinishRsp (RET_ENTER_SCENE_TOKEN_INVALID)");
            session.send(new PacketSceneInitFinishRsp(sceneToken, PacketRetcodes.RET_ENTER_SCENE_TOKEN_INVALID));
            return;
        }

        /// send the pre packets.
        session.send(new PacketServerTimeNotify());
        session.send(new PacketWorldPlayerInfoNotify(world));
        session.send(new PacketWorldDataNotify(world));
        session.send(new PacketPlayerWorldSceneInfoListNotify(player));
        session.send(new PacketSceneForceUnlockNotify());
        session.send(new PacketHostPlayerNotify(world));
        session.send(new PacketSceneTimeNotify(player.getScene()));
        session.send(new PacketPlayerGameTimeNotify(player));
        session.send(new PacketPlayerEnterSceneInfoNotify(player));
        session.send(new PacketSceneAreaWeatherNotify(player));
        session.send(new PacketScenePlayerInfoNotify(world));
        session.send(new PacketSceneTeamUpdateNotify(player));
        session.send(new PacketSyncTeamEntityNotify(player));
        session.send(new PacketSyncScenePlayTeamEntityNotify(player));

        session.send(new PacketSceneInitFinishRsp(sceneToken, PacketRetcodes.RETCODE_SUCC));
        session.send(new PacketWindSeedType1Notify("lua/uid.luac"));
    }
}