package raidengame.connection.packets.send.game.team;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;

// Protocol buffers
import raidengame.cache.protobuf.AddBackupAvatarTeamRspOuterClass.AddBackupAvatarTeamRsp;

/**
 * Packet to create a new backup team.
 */
public class PacketAddBackupAvatarTeamRsp extends BasePacket {
    public PacketAddBackupAvatarTeamRsp(int retcode) {
        super(PacketIds.AddBackupAvatarTeamRsp);

        AddBackupAvatarTeamRsp proto =
                AddBackupAvatarTeamRsp.newBuilder()
                        .setRetcode(retcode)
                        .build();

        this.setData(proto);
    }
}