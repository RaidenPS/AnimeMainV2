package raidengame.connection.packets.send.game.social;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.game.player.Player;

// Protocol buffers
import raidengame.cache.protobuf.UpdatePlayerShowAvatarListRspOuterClass.UpdatePlayerShowAvatarListRsp;

/**
 * Packet for show all avatars or constellation number on the player's profile.
 */
public class PacketUpdatePlayerShowAvatarListRsp extends BasePacket {
    public PacketUpdatePlayerShowAvatarListRsp(Player player, int retcode) {
        super(PacketIds.UpdatePlayerShowAvatarListRsp);

        UpdatePlayerShowAvatarListRsp proto =
                UpdatePlayerShowAvatarListRsp.newBuilder()
                        .setRetcode(retcode)
                        .setIsShowAvatar(player.isShowAvatars())
                        .setIsShowConstellationNum(player.isShowConstellationNum())
                        .addAllShowAvatarIdList(player.getShowAvatarList())
                        .build();

        this.setData(proto);
    }
}