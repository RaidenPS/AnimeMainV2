package raidengame.connection.packets.send.game.world;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.game.player.Player;
import raidengame.game.world.World;

// Protocol buffers
import raidengame.cache.protobuf.WorldPlayerInfoNotifyOuterClass.WorldPlayerInfoNotify;

/**
 * Packet to send all players information in current world.
 */
public class PacketWorldPlayerInfoNotify extends BasePacket {
    public PacketWorldPlayerInfoNotify(World world) {
        super(PacketIds.WorldPlayerInfoNotify);

        WorldPlayerInfoNotify.Builder proto = WorldPlayerInfoNotify.newBuilder();
        for (int i = 0; i < world.getPlayers().size(); i++) {
            Player player = world.getPlayers().get(i);

            proto.addPlayerInfoList(player.getOnlinePlayerInfo());
            proto.addPlayerUidList(player.getUid());
        }
        this.setData(proto.build());
    }
}