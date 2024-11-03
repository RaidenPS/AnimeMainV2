package raidengame.connection.packets.send.game.world;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.game.player.Player;

// Protocol buffers
import raidengame.cache.protobuf.PlayerTimeNotifyOuterClass.PlayerTimeNotify;

/**
 * Packet to send both player and server time in current world.
 */
public class PacketPlayerTimeNotify extends BasePacket {
    public PacketPlayerTimeNotify(Player player) {
        super(PacketIds.PlayerTimeNotify);

        PlayerTimeNotify proto =
                PlayerTimeNotify.newBuilder()
                        .setIsPaused(player.isPaused())
                        .setPlayerTime(player.getClientTime())
                        .setServerTime(System.currentTimeMillis())
                        .build();

        this.setData(proto);
    }
}