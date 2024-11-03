package raidengame.connection.packets.send.scene;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.game.player.Player;
import raidengame.game.world.World;

// Protocol buffers
import raidengame.cache.protobuf.ScenePlayerInfoNotifyOuterClass.ScenePlayerInfoNotify;
import raidengame.cache.protobuf.ScenePlayerInfoOuterClass.ScenePlayerInfo;

/**
 * Packet to send player info on player's scene.
 */
public class PacketScenePlayerInfoNotify extends BasePacket {
    public PacketScenePlayerInfoNotify(World world) {
        super(PacketIds.ScenePlayerInfoNotify);

        ScenePlayerInfoNotify.Builder proto = ScenePlayerInfoNotify.newBuilder();
        for (int i = 0; i < world.getPlayers().size(); i++) {
            Player p = world.getPlayers().get(i);
            proto.addPlayerInfoList(
                    ScenePlayerInfo.newBuilder()
                            .setUid(p.getUid())
                            .setPeerId(p.getPeerId())
                            .setName(p.getNickname())
                            .setSceneId(p.getSceneId())
                            .setOnlinePlayerInfo(p.getOnlinePlayerInfo())
                            .setIsConnected(p.isHasSentLoginPackets())
                            .build()
            );
        }
        this.setData(proto.build());
    }
}