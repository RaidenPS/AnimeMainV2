package raidengame.connection.packets.receive.game.mail;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;

// Protocol buffers
import raidengame.cache.protobuf.DelMailReqOuterClass.DelMailReq;

/**
 * Handler to delete a mail from the mailbox.
 */
@PacketOpcode(PacketIds.DelMailReq)
public class HandlerDelMailReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        DelMailReq req = DelMailReq.parseFrom(data);
        session.getPlayer().getMailHandler().deleteMail(req.getMailIdListList());
    }
}