package raidengame.connection.packets.receive.game.chat;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;

// Protocol buffers
import raidengame.cache.protobuf.PullPrivateChatReqOuterClass.PullPrivateChatReq;

/**
 * Handler for pull the private chat.
 */
@PacketOpcode(PacketIds.PullPrivateChatReq)
public class HandlerPullPrivateChatReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        PullPrivateChatReq req = PullPrivateChatReq.parseFrom(data);
        session.getServer().getChatSystem().handlePullPrivateChatReq(session.getPlayer(), req.getTargetUid(), req.getFromSequence(), req.getPullNum());
    }
}