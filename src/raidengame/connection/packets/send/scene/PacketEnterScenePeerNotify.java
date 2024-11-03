package raidengame.connection.packets.send.scene;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.game.player.Player;

// Protocol buffers
import raidengame.cache.protobuf.EnterScenePeerNotifyOuterClass.EnterScenePeerNotify;

/**
 * Packet for send to gameworld while entering the host's world. (CO-OP Initialization #2)
 */
public class PacketEnterScenePeerNotify extends BasePacket {
    public PacketEnterScenePeerNotify(Player player) {
        super(PacketIds.EnterScenePeerNotify);

        EnterScenePeerNotify proto =
                EnterScenePeerNotify.newBuilder()
                        .setDestSceneId(player.getSceneId())
                        .setPeerId(player.getPeerId())
                        .setHostPeerId(player.getWorld().getHost().getPeerId())
                        .setEnterSceneToken(player.getEnterSceneToken())
                        .build();

        this.setData(proto);
    }
}