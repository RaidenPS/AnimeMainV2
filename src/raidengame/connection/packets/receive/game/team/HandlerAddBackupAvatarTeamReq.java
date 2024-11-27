package raidengame.connection.packets.receive.game.team;

// Imports
import raidengame.configuration.GameConstants;
import raidengame.connection.GameSession;
import raidengame.connection.base.*;

// Packets
import raidengame.connection.packets.send.game.team.PacketAddBackupAvatarTeamRsp;

/**
 * Handler for create backup team.
 */
@PacketOpcode(PacketIds.AddBackupAvatarTeamReq)
public class HandlerAddBackupAvatarTeamReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        var player = session.getPlayer();
        if(player.getTeamManager().getTeams().size() == GameConstants.TOTAL_TEAMS_SIZE) {
            session.send(new PacketAddBackupAvatarTeamRsp(PacketRetcodes.RET_FULL_BACKUP_TEAM));
            return;
        }

        player.getTeamManager().addNewCustomTeam();
        session.send(new PacketAddBackupAvatarTeamRsp(PacketRetcodes.RETCODE_SUCC));
    }
}