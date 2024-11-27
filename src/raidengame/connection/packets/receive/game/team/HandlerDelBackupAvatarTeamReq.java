package raidengame.connection.packets.receive.game.team;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;

// Packets
import raidengame.connection.packets.send.game.team.PacketDelBackupAvatarTeamRsp;

// Protocol buffers
import raidengame.cache.protobuf.DelBackupAvatarTeamReqOuterClass.DelBackupAvatarTeamReq;

/**
 * Handler to remove a backup avatar team.
 */
@PacketOpcode(PacketIds.DelBackupAvatarTeamReq)
public class HandlerDelBackupAvatarTeamReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        DelBackupAvatarTeamReq req = DelBackupAvatarTeamReq.parseFrom(data);
        var team = session.getPlayer().getTeamManager();
        int id = req.getBackupAvatarTeamId();

        if(team.getCurrentTeamId() == id) {
            session.send(new PacketDelBackupAvatarTeamRsp(PacketRetcodes.RET_BACKUP_TEAM_IS_CUR_TEAM, id));
            return;
        }

        if (!team.getTeams().containsKey(id)) {
            session.send(new PacketDelBackupAvatarTeamRsp(PacketRetcodes.RET_BACKUP_TEAM_ID_NOT_VALID, id));
            return;
        }

        session.getPlayer().getTeamManager().removeCustomTeam(id);
        session.send(new PacketDelBackupAvatarTeamRsp(PacketRetcodes.RETCODE_SUCC, id));
    }
}