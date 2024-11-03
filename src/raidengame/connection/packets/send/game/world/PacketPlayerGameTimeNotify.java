package raidengame.connection.packets.send.game.world;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.game.player.Player;

// Protocol buffers
import raidengame.cache.protobuf.PlayerGameTimeNotifyOuterClass.PlayerGameTimeNotify;

/**
 * Packet for send player's game time in the world.
 */
public class PacketPlayerGameTimeNotify extends BasePacket {
    public PacketPlayerGameTimeNotify(Player player) {
        super(PacketIds.PlayerGameTimeNotify);

        PlayerGameTimeNotify proto =
            PlayerGameTimeNotify.newBuilder()
                .setUid(player.getUid())
                .setGameTime((int)player.getWorld().getTotalGameTimeMinutes())
                .setIsHome(player.getHome() != null)
                .build();

        this.setData(proto);
    }
}