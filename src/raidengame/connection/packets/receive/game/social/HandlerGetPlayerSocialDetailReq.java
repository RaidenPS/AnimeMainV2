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

import java.util.Objects;

/**
 * Handler for send player's social information.
 */
@PacketOpcode(PacketIds.GetPlayerSocialDetailReq)
public class HandlerGetPlayerSocialDetailReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        GetPlayerSocialDetailReq req = GetPlayerSocialDetailReq.parseFrom(data);
        SocialDetail.Builder detail = session.getServer().getSocialInfoByUid(req.getUid());
        int id = req.getUid();
        var friendList = session.getPlayer().getFriendsList();

        if (detail != null) {
            boolean isFriend = friendList.isFriendsWith(id);
            detail.setIsFriend(id == UID || isFriend);
            detail.setIsInBlacklist(friendList.isBlockedWith(id));
            if(isFriend) {
                String remarkName = friendList.getFriends().get(id).getRemarkName();
                if(!Objects.equals(remarkName, Objects.requireNonNull(session.getServer().getPlayerByUid(id, true)).getNickname())) {
                    detail.setRemarkName(friendList.getFriends().get(id).getRemarkName());
                }
            }
            session.send(new PacketGetPlayerSocialDetailRsp(detail, PacketRetcodes.RETCODE_SUCC));
        }
        else {
            session.send(new PacketGetPlayerSocialDetailRsp(null, PacketRetcodes.RET_PSN_GET_PLAYER_SOCIAL_DETAIL_FAIL));
        }
    }
}