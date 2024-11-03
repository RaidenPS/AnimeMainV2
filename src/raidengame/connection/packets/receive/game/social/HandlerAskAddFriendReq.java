package raidengame.connection.packets.receive.game.social;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;

// Protocol buffers
import raidengame.cache.protobuf.AskAddFriendReqOuterClass.AskAddFriendReq;

/**
 * Handler for send friend request.
 */
@PacketOpcode(PacketIds.AskAddFriendReq)
public class HandlerAskAddFriendReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        AskAddFriendReq req = AskAddFriendReq.parseFrom(data);
        session.getPlayer().getFriendsList().handleSendFriendRequest(req.getTargetUid());
    }
}