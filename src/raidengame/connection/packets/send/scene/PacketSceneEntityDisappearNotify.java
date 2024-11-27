package raidengame.connection.packets.send.scene;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.game.entity.GameEntity;

// Protocol buffers
import raidengame.cache.protobuf.SceneEntityDisappearNotifyOuterClass.SceneEntityDisappearNotify;
import raidengame.cache.protobuf.VisionTypeOuterClass.VisionType;

/**
 * Removes an entity from current scene.
 */
public class PacketSceneEntityDisappearNotify extends BasePacket {
    public PacketSceneEntityDisappearNotify(GameEntity entity, VisionType disappearType) {
        super(PacketIds.SceneEntityDisappearNotify);

        SceneEntityDisappearNotify proto =
                SceneEntityDisappearNotify.newBuilder()
                        .setDisappearType(disappearType)
                        .addEntityList(entity.getId())
                        .build();

        this.setData(proto);
    }
}