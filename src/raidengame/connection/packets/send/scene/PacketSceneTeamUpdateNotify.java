package raidengame.connection.packets.send.scene;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.game.player.Player;

// Protocol buffers
import raidengame.cache.protobuf.AbilitySyncStateInfoOuterClass.AbilitySyncStateInfo;
import raidengame.cache.protobuf.SceneTeamAvatarOuterClass.SceneTeamAvatar;
import raidengame.cache.protobuf.SceneTeamUpdateNotifyOuterClass.SceneTeamUpdateNotify;

/**
 * Packet to update the team in current scene.
 */
public class PacketSceneTeamUpdateNotify extends BasePacket {
    public PacketSceneTeamUpdateNotify(Player player) {
        super(PacketIds.SceneTeamUpdateNotify);

        SceneTeamUpdateNotify.Builder proto =
                SceneTeamUpdateNotify.newBuilder()
                        .setIsInMp(player.getWorld().isMultiplayer());

        for (var p : player.getWorld().getPlayers()) {
            for (var entityAvatar : p.getTeamManager().getActiveTeam()) {
                var avatarProto =
                        SceneTeamAvatar.newBuilder()
                                .setPlayerUid(p.getUid())
                                .setAvatarGuid(entityAvatar.getAvatar().getGuid())
                                .setSceneId(p.getSceneId())
                                .setEntityId(entityAvatar.getId())
                                .setSceneEntityInfo(entityAvatar.toProto())
                                .setWeaponGuid(entityAvatar.getAvatar().getWeaponNotNull().getGuid())
                                .setWeaponEntityId(entityAvatar.getWeaponEntityId())
                                .setIsPlayerCurAvatar(p.getTeamManager().getCurrentAvatarEntity() == entityAvatar)
                                .setIsOnScene(p.getTeamManager().getCurrentAvatarEntity() == entityAvatar)
                                .setAvatarAbilityInfo(AbilitySyncStateInfo.newBuilder())
                                .setWeaponAbilityInfo(AbilitySyncStateInfo.newBuilder())
                                .setAbilityControlBlock(entityAvatar.getAbilityControlBlock());;

                if (player.getWorld().isMultiplayer()) {
                    avatarProto.setAvatarInfo(entityAvatar.getAvatar().toProto());
                    avatarProto.setSceneAvatarInfo(entityAvatar.getSceneAvatarInfo());
                }

                proto.addSceneTeamAvatarList(avatarProto);
            }
        }
        this.setData(proto);
    }
}