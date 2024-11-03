package raidengame.connection.packets.receive.game.social;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;
import raidengame.enums.game.social.DealAddFriendResultType;

// Protocol buffers
import raidengame.cache.protobuf.DealAddFriendReqOuterClass.DealAddFriendReq;

/**
 * Handler for deal with friend request (Deny or Accept).
 */
@PacketOpcode(PacketIds.DealAddFriendReq)
public class HandlerDealAddFriendReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        DealAddFriendReq req = DealAddFriendReq.parseFrom(data);
        session.getPlayer().getFriendsList().handleFriendRequest(req.getTargetUid(), DealAddFriendResultType.fromValue(req.getDealAddFriendResult()));
    }
}