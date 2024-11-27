package raidengame.connection.packets.send.game.team;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;

// Protocol buffers
import raidengame.cache.protobuf.ChangeTeamNameRspOuterClass.ChangeTeamNameRsp;

/**
 * Packet to change team's name.
 */
public class PacketChangeTeamNameRsp extends BasePacket {
    public PacketChangeTeamNameRsp(int retcode, int teamId, String teamName) {
        super(PacketIds.ChangeTeamNameRsp);

        ChangeTeamNameRsp proto =
                ChangeTeamNameRsp.newBuilder()
                        .setRetcode(retcode)
                        .setTeamId(teamId)
                        .setTeamName(teamName)
                        .build();

        this.setData(proto);
    }
}