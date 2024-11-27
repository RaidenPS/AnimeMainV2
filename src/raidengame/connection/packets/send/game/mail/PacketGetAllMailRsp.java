package raidengame.connection.packets.send.game.mail;

// Imports
import static raidengame.connection.base.PacketRetcodes.RETCODE_SUCC;
import raidengame.configuration.GameConstants;
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.game.player.Player;
import java.time.Instant;

// Protocol buffers
import raidengame.cache.protobuf.GetAllMailRspOuterClass.GetAllMailRsp;

/**
 * Packet to send all mails in the mailbox.
 * @deprecated Yes
 */
public class PacketGetAllMailRsp extends BasePacket {
    public PacketGetAllMailRsp(Player player, boolean is_collected) {
        super(PacketIds.GetAllMailRsp);

        var inbox = player.getAllMail();
        GetAllMailRsp proto =
                GetAllMailRsp.newBuilder()
                        .setRetcode(RETCODE_SUCC)
                        .setIsCollected(is_collected)
                        .setIsTruncated(inbox.size() > GameConstants.MAILBOX_MAX_LEN)
                        .addAllMailList(inbox.stream()
                                .filter(mail -> mail.expireTime > Instant.now().getEpochSecond())
                                .map(mail -> mail.toProto(player)).toList())
                        .build();

        this.setData(proto);
    }
}