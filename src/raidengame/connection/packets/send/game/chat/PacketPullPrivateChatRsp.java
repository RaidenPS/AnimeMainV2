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
    public PacketPullPrivateChatRsp(List<ChatInfo> history, int retcode) {
        super(PacketIds.PullPrivateChatRsp);

        PullPrivateChatRsp proto =
                PullPrivateChatRsp.newBuilder()
                        .setRetcode(retcode)
                        .addAllChatInfo((history == null) ? List.of() : history)
                        .build();

        this.setData(proto);
    }
}