package raidengame.connection.packets.receive.game.team;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;

// Protocol buffers
import raidengame.cache.protobuf.ChooseCurAvatarTeamReqOuterClass.ChooseCurAvatarTeamReq;
import raidengame.connection.packets.send.game.team.PacketChooseCurAvatarTeamRsp;
import raidengame.game.player.team.TeamInfo;

/**
 * Handler for deploy a team.
 */
@PacketOpcode(PacketIds.ChooseCurAvatarTeamReq)
public class HandlerChooseCurAvatarTeamReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        ChooseCurAvatarTeamReq req = ChooseCurAvatarTeamReq.parseFrom(payload);
        int teamId = req.getTeamId();
        var player = session.getPlayer();

        // multiplayer
        if (player.isInMultiplayer()) {
            session.send(new PacketChooseCurAvatarTeamRsp(PacketRetcodes.RETCODE_FAIL, teamId));
            return;
        }

        // get team
        TeamInfo teamInfo = player.getTeamManager().getTeams().get(teamId);
        if (teamInfo == null || teamInfo.getAvatars().isEmpty()) {
            session.send(new PacketChooseCurAvatarTeamRsp(PacketRetcodes.RETCODE_FAIL, teamId));
            return;
        }

        player.getTeamManager().setCurrentTeamId(teamId);
        session.send(new PacketChooseCurAvatarTeamRsp(PacketRetcodes.RETCODE_SUCC, teamId));
    }
}