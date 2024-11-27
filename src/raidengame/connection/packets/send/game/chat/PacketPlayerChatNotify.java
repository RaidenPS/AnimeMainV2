package raidengame.connection.packets.send.game.chat;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.game.player.Player;
import raidengame.misc.classes.Timestamp;

// Protocol buffers
import raidengame.cache.protobuf.ChatInfoOuterClass.ChatInfo;
import raidengame.cache.protobuf.PlayerChatNotifyOuterClass.PlayerChatNotify;
import raidengame.cache.protobuf.SystemHintOuterClass.SystemHint;

/**
 * Sends a notify in co-op chat.
 */
public class PacketPlayerChatNotify extends BasePacket {
    public PacketPlayerChatNotify(Player sender, int channelId, SystemHint systemHint) {
        super(PacketIds.PlayerChatNotify);

        ChatInfo info =
                ChatInfo.newBuilder()
                        .setTime(Timestamp.getCurrentSeconds())
                        .setUid(sender.getUid())
                        .setSystemHint(systemHint)
                        .build();

        PlayerChatNotify proto =
                PlayerChatNotify.newBuilder()
                        .setChannelId(channelId)
                        .setChatInfo(info)
                        .build();

        this.setData(proto);
    }
}