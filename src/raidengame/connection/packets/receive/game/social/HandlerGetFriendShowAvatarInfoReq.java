package raidengame.connection.packets.receive.game.social;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;
import raidengame.game.player.Player;

// Packets
import raidengame.connection.packets.send.game.social.PacketGetFriendShowAvatarInfoRsp;

// Protocol buffers
import raidengame.cache.protobuf.GetFriendShowAvatarInfoReqOuterClass.GetFriendShowAvatarInfoReq;

/**
 * Handler for show friend's additional information of his avatar.
 */
@PacketOpcode(PacketIds.GetFriendShowAvatarInfoReq)
public class HandlerGetFriendShowAvatarInfoReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        GetFriendShowAvatarInfoReq req = GetFriendShowAvatarInfoReq.parseFrom(payload);
        int targetUid = req.getUid();
        Player targetPlayer = session.getServer().getPlayerByUid(targetUid, true);
        if(targetPlayer == null) {
            session.send(new PacketGetFriendShowAvatarInfoRsp(targetUid, null, PacketRetcodes.RETCODE_FAIL));
        }
        else {
            var avatarInfo = targetPlayer.getShowAvatarInfoList();
            session.send(new PacketGetFriendShowAvatarInfoRsp(targetUid, avatarInfo, (targetPlayer.isShowAvatars()) ? PacketRetcodes.RETCODE_SUCC : PacketRetcodes.RET_PLAYER_NOT_SHOW_AVATAR));
        }
    }
}