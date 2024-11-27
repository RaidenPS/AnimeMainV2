package raidengame.connection.packets.send.game;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import java.util.Map;

// Protocol buffers
import raidengame.cache.protobuf.OpenStateChangeNotifyOuterClass.OpenStateChangeNotify;

/**
 * Packet to change the openstate of current player.
 */
public class PacketOpenStateChangeNotify extends BasePacket {
    /**
     * Change the openstate by given Map of openstates.
     * @param map The given map.
     */
    public PacketOpenStateChangeNotify(Map<Integer, Integer> map) {
        super(PacketIds.OpenStateChangeNotify);

        OpenStateChangeNotify proto =
                OpenStateChangeNotify.newBuilder()
                        .putAllOpenStateMap(map)
                        .build();

        this.setData(proto);
    }

    /**
     * Change the openstate by given id and value.
     * @param openState The openstate id.
     * @param value Value to apply (0 - OFF, 1 - ON)
     */
    public PacketOpenStateChangeNotify(int openState, int value) {
        super(PacketIds.OpenStateChangeNotify);

        OpenStateChangeNotify proto =
                OpenStateChangeNotify.newBuilder()
                        .putOpenStateMap(openState, value)
                        .build();

        this.setData(proto);
    }
}