package raidengame.connection.packets.send.scene;

// Imports
import raidengame.configuration.ConfigManager;
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.game.player.Player;
import raidengame.resources.GameData;
import java.util.Map;
import java.util.stream.IntStream;

// Protocol buffers
import raidengame.cache.protobuf.MapLayerInfoOuterClass.MapLayerInfo;
import raidengame.cache.protobuf.PlayerWorldSceneInfoOuterClass.PlayerWorldSceneInfo;
import raidengame.cache.protobuf.PlayerWorldSceneInfoListNotifyOuterClass.PlayerWorldSceneInfoListNotify;

/**
 * Packet to add all scene tags.
 */
public class PacketPlayerWorldSceneInfoListNotify extends BasePacket {
    public PacketPlayerWorldSceneInfoListNotify(Player player) {
        super(PacketIds.PlayerWorldSceneInfoListNotify);

        var sceneTags = player.getSceneTags();
        PlayerWorldSceneInfoListNotify.Builder proto =
                PlayerWorldSceneInfoListNotify.newBuilder()
                        .addInfoList(PlayerWorldSceneInfo.newBuilder().setSceneId(1).setIsLocked(false).build());

        for (int scene : GameData.getSceneDataMap().keySet()) {
            var worldInfoBuilder = PlayerWorldSceneInfo.newBuilder().setSceneId(scene).setIsLocked(false);

            if (sceneTags.containsKey(scene)) {
                worldInfoBuilder.addAllSceneTagIdList(sceneTags.entrySet().stream().filter(e -> e.getKey().equals(scene)).map(Map.Entry::getValue).toList().getFirst());
            }

            if (scene == 3) {
                worldInfoBuilder.setMapLayerInfo(
                        MapLayerInfo.newBuilder()
                                .addAllUnlockedMapLayerIdList(GameData.getMapLayerDataMap().keySet())
                                .addAllUnlockedMapLayerFloorIdList(GameData.getMapLayerFloorDataMap().keySet())
                                .addAllUnlockedMapLayerGroupIdList(GameData.getMapLayerGroupDataMap().keySet())
                                .build());
            }
            proto.addInfoList(worldInfoBuilder.build());
        }

        if(!ConfigManager.serverConfig.gameInfo.enableResources) {
            // no resources
            proto.addInfoList(
                    PlayerWorldSceneInfo.newBuilder()
                            .setSceneId(3)
                            .setIsLocked(false)
                            .addAllSceneTagIdList(IntStream.range(1150, 1250).boxed().toList()) // bruteforce
                            .build());
        }

        this.setData(proto.build());
    }
}