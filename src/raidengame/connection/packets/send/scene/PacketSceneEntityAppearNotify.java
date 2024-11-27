package raidengame.connection.packets.send.scene;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.game.entity.EntityAvatar;
import raidengame.game.entity.GameEntity;
import java.util.Collection;

// Protocol buffers
import raidengame.cache.protobuf.VisionTypeOuterClass.VisionType;
import raidengame.cache.protobuf.SceneEntityAppearNotifyOuterClass.SceneEntityAppearNotify;

/**
 * Creates a new entity in current scene.
 */
public class PacketSceneEntityAppearNotify extends BasePacket {
    /**
     * Creates an entity by given object.
     * @param entity The given object.
     */
    public PacketSceneEntityAppearNotify(GameEntity entity) {
        super(PacketIds.SceneEntityAppearNotify, true);

        SceneEntityAppearNotify.Builder proto =
                SceneEntityAppearNotify.newBuilder()
                        .setAppearType(VisionType.VISION_BORN)
                        .addEntityList(entity.toProto());

        this.setData(proto.build());
    }

    /**
     * Creates an entity by given object and vision type.
     * @param entities The given list of all entities.
     * @param visionType The vision type.
     */
    public PacketSceneEntityAppearNotify(Collection<? extends GameEntity> entities, VisionType visionType) {
        super(PacketIds.SceneEntityAppearNotify, true);

        SceneEntityAppearNotify.Builder proto =
                SceneEntityAppearNotify.newBuilder().setAppearType(visionType);

        entities.forEach(e -> proto.addEntityList(e.toProto()));

        this.setData(proto.build());
    }

    /**
     * Creates an entity by given object and vision type.
     * @param entity The given list of all entities.
     * @param visionType The vision type.
     */
    public PacketSceneEntityAppearNotify(GameEntity entity, VisionType visionType) {
        super(PacketIds.SceneEntityAppearNotify, true);

        SceneEntityAppearNotify.Builder proto =
                SceneEntityAppearNotify.newBuilder()
                        .addEntityList(entity.toProto())
                        .setAppearType(visionType);

        this.setData(proto.build());
    }

    public PacketSceneEntityAppearNotify(EntityAvatar entity, VisionType visionType, int param) {
        super(PacketIds.SceneEntityAppearNotify, true);

        SceneEntityAppearNotify.Builder proto =
                SceneEntityAppearNotify.newBuilder()
                        .addEntityList(entity.toProto())
                        .setParam(param)
                        .setAppearType(visionType);

        this.setData(proto.build());
    }
}