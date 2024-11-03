package raidengame.connection.packets.send.game;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;

// Protocol buffers
import raidengame.cache.protobuf.ServerTimeNotifyOuterClass.ServerTimeNotify;

/**
 * Packet for send server's time.
 */
public class PacketServerTimeNotify extends BasePacket {
    public PacketServerTimeNotify() {
        super(PacketIds.ServerTimeNotify);

        ServerTimeNotify proto =
                ServerTimeNotify.newBuilder()
                        .setServerTime(System.currentTimeMillis())
                        .build();

        this.setData(proto);
    }
}