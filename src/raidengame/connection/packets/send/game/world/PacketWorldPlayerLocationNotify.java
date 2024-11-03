package raidengame.connection.packets.send.game.world;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.game.player.Player;
import raidengame.game.world.World;

// Protocol buffers
import raidengame.cache.protobuf.WorldPlayerLocationNotifyOuterClass.WorldPlayerLocationNotify;

/**
 * Packet for send player's location in the world.
 */
public class PacketWorldPlayerLocationNotify extends BasePacket {
    public PacketWorldPlayerLocationNotify(World world) {
        super(PacketIds.WorldPlayerLocationNotify);

        WorldPlayerLocationNotify.Builder proto = WorldPlayerLocationNotify.newBuilder();
        for (Player p : world.getPlayers()) {
            proto.addPlayerWorldLocList(p.getWorldPlayerLocationInfo());
        }

        this.setData(proto.build());
    }
}