package raidengame.connection.packets.send.game.avatar;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.game.player.Player;

// Protocol buffers
import raidengame.cache.protobuf.AvatarDataNotifyOuterClass.AvatarDataNotify;

/**
 * Packet to notify the avatar in the game.
 */
public class PacketAvatarDataNotify extends BasePacket {
    public PacketAvatarDataNotify(Player player) {
        super(PacketIds.AvatarDataNotify, true);

        AvatarDataNotify.Builder proto =
                AvatarDataNotify.newBuilder()
                        .setCurAvatarTeamId(player.getTeamManager().getCurrentTeamId())
                        .setChooseAvatarGuid(player.getTeamManager().getCurrentCharacterGuid())
                        .addAllOwnedFlycloakList(player.getFlyCloakList())
                        .addAllOwnedCostumeList(player.getCostumeList());

        player.getAvatars().forEach(avatar -> proto.addAvatarList(avatar.toProto()));
        player.getTeamManager().getTeams().forEach((id, teamInfo) -> {
            proto.putAvatarTeamMap(id, teamInfo.toProto(player));
            if (id > 4) {
                proto.addBackupAvatarTeamOrderList(id);
            }
        });

        this.setData(proto.build());
    }
}