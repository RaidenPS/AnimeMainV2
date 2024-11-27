package raidengame.connection.packets.send.game.team;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;

// Protocol buffers
import raidengame.cache.protobuf.ChooseCurAvatarTeamRspOuterClass.ChooseCurAvatarTeamRsp;

/**
 * Packet to deploy a team.
 */
public class PacketChooseCurAvatarTeamRsp extends BasePacket {
    public PacketChooseCurAvatarTeamRsp(int retcode, int teamId) {
        super(PacketIds.ChooseCurAvatarTeamRsp);

        ChooseCurAvatarTeamRsp proto =
                ChooseCurAvatarTeamRsp.newBuilder()
                        .setRetcode(retcode)
                        .setCurTeamId(teamId)
                        .build();

        this.setData(proto);
    }
}