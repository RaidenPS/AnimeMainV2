package raidengame.connection.packets.receive.game.social;

// Imports
import static raidengame.game.chat.ServerBot.UID;
import raidengame.connection.GameSession;
import raidengame.connection.base.*;

// Packets
import raidengame.connection.packets.send.game.social.PacketGetPlayerSocialDetailRsp;

// Protocol buffers
import raidengame.cache.protobuf.GetPlayerSocialDetailReqOuterClass.GetPlayerSocialDetailReq;
import raidengame.cache.protobuf.SocialDetailOuterClass.SocialDetail;

/**
 * Handler for send player's social information.
 */
@PacketOpcode(PacketIds.GetPlayerSocialDetailReq)
public class HandlerGetPlayerSocialDetailReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        GetPlayerSocialDetailReq req = GetPlayerSocialDetailReq.parseFrom(data);

        SocialDetail.Builder detail = session.getServer().getSocialInfoByUid(req.getUid());
        if (detail != null) {
            detail.setIsFriend(req.getUid() == UID || session.getPlayer().getFriendsList().isFriendsWith(req.getUid()));
            detail.setIsInBlacklist(req.getUid() == UID || session.getPlayer().getFriendsList().isBlockedWith(req.getUid()));
            session.send(new PacketGetPlayerSocialDetailRsp(detail, PacketRetcodes.RETCODE_SUCC));
        }
        else {
            session.send(new PacketGetPlayerSocialDetailRsp(null, PacketRetcodes.RET_PSN_GET_PLAYER_SOCIAL_DETAIL_FAIL));
        }
    }
}