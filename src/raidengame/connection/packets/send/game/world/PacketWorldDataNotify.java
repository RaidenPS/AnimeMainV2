package raidengame.connection.packets.send.game.world;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.game.world.World;

// Protocol buffers
import raidengame.cache.protobuf.PropValueOuterClass.PropValue;
import raidengame.cache.protobuf.WorldDataNotifyOuterClass.WorldDataNotify;

/**
 * Packet for send world data (WR and isMultiplayer).
 */
public class PacketWorldDataNotify extends BasePacket {
    public PacketWorldDataNotify(World world) {
        super(PacketIds.WorldDataNotify);

        int worldLevel = world.getWorldLevel();
        int isMp = world.isMultiplayer() ? 1 : 0;

        WorldDataNotify proto =
                WorldDataNotify.newBuilder()
                        .putWorldPropMap(1, PropValue.newBuilder().setType(1).setIval(worldLevel).setVal(worldLevel).build())
                        .putWorldPropMap(2, PropValue.newBuilder().setType(2).setIval(isMp).setVal(isMp).build())
                        .build();

        this.setData(proto);
    }
}