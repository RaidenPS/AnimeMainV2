package raidengame.connection.packets.receive.game.team;

// Imports
import raidengame.configuration.GameConstants;
import raidengame.connection.GameSession;
import raidengame.connection.base.*;
import raidengame.game.player.team.TeamInfo;
import raidengame.misc.WordFilter;

// Packets
import raidengame.connection.packets.send.game.team.PacketChangeTeamNameRsp;

// Protocol buffers
import raidengame.cache.protobuf.ChangeTeamNameReqOuterClass.ChangeTeamNameReq;

/**
 * Handler to change team's name.
 */
@PacketOpcode(PacketIds.ChangeTeamNameReq)
public class HandlerChangeTeamNameReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        ChangeTeamNameReq req = ChangeTeamNameReq.parseFrom(payload);
        String teamName = req.getTeamName();
        int teamId = req.getTeamId();

        if(teamName.isEmpty() || WordFilter.checkIsBadWord(teamName) || teamName.length() > GameConstants.NICKNAME_MAX_LEN) {
            session.send(new PacketChangeTeamNameRsp(PacketRetcodes.RET_TEAM_NAME_ILLEGAL, teamId, ""));
            return;
        }

        TeamInfo teamInfo = session.getPlayer().getTeamManager().getTeams().get(teamId);
        if (teamInfo == null) {
            session.send(new PacketChangeTeamNameRsp(PacketRetcodes.RETCODE_FAIL, teamId, ""));
            return;
        }

        teamInfo.setName(teamName);
        session.send(new PacketChangeTeamNameRsp(PacketRetcodes.RETCODE_SUCC, teamId, teamName));
    }
}