package raidengame.connection.packets.receive.game.social;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;

// Protocol buffers
import raidengame.cache.protobuf.RemoveBlacklistReqOuterClass.RemoveBlacklistReq;

/**
 * Handler for remove a player from blocked list.
 */
@PacketOpcode(PacketIds.RemoveBlacklistReq)
public class HandlerRemoveBlacklistReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        RemoveBlacklistReq req = RemoveBlacklistReq.parseFrom(data);
        session.getPlayer().getFriendsList().removeBlockedPerson(req.getTargetUid());
    }
}