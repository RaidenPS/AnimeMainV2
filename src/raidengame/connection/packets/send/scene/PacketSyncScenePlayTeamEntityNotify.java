package raidengame.connection.packets.send.scene;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.game.player.Player;

// Protocol buffers
import raidengame.cache.protobuf.AbilitySyncStateInfoOuterClass.AbilitySyncStateInfo;
import raidengame.cache.protobuf.PlayTeamEntityInfoOuterClass.PlayTeamEntityInfo;
import raidengame.cache.protobuf.SyncScenePlayTeamEntityNotifyOuterClass.SyncScenePlayTeamEntityNotify;

/**
 * Packet to sync the team entities in current scene.
 */
public class PacketSyncScenePlayTeamEntityNotify extends BasePacket {
    public PacketSyncScenePlayTeamEntityNotify(Player player) {
        super(PacketIds.SyncScenePlayTeamEntityNotify);

        SyncScenePlayTeamEntityNotify.Builder proto =
                SyncScenePlayTeamEntityNotify.newBuilder()
                        .setSceneId(player.getSceneId());

        for(var entity : player.getScene().getEntities().values()) {
            proto.addEntityInfoList(
                    PlayTeamEntityInfo.newBuilder()
                            .setAbilityInfo(AbilitySyncStateInfo.newBuilder().build())
                            .setAuthorityPeerId(player.getPeerId())
                            .setPlayerUid(player.getUid())
                            .setEntityId(entity.getId())
                            .build());
        }

        this.setData(proto.build());
    }
}