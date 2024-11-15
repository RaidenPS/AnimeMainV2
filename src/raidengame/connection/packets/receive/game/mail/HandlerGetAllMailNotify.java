package raidengame.connection.packets.receive.game.mail;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;

// Packets
import raidengame.connection.packets.send.game.mail.PacketGetAllMailResultNotify;

// Protocol buffers
import raidengame.cache.protobuf.GetAllMailNotifyOuterClass.GetAllMailNotify;

/**
 * Handler for send all mails in the mailbox.
 */
@PacketOpcode(PacketIds.GetAllMailNotify)
public class HandlerGetAllMailNotify extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        GetAllMailNotify req = GetAllMailNotify.parseFrom(data);
        session.send(new PacketGetAllMailResultNotify(session.getPlayer(), req.getIsCollected()));
    }
}