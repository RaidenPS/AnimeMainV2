package raidengame.connection.packets.send.game;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;

// Protocol buffers
import raidengame.cache.protobuf.ServerDisconnectClientNotifyOuterClass.ServerDisconnectClientNotify;

/**
 * Sends a packet for disconnect the player from the server.
 */
public class PacketServerDisconnectClientNotify extends BasePacket {
    public PacketServerDisconnectClientNotify() {
        super(PacketIds.ServerDisconnectClientNotify);

        ServerDisconnectClientNotify proto =
                ServerDisconnectClientNotify.newBuilder()
                    .setData(1)
                    .build();

        this.setData(proto);
    }
}