package raidengame.connection.packets.send.scene;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.game.world.Scene;

// Protocol buffers
import raidengame.cache.protobuf.SceneTimeNotifyOuterClass.SceneTimeNotify;

/**
 * Sends a packet for change player's birthday.
 */
public class PacketSceneTimeNotify extends BasePacket {
    public PacketSceneTimeNotify(Scene scene) {
        super(PacketIds.SceneTimeNotify);

        SceneTimeNotify proto =
                SceneTimeNotify.newBuilder()
                        .setSceneId(scene.getId())
                        .setSceneTime(scene.getSceneTime())
                        .setIsPaused(scene.isPaused())
                        .build();

        this.setData(proto);
    }
}