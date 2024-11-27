package raidengame.connection.packets.receive.login;

// Imports
import raidengame.configuration.*;
import raidengame.connection.GameSession;
import raidengame.connection.SessionState;
import raidengame.connection.base.*;
import raidengame.database.entities.GameLog;
import raidengame.enums.player.PlatformType;
import raidengame.game.player.Player;

// Packets
import raidengame.connection.packets.send.login.PacketPlayerLoginRsp;

// Protocol buffers
import raidengame.cache.protobuf.PlayerLoginReqOuterClass.PlayerLoginReq;
import raidengame.misc.classes.Randomizer;

/**
 * Handler for send login packet.
 */
@PacketOpcode(PacketIds.PlayerLoginReq)
public class HandlerPlayerLoginReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        var req = PlayerLoginReq.parseFrom(data);
        if (!req.getToken().equals(session.getAccount().getToken())) {
            /// Token failed
            session.send(new PacketPlayerLoginRsp(session, req, PacketRetcodes.RET_TOKEN_ERROR, "TOKEN_MISMATCH"));
            return;
        }

        if(ConfigManager.serverConfig.gameInfo.isBeta && !ConfigManager.serverConfig.gameInfo.betaAccountIds.contains(session.getAccount().getId())) {
            // Beta only.
            session.send(new PacketPlayerLoginRsp(session, req, PacketRetcodes.RET_BETA_ACCESS_DENIED, "SomebodyGotAccessToBeta"));
            return;
        }

        if(!req.getSecurityLibraryMd5().equals(GameConstants.security_library_md5)) {
            session.send(new PacketPlayerLoginRsp(session, req, PacketRetcodes.RET_SECURITY_LIBRARY_ERROR, "SECURITY_LIBRARY_MD5_HASH_MISMATCH"));
            return;
        }

        if(ConfigManager.serverConfig.isMaintenance) {
            // The server is under maintenance.
            session.send(new PacketPlayerLoginRsp(session));
            return;
        }

        if(!req.getClientVersionName().equals(ConfigManager.serverConfig.gameInfo.gameVersion)) {
            // Outdated version
            session.send(new PacketPlayerLoginRsp(session, req, PacketRetcodes.RET_CLIENT_FORCE_UPDATE, "LOGIN_ANOTHER_GAME_VERSION"));
            return;
        }

        if(!req.getClientVersion().equals(ConfigManager.serverConfig.gameInfo.gameRawVersion)) {
            // Outdated version
            session.send(new PacketPlayerLoginRsp(session, req, PacketRetcodes.RET_CLIENT_VERSION_ERROR, "LOGIN_ANOTHER_GAME_VERSION_2"));
            return;
        }

        if(ConfigManager.serverConfig.gameInfo.isBeta) {
            // logging only on beta versions. (fair)
            GameLog log = new GameLog(session.getPlayer(), req);
            log.save();
        }

        Player player = session.getPlayer();
        player.setPlatform(PlatformType.fromValue(req.getPlatformType()));
        player.setOnlineId(req.getOnlineId());
        player.setPsnId(req.getPsnId()); // ps4/5 support
        if (player.getAvatars().getAvatarCount() == 0) {
            if(ConfigManager.serverConfig.gameInfo.enableSwitchCharacters) {
                session.setState(SessionState.PICKING_CHARACTER);
                session.send(new BasePacket(PacketIds.DoSetPlayerBornDataNotify));
            }
            else {
                int avatarId = (Randomizer.randomRange(1, 2) == 2) ? GameConstants.MAIN_CHARACTER_MALE : GameConstants.MAIN_CHARACTER_FEMALE;
                player.addAvatar(avatarId, true, false);
                player.getTeamManager().getCurrentSinglePlayerTeamInfo().getAvatars().add(avatarId);
                player.setNickname("Traveler");
                player.saveDatabase();

                player.onLogin(true);
            }
        }
        else {
            player.onLogin(false);
        }
        session.send(new PacketPlayerLoginRsp(session, req));
    }
}