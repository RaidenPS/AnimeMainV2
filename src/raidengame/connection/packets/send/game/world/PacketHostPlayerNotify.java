package raidengame.connection.packets.send.game.world;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.game.world.World;

// Protocol buffers
import raidengame.cache.protobuf.HostPlayerNotifyOuterClass.HostPlayerNotify;

/**
 * Packet for init host's world for future CO-OP requests. (CO-OP Initialization #1)
 */
public class PacketHostPlayerNotify extends BasePacket {
    public PacketHostPlayerNotify(World world) {
        super(PacketIds.HostPlayerNotify);

        HostPlayerNotify proto =
                HostPlayerNotify.newBuilder()
                        .setHostUid(world.getHost().getUid())
                        .setHostPeerId(world.getHostPeerId())
                        .build();

        this.setData(proto);
    }
}