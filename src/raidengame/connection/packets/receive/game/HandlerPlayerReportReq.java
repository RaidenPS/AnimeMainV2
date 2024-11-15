package raidengame.connection.packets.receive.game;

// Imports
import raidengame.Main;
import raidengame.configuration.ConfigManager;
import raidengame.connection.GameSession;
import raidengame.connection.base.*;
import raidengame.game.chat.ServerBot;
import raidengame.game.player.Player;
import raidengame.misc.WordFilter;
import raidengame.misc.classes.Timestamp;
import java.util.Objects;

// Packets
import raidengame.connection.packets.send.game.PacketPlayerReportRsp;

// Protocol buffers
import raidengame.cache.protobuf.PlayerReportReqOuterClass.PlayerReportReq;

/**
 * Handler for report a player.
 */
@PacketOpcode(PacketIds.PlayerReportReq)
public class HandlerPlayerReportReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        PlayerReportReq req = PlayerReportReq.parseFrom(data);
        int target_uid = req.getTargetUid();
        Player player = session.getPlayer();

        // self report
        if(target_uid == ServerBot.UID || req.getTargetUid() == session.getPlayer().getUid()) {
            session.send(new PacketPlayerReportRsp(req.getTargetUid(), player.getReportCurrentTimestamp(), PacketRetcodes.RETCODE_FAIL));
            return;
        }

        // cooldown timer
        if(Timestamp.getCurrentSeconds() - player.getReportCurrentTimestamp() < ConfigManager.serverConfig.gameInfo.reportCooldown) {
            session.send(new PacketPlayerReportRsp(req.getTargetUid(), player.getReportCurrentTimestamp(), PacketRetcodes.RET_REPORT_CD));
            return;
        }
        else {
            player.setReportCurrentTimestamp(Timestamp.getCurrentSeconds());
        }

        // bad word or report the console.
        if((WordFilter.checkIsBadWord(req.getContent()) && !req.getContent().isEmpty())) {
            session.send(new PacketPlayerReportRsp(req.getTargetUid(), 0, PacketRetcodes.RET_REPORT_CONTENT_ILLEGAL));
            return;
        }

        // save all reports to file.
        var reporter = session.getPlayer();
        var server = session.getServer();
        Main.getLogger().info(String.format("[Report] %s reported the player %s for %s (%s) -> %s", reporter.getNickname(), Objects.requireNonNull(server.getPlayerByUid(req.getTargetUid(), true)).getNickname(), req.getReason(), req.getSubreason(), req.getContent()));

        session.send(new PacketPlayerReportRsp(target_uid, 0, PacketRetcodes.RETCODE_SUCC));
    }
}