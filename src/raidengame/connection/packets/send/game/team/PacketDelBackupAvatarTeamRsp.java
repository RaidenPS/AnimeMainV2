package raidengame.connection.packets.send.game.team;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;

// Protocol buffers
import raidengame.cache.protobuf.DelBackupAvatarTeamRspOuterClass.DelBackupAvatarTeamRsp;

/**
 * Packet to disband a backup team.
 */
public class PacketDelBackupAvatarTeamRsp extends BasePacket {
    public PacketDelBackupAvatarTeamRsp(int retcode, int id) {
        super(PacketIds.DelBackupAvatarTeamRsp);

        DelBackupAvatarTeamRsp proto =
                DelBackupAvatarTeamRsp.newBuilder()
                        .setRetcode(retcode)
                        .setBackupAvatarTeamId(id)
                        .build();

        this.setData(proto);
    }
}