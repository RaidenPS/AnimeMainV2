package raidengame.connection.packets.receive.game.social;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;

// Protocol buffers
import raidengame.cache.protobuf.AddBlacklistReqOuterClass.AddBlacklistReq;

/**
 * Handler for add a person to block list.
 */
@PacketOpcode(PacketIds.AddBlacklistReq)
public class HandlerAddBlacklistReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        AddBlacklistReq req = AddBlacklistReq.parseFrom(data);
        session.getPlayer().getFriendsList().handlerAddBlockedPlayer(req.getTargetUid());
    }
}