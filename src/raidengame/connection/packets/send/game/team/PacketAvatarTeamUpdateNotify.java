package raidengame.connection.packets.send.game.team;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.game.player.Player;

// Protocol buffers
import raidengame.cache.protobuf.AvatarTeamUpdateNotifyOuterClass.AvatarTeamUpdateNotify;

/**
 * Packet to update the avatar team in current team.
 */
public class PacketAvatarTeamUpdateNotify extends BasePacket {
    public PacketAvatarTeamUpdateNotify(Player player) {
        super(PacketIds.AvatarTeamUpdateNotify);

        AvatarTeamUpdateNotify.Builder proto =
                AvatarTeamUpdateNotify.newBuilder();

        var teamManager = player.getTeamManager();
        if (teamManager.isUsingTrialTeam()) {
            proto.addAllTempAvatarGuidList(teamManager.getActiveTeam().stream().map(entity -> entity.getAvatar().getGuid()).toList());
        } else {
            teamManager.getTeams().forEach((key, value) -> proto.putAvatarTeamMap(key, value.toProto(player)));
        }

        this.setData(proto.build());
    }
}