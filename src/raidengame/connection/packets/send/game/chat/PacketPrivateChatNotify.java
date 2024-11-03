package raidengame.connection.packets.send.game.chat;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;

// Protocol buffers
import raidengame.cache.protobuf.ChatInfoOuterClass.ChatInfo;
import raidengame.cache.protobuf.PrivateChatNotifyOuterClass.PrivateChatNotify;

/**
 * Sends a message or emote in chat.
 */
public class PacketPrivateChatNotify extends BasePacket {
    public PacketPrivateChatNotify(ChatInfo message) {
        super(PacketIds.PrivateChatNotify);

        PrivateChatNotify proto =
                PrivateChatNotify.newBuilder()
                        .setChatInfo(message)
                        .build();

        this.setData(proto);
    }
}