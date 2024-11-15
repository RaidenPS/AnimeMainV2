package raidengame.connection.packets.receive.game.mail;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;

// Packets
import raidengame.connection.packets.send.game.mail.PacketGetMailItemRsp;

// Protocol buffers
import raidengame.cache.protobuf.GetMailItemReqOuterClass.GetMailItemReq;

/**
 * Handler for receive the items from given mail.
 */
@PacketOpcode(PacketIds.GetMailItemReq)
public class HandlerGetMailItemReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        GetMailItemReq req = GetMailItemReq.parseFrom(data);
        session.send(new PacketGetMailItemRsp(session.getPlayer(), req.getMailIdListList()));
    }
}