package raidengame.connection.packets.send.game;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.game.player.Player;

// Protocol buffers
import raidengame.cache.protobuf.OpenStateUpdateNotifyOuterClass.OpenStateUpdateNotify;

/**
 * Packet to load the openstates on given player.
 */
public class PacketOpenStateUpdateNotify extends BasePacket {
    public PacketOpenStateUpdateNotify(Player player) {
        super(PacketIds.OpenStateUpdateNotify);

        OpenStateUpdateNotify.Builder proto =
                OpenStateUpdateNotify.newBuilder()
                        .putAllOpenStateMap(player.getUnlockedOpenStates());

        this.setData(proto);
    }
}