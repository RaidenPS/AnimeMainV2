package raidengame.connection.packets.send.game.chat;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import java.util.List;

// Protocol buffers
import raidengame.cache.protobuf.GetChatEmojiCollectionRspOuterClass.GetChatEmojiCollectionRsp;
import raidengame.cache.protobuf.ChatEmojiCollectionDataOuterClass.ChatEmojiCollectionData;

/**
 * Packet to send the emoji collection in the chat.
 */
public class PacketGetChatEmojiCollectionRsp extends BasePacket {
    public PacketGetChatEmojiCollectionRsp(List<Integer> emoteIds) {
        super(PacketIds.GetChatEmojiCollectionRsp);

        GetChatEmojiCollectionRsp proto =
                GetChatEmojiCollectionRsp.newBuilder()
                        .setChatEmojiCollectionData(ChatEmojiCollectionData.newBuilder().addAllEmojiIdList(emoteIds).build())
                        .build();

        this.setData(proto);
    }
}