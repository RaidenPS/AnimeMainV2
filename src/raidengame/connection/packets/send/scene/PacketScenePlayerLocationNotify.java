package raidengame.connection.packets.send.scene;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.game.player.Player;
import raidengame.game.world.Scene;

// Protocol buffers
import raidengame.cache.protobuf.ScenePlayerLocationNotifyOuterClass.ScenePlayerLocationNotify;

/**
 * Packet to send player's location on player's scene.
 */
public class PacketScenePlayerLocationNotify extends BasePacket {
    public PacketScenePlayerLocationNotify(Scene scene) {
        super(PacketIds.ScenePlayerLocationNotify);

        ScenePlayerLocationNotify.Builder proto =
                ScenePlayerLocationNotify.newBuilder()
                        .setSceneId(scene.getId());

        for (Player p : scene.getPlayers()) {
            proto.addPlayerLocList(p.getPlayerLocationInfo());
        }

        this.setData(proto.build());
    }
}