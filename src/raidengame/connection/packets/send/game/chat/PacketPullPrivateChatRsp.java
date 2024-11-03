package raidengame.connection.packets.send.game.chat;

// Imports
import raidengame.connection.base.*;
import java.util.List;

// Protocol buffers
import raidengame.cache.protobuf.ChatInfoOuterClass.ChatInfo;
import raidengame.cache.protobuf.PullPrivateChatRspOuterClass.PullPrivateChatRsp;

/**
 * Packet to send response from PullPrivateChatReq.
 */
public class PacketPullPrivateChatRsp extends BasePacket {
    public PacketPullPrivateChatRsp(List<ChatInfo> history) {
        super(PacketIds.PullPrivateChatRsp);

        PullPrivateChatRsp proto =
                PullPrivateChatRsp.newBuilder()
                        .setRetcode((history == null) ? PacketRetcodes.RETCODE_FAIL : PacketRetcodes.RETCODE_SUCC)
                        .addAllChatInfo(history)
                        .build();

        this.setData(proto);
    }
}