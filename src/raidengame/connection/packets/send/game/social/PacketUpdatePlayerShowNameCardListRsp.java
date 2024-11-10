package raidengame.connection.packets.send.game.social;

// Imports
import static raidengame.connection.base.PacketRetcodes.RETCODE_SUCC;
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import java.util.List;

// Protocol buffers
import raidengame.cache.protobuf.UpdatePlayerShowNameCardListRspOuterClass.UpdatePlayerShowNameCardListRsp;

/**
 * Packet to update player's name cards in profile.
 */
public class PacketUpdatePlayerShowNameCardListRsp extends BasePacket {
    public PacketUpdatePlayerShowNameCardListRsp(List<Integer> nameCardIds) {
        super(PacketIds.UpdatePlayerShowNameCardListRsp);

        UpdatePlayerShowNameCardListRsp proto =
                UpdatePlayerShowNameCardListRsp.newBuilder()
                        .setRetcode(RETCODE_SUCC)
                        .addAllShowNameCardIdList(nameCardIds)
                        .build();

        this.setData(proto);
    }
}