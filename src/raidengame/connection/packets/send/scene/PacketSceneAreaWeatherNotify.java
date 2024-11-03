package raidengame.connection.packets.send.scene;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.game.player.Player;

// Protocol buffers
import raidengame.cache.protobuf.SceneAreaWeatherNotifyOuterClass.SceneAreaWeatherNotify;

/**
 * Packet to send player's weather in his world.
 */
public class PacketSceneAreaWeatherNotify extends BasePacket {
    public PacketSceneAreaWeatherNotify(Player player) {
        super(PacketIds.SceneAreaWeatherNotify);

        SceneAreaWeatherNotify proto =
                SceneAreaWeatherNotify.newBuilder()
                        .setWeatherAreaId(player.getWeatherId())
                        .setClimateType(player.getClimate().getValue())
                        .setTransDuration(0.5f)
                        .build();

        this.setData(proto);
    }
}