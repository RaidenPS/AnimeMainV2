package raidengame.connection.packets.send.game.team;

// Imports
import static raidengame.connection.base.PacketRetcodes.RETCODE_SUCC;
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.game.player.Player;
import raidengame.game.player.team.TeamInfo;

// Protocol buffers
import raidengame.cache.protobuf.SetUpAvatarTeamRspOuterClass.SetUpAvatarTeamRsp;

/**
 * Packet to add avatars in given team.
 */
public class PacketSetUpAvatarTeamRsp extends BasePacket {
    /**
     * Default packet.
     * @param player The player.
     * @param teamId The teamId.
     * @param teamInfo The teamInfo.
     */
    public PacketSetUpAvatarTeamRsp(Player player, int teamId, TeamInfo teamInfo) {
        super(PacketIds.SetUpAvatarTeamRsp);

        SetUpAvatarTeamRsp.Builder proto =
                SetUpAvatarTeamRsp.newBuilder()
                        .setRetcode(RETCODE_SUCC)
                        .setTeamId(teamId)
                        .setCurAvatarGuid(player.getTeamManager().getCurrentCharacterGuid());

        teamInfo.getAvatars().forEach(avatarId -> proto.addAvatarTeamGuidList(player.getAvatars().getAvatarById(avatarId).getGuid()));
        this.setData(proto.build());
    }

    /**
     * Sends only retcode (Sanity checks).
     * @param retcode The retcode.
     */
    public PacketSetUpAvatarTeamRsp(int retcode) {
        super(PacketIds.SetUpAvatarTeamRsp);

        SetUpAvatarTeamRsp proto =
                SetUpAvatarTeamRsp.newBuilder()
                        .setRetcode(retcode)
                        .build();

        this.setData(proto);
    }
}