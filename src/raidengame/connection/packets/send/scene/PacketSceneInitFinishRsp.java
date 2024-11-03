package raidengame.connection.packets.send.scene;

// Imports
import static raidengame.connection.base.PacketRetcodes.RETCODE_SUCC;
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.game.player.Player;

// Protocol buffers
import raidengame.cache.protobuf.SceneInitFinishRspOuterClass.SceneInitFinishRsp;

/**
 * Packet to init the scene.
 */
public class PacketSceneInitFinishRsp extends BasePacket {
    public PacketSceneInitFinishRsp(Player player) {
        super(PacketIds.SceneInitFinishRsp);

        SceneInitFinishRsp proto =
                SceneInitFinishRsp.newBuilder()
                        .setRetcode(RETCODE_SUCC)
                        .setEnterSceneToken(player.getEnterSceneToken())
                        .build();

        this.setData(proto);
    }
}