package raidengame.connection.packets.send.game.social;

// Imports
import static raidengame.connection.base.PacketRetcodes.RETCODE_SUCC;
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import java.util.Set;

// Protocol buffers
import raidengame.cache.protobuf.GetAllUnlockNameCardRspOuterClass.GetAllUnlockNameCardRsp;

/**
 * Packet to show all unlocked name cards.
 */
public class PacketGetAllUnlockNameCardRsp extends BasePacket {
    public PacketGetAllUnlockNameCardRsp(Set<Integer> nameCards) {
        super(PacketIds.GetAllUnlockNameCardRsp);

        GetAllUnlockNameCardRsp proto =
                GetAllUnlockNameCardRsp.newBuilder()
                        .setRetcode(RETCODE_SUCC)
                        .addAllNameCardList(nameCards)
                        .build();

        this.setData(proto);
    }
}