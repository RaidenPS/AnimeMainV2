package raidengame.connection.packets.receive.game.social;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;

// Packets
import raidengame.connection.packets.send.game.social.PacketUpdatePlayerShowAvatarListRsp;

// Protocol buffers
import raidengame.cache.protobuf.UpdatePlayerShowAvatarListReqOuterClass.UpdatePlayerShowAvatarListReq;

/**
 * Handler for show all avatars or constellation number on the player's profile.
 */
@PacketOpcode(PacketIds.UpdatePlayerShowAvatarListReq)
public class HandlerUpdatePlayerShowAvatarListReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        UpdatePlayerShowAvatarListReq req = UpdatePlayerShowAvatarListReq.parseFrom(data);

        var player = session.getPlayer();
        for(int avatarId : req.getShowAvatarIdListList()) {
            if(!player.getAvatars().hasAvatar(avatarId)) {
                session.send(new PacketUpdatePlayerShowAvatarListRsp(player, PacketRetcodes.RET_SHOW_AVATAR_INFO_NOT_EXIST));
                return;
            }
        }

        player.setShowAvatars(req.getIsShowAvatar());
        player.setShowConstellationNum(req.getIsShowConstellationNum());
        player.setShowAvatarList(req.getShowAvatarIdListList());

        session.send(new PacketUpdatePlayerShowAvatarListRsp(player, PacketRetcodes.RETCODE_SUCC));
    }
}