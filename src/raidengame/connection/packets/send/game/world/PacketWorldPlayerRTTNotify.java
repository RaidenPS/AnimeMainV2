package raidengame.connection.packets.send.game.world;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.game.player.Player;
import raidengame.game.world.World;

// Protocol buffers
import raidengame.cache.protobuf.PlayerRTTInfoOuterClass.PlayerRTTInfo;
import raidengame.cache.protobuf.WorldPlayerRTTNotifyOuterClass.WorldPlayerRTTNotify;

/**
 * Packet for send player's ping (RTT) in his world.
 */
public class PacketWorldPlayerRTTNotify extends BasePacket {
    public PacketWorldPlayerRTTNotify(World world) {
        super(PacketIds.WorldPlayerRTTNotify);

        WorldPlayerRTTNotify.Builder proto = WorldPlayerRTTNotify.newBuilder();
        for (Player player : world.getPlayers()) {
            proto.addPlayerRttList(
                    PlayerRTTInfo.newBuilder()
                            .setUid(player.getUid())
                            .setRtt(player.getPing())
            );
        }
        this.setData(proto.build());
    }
}