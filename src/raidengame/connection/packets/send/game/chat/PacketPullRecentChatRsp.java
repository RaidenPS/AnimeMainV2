package raidengame.connection.packets.send.game.chat;

// Imports
import static raidengame.connection.base.PacketRetcodes.RETCODE_SUCC;
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import java.util.List;

// Protocol buffers
import raidengame.cache.protobuf.ChatInfoOuterClass.ChatInfo;
import raidengame.cache.protobuf.PullRecentChatRspOuterClass.PullRecentChatRsp;

/**
 * Packet to send recent history from chat.
 */
public class PacketPullRecentChatRsp extends BasePacket {
    public PacketPullRecentChatRsp(List<ChatInfo> chatHistory) {
        super(PacketIds.PullRecentChatRsp);

        PullRecentChatRsp proto =
                PullRecentChatRsp.newBuilder()
                        .setRetcode(RETCODE_SUCC)
                        .addAllChatInfo(chatHistory)
                        .build();

        this.setData(proto);
    }
}