package raidengame.connection.packets.receive.game;

// Imports
import raidengame.configuration.ConfigManager;
import raidengame.connection.GameSession;
import raidengame.connection.base.*;
import raidengame.game.chat.ServerBot;
import raidengame.game.player.Player;
import raidengame.misc.WordFilter;
import raidengame.misc.classes.Timestamp;

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

        // cooldown timer
        if(Timestamp.getCurrentSeconds() - player.getReportCurrentTimestamp() < ConfigManager.serverConfig.gameInfo.reportCooldown) {
            session.send(new PacketPlayerReportRsp(req.getTargetUid(), player.getReportCurrentTimestamp(), PacketRetcodes.RET_REPORT_CD));
            return;
        }
        else {
            player.setReportCurrentTimestamp(Timestamp.getCurrentSeconds());
        }

        // bad word or report the console.
        if(WordFilter.checkIsBadWord(req.getContent()) || target_uid == ServerBot.UID) {
            session.send(new PacketPlayerReportRsp(req.getTargetUid(), 0, PacketRetcodes.RET_REPORT_CONTENT_ILLEGAL));
            return;
        }

        /// TODO: Do something like save reports in file or show in game chat.
        session.send(new PacketPlayerReportRsp(target_uid, 0, PacketRetcodes.RETCODE_SUCC));
    }
}