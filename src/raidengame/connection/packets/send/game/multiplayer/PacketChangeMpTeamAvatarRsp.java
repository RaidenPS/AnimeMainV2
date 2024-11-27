package raidengame.connection.packets.send.game.multiplayer;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.game.player.Player;
import raidengame.game.player.team.TeamInfo;

// Protocol buffers
import raidengame.cache.protobuf.ChangeMpTeamAvatarRspOuterClass.ChangeMpTeamAvatarRsp;

/**
 * Packet to change team avatar in multiplayer.
 */
public class PacketChangeMpTeamAvatarRsp extends BasePacket {
    public PacketChangeMpTeamAvatarRsp(Player player, TeamInfo teamInfo) {
        super(PacketIds.ChangeMpTeamAvatarRsp);

        ChangeMpTeamAvatarRsp.Builder proto =
                ChangeMpTeamAvatarRsp.newBuilder()
                        .setCurAvatarGuid(player.getTeamManager().getCurrentCharacterGuid());

        for (int avatarId : teamInfo.getAvatars()) {
            proto.addAvatarGuidList(player.getAvatars().getAvatarById(avatarId).getGuid());
        }

        this.setData(proto.build());
    }
}