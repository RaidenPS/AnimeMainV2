package raidengame.connection.packets.send.game.mail;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.game.player.Player;
import java.util.List;

// Protocol buffers
import raidengame.cache.protobuf.DelMailRspOuterClass.DelMailRsp;

/**
 * Packet to delete mail from the mailbox.
 */
public class PacketDelMailRsp extends BasePacket {
    public PacketDelMailRsp(List<Integer> toDeleteIds, int retcode) {
        super(PacketIds.DelMailRsp);

        DelMailRsp proto =
                DelMailRsp.newBuilder()
                        .setRetcode(retcode)
                        .addAllMailIdList(toDeleteIds)
                        .build();

        this.setData(proto);
    }
}