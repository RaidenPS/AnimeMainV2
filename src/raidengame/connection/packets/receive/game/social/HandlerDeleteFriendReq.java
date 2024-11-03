package raidengame.connection.packets.receive.game.social;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;

// Protocol buffers
import raidengame.cache.protobuf.DeleteFriendReqOuterClass.DeleteFriendReq;

 /**
 * Handler for remove a person from friend list.
 */
@PacketOpcode(PacketIds.DeleteFriendReq)
public class HandlerDeleteFriendReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        DeleteFriendReq req = DeleteFriendReq.parseFrom(data);
        session.getPlayer().getFriendsList().handleRemoveFriend(req.getTargetUid());
    }
}