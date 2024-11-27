package raidengame.connection.packets.send.game.chat;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;

// Protocol buffers
import raidengame.cache.protobuf.SetChatEmojiCollectionRspOuterClass.SetChatEmojiCollectionRsp;

/**
 * Packet to change emoji collection in the chat.
 */
public class PacketSetChatEmojiCollectionRsp extends BasePacket {
    public PacketSetChatEmojiCollectionRsp(int retcode) {
        super(PacketIds.SetChatEmojiCollectionRsp);

        SetChatEmojiCollectionRsp proto =
                SetChatEmojiCollectionRsp.newBuilder()
                        .setRetcode(retcode)
                        .build();

        this.setData(proto);
    }
}