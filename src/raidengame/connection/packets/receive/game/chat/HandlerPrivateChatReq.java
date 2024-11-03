package raidengame.connection.packets.receive.game.chat;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;

// Protocol buffer
import raidengame.cache.protobuf.PrivateChatReqOuterClass.PrivateChatReq;

/**
 * Handler for send a private message to player.
 */
@PacketOpcode(PacketIds.PrivateChatReq)
public class HandlerPrivateChatReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        PrivateChatReq req = PrivateChatReq.parseFrom(data);
        PrivateChatReq.ContentCase content = req.getContentCase();

        if (content == PrivateChatReq.ContentCase.TEXT) {
            session.getServer().getChatSystem().sendPrivateMessage(session.getPlayer(), req.getTargetUid(), req.getText().replace(" ", ""));
        } else if (content == PrivateChatReq.ContentCase.ICON) {
            session.getServer().getChatSystem().sendPrivateMessage(session.getPlayer(), req.getTargetUid(), req.getIcon());
        }
    }
}