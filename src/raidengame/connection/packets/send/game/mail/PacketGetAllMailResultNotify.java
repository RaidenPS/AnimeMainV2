package raidengame.connection.packets.send.game.mail;

// Imports
import static raidengame.connection.base.PacketRetcodes.RETCODE_SUCC;
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.game.player.Player;
import raidengame.misc.classes.Timestamp;
import java.time.Instant;

// Protocol buffers
import raidengame.cache.protobuf.GetAllMailResultNotifyOuterClass.GetAllMailResultNotify;

/**
 * Packet to show all mails in the mailbox.
 */
public class PacketGetAllMailResultNotify extends BasePacket {
    public PacketGetAllMailResultNotify(Player player, boolean is_collected) {
        super(PacketIds.GetAllMailResultNotify);

        GetAllMailResultNotify proto =
                GetAllMailResultNotify.newBuilder()
                        .setRetcode(RETCODE_SUCC)
                        .setTransaction(player.getUid() + "-" + Timestamp.getCurrentSeconds() + "-" + 0)
                        .setIsCollected(is_collected)
                        .setPageIndex(1)
                        .setTotalPageCount(1)
                        .addAllMailList(
                                player.getAllMail().stream()
                                        .filter(mail -> mail.stateValue == 1)
                                        .filter(mail -> mail.expireTime > Instant.now().getEpochSecond())
                                        .map(mail -> mail.toProto(player))
                                        .toList())
                        .build();

        this.setData(proto);
    }
}