package raidengame.connection.packets.send.game.multiplayer;

// Imports
import static raidengame.connection.base.PacketRetcodes.RETCODE_SUCC;
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import java.util.List;

// Protocol buffers
import raidengame.cache.protobuf.FriendBriefOuterClass.FriendBrief;
import raidengame.cache.protobuf.GetRecentMpPlayerListRspOuterClass.GetRecentMpPlayerListRsp;

/**
 * Packet to send all co-op requests.
 */
public class PacketGetRecentMpPlayerListRsp extends BasePacket {
    public PacketGetRecentMpPlayerListRsp(List<FriendBrief> requests) {
        super(PacketIds.GetRecentMpPlayerListRsp);

        GetRecentMpPlayerListRsp proto =
                GetRecentMpPlayerListRsp.newBuilder()
                        .setRetcode(RETCODE_SUCC)
                        .addAllRecentMpPlayerBriefList(requests)
                        .build();

        this.setData(proto);
    }
}