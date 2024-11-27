package raidengame.connection.packets.send.game.avatar;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.game.player.Player;

// Protocol buffers
import raidengame.cache.protobuf.AbilitySyncStateInfoOuterClass.AbilitySyncStateInfo;
import raidengame.cache.protobuf.SyncTeamEntityNotifyOuterClass.SyncTeamEntityNotify;
import raidengame.cache.protobuf.TeamEntityInfoOuterClass.TeamEntityInfo;

/**
 * Packet to sync the team entity in multiplayer.
 */
public class PacketSyncTeamEntityNotify extends BasePacket {
    public PacketSyncTeamEntityNotify(Player player) {
        super(PacketIds.SyncTeamEntityNotify);

        SyncTeamEntityNotify.Builder proto =
                SyncTeamEntityNotify.newBuilder()
                        .setSceneId(player.getSceneId());

        if (player.getWorld().isMultiplayer()) {
            for (Player p : player.getWorld()) {
                // Skip if same player
                if (player == p) {
                    continue;
                }

                // Set info
                TeamEntityInfo info =
                        TeamEntityInfo.newBuilder()
                                .setTeamEntityId(p.getTeamManager().getEntity().getId())
                                .setAuthorityPeerId(p.getPeerId())
                                .setTeamAbilityInfo(AbilitySyncStateInfo.newBuilder())
                                .build();

                proto.addTeamEntityInfoList(info);
            }
        }
        this.setData(proto.build());
    }
}