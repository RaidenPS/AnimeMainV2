package raidengame.connection.packets.send.game.team;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.game.player.Player;

// Protocol buffers
import raidengame.cache.protobuf.AvatarTeamAllDataNotifyOuterClass.AvatarTeamAllDataNotify;

/**
 * Notify for send all data in teams.
 */
public class PacketAvatarTeamAllDataNotify extends BasePacket {
    public PacketAvatarTeamAllDataNotify(Player player) {
        super(PacketIds.AvatarTeamAllDataNotify);

        AvatarTeamAllDataNotify.Builder proto =
                AvatarTeamAllDataNotify.newBuilder()
                        .setCurAvatarTeamId(player.getTeamManager().getCurrentTeamId());

        for (int id : player.getTeamManager().getTeams().keySet()) {
            if (id > 4) {
                proto.addBackupAvatarTeamOrderList(id);
            }
        }

        player.getTeamManager().getTeams().forEach((id, teamInfo) -> proto.putAvatarTeamMap(id, teamInfo.toProto(player)));
        proto.addAllTempAvatarGuidList(player.getTeamManager().getTemporaryTeamGuids());

        this.setData(proto.build());
    }
}