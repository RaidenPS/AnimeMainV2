package raidengame.connection.packets.send.scene;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.enums.game.scene.EnterReason;
import raidengame.enums.game.scene.EnterType;
import raidengame.game.player.Player;
import raidengame.game.player.SceneLoadState;
import raidengame.game.world.Position;
import raidengame.misc.classes.Randomizer;

// Protocol buffers
import raidengame.cache.protobuf.PlayerEnterSceneNotifyOuterClass.PlayerEnterSceneNotify;

/**
 * Packet for send enter the world or teleport to another scene.
 */
public class PacketPlayerEnterSceneNotify extends BasePacket {
    public PacketPlayerEnterSceneNotify(Player player, int worldType) {
        super(PacketIds.PlayerEnterSceneNotify);

        player.setEnterSceneToken(Randomizer.randomRange(1000, 99999));
        player.setSceneLoadState(SceneLoadState.LOADING);

        PlayerEnterSceneNotify proto =
            PlayerEnterSceneNotify.newBuilder()
                .setIsFirstLoginEnterScene(player.isHasSentLoginPackets())
                .setSceneBeginTime(System.currentTimeMillis())
                .setSceneId(player.getSceneId())
                .setPrevSceneId(player.getPrevSceneId())
                .setEnterSceneToken(player.getEnterSceneToken())
                .setType(EnterType.SELF.getValue())
                .setTargetUid(player.getUid())
                .setPos(player.getPosition().toProto())
                .setWorldLevel(player.getWorldLevel())
                .setWorldType(worldType)
                .setEnterReason(EnterReason.LOGIN.getValue())
                .setSceneTransaction(
                        "3-"
                                + player.getUid()
                                + "-"
                                + (int) (System.currentTimeMillis() / 1000)
                                + "-"
                                + 18402)
                .build();

        this.setData(proto);
    }

    public PacketPlayerEnterSceneNotify(Player player, EnterType type, EnterReason reason, int newScene, Position newPos) {
        super(PacketIds.PlayerEnterSceneNotify);

        player.setPrevSceneId(player.getSceneId());
        player.setEnterSceneToken(Randomizer.randomRange(1000, 99999));
        player.setSceneLoadState(SceneLoadState.LOADING);

        PlayerEnterSceneNotify proto =
                PlayerEnterSceneNotify.newBuilder()
                        .setIsFirstLoginEnterScene(player.isHasSentLoginPackets())
                        .setSceneBeginTime(System.currentTimeMillis())
                        .setSceneId(newScene)
                        .setPrevSceneId(player.getPrevSceneId())
                        .setEnterSceneToken(player.getEnterSceneToken())
                        .setType(type.getValue())
                        .setTargetUid(player.getUid())
                        .setPos(newPos.toProto())
                        .setWorldLevel(player.getWorldLevel())
                        .setWorldType(player.getWorld().getWorldType())
                        .setEnterReason(reason.getValue())
                        .setSceneTransaction(
                                "3-"
                                        + player.getUid()
                                        + "-"
                                        + (int) (System.currentTimeMillis() / 1000)
                                        + "-"
                                        + 18402)
                        .build();

        this.setData(proto);
    }
}
