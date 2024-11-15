package raidengame.connection.packets.receive.game.mail;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;

// Packets
import raidengame.connection.packets.send.game.mail.PacketGetAllMailRsp;

// Protocol buffers
import raidengame.cache.protobuf.GetAllMailReqOuterClass.GetAllMailReq;

/**
 * Handler for send all mails in the mailbox.
 * @deprecated Yes
 */
@PacketOpcode(PacketIds.GetAllMailReq)
public class HandlerGetAllMailReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        GetAllMailReq req = GetAllMailReq.parseFrom(data);
        session.send(new PacketGetAllMailRsp(session.getPlayer(), req.getIsCollected()));
    }
}