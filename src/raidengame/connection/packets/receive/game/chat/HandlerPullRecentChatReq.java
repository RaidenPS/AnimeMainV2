package raidengame.connection.packets.receive.game.chat;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;

// Protocol buffers
import raidengame.cache.protobuf.PullRecentChatReqOuterClass.PullRecentChatReq;

/**
 * Handler for pull the recent chat.
 */
@PacketOpcode(PacketIds.PullRecentChatReq)
public class HandlerPullRecentChatReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        PullRecentChatReq req = PullRecentChatReq.parseFrom(data);
        session.getServer().getChatSystem().handlePullRecentChatReq(session.getPlayer(), req.getBeginSequence(), req.getPullNum());
    }
}