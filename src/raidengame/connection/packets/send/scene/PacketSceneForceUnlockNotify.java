package raidengame.connection.packets.send.scene;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import java.util.Collection;

// Protocol buffers
import raidengame.cache.protobuf.SceneForceUnlockNotifyOuterClass.SceneForceUnlockNotify;

/**
 * Packet to send forcefully unlock a scene.
 */
public class PacketSceneForceUnlockNotify extends BasePacket {
    public PacketSceneForceUnlockNotify() {
        super(PacketIds.SceneForceUnlockNotify);

        SceneForceUnlockNotify proto = SceneForceUnlockNotify.newBuilder().build();
        this.setData(proto);
    }

    public PacketSceneForceUnlockNotify(Collection<Integer> unlocked, boolean isAdd) {
        super(PacketIds.SceneForceUnlockNotify);

        SceneForceUnlockNotify proto =
            SceneForceUnlockNotify.newBuilder()
                .addAllForceIdList(unlocked)
                .setIsAdd(isAdd)
                .build();

        this.setData(proto);
    }

    public PacketSceneForceUnlockNotify(int unlocked, boolean isAdd) {
        super(PacketIds.SceneForceUnlockNotify);

        SceneForceUnlockNotify proto =
            SceneForceUnlockNotify.newBuilder()
                .addForceIdList(unlocked)
                .setIsAdd(isAdd)
                .build();

        this.setData(proto);
    }
}