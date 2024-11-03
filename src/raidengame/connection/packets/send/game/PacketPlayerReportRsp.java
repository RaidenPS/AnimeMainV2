package raidengame.connection.packets.send.game;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;

// Protocol buffers
import raidengame.cache.protobuf.PlayerReportRspOuterClass.PlayerReportRsp;

/**
 * Packet to send a player report.
 */
public class PacketPlayerReportRsp extends BasePacket {
    public PacketPlayerReportRsp(int target_uid, int cooldown, int retcode) {
        super(PacketIds.PlayerReportRsp);

        PlayerReportRsp proto =
                PlayerReportRsp.newBuilder()
                        .setRetcode(retcode)
                        .setTargetUid(target_uid)
                        .setCdTime(cooldown)
                        .build();

        this.setData(proto);
    }
}