package raidengame.connection.packets.receive.game.mail;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;
import raidengame.game.mail.Mail;

import java.util.ArrayList;
import java.util.List;

// Packets
import raidengame.connection.packets.send.game.mail.PacketMailChangeNotify;

// Protocol buffers
import raidengame.cache.protobuf.ReadMailNotifyOuterClass.ReadMailNotify;

/**
 * Handler to read a mail.
 */
@PacketOpcode(PacketIds.ReadMailNotify)
public class HandlerReadMailNotify extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        ReadMailNotify req = ReadMailNotify.parseFrom(data);
        List<Mail> updatedMail = new ArrayList<>();

        for (int mailId : req.getMailIdListList()) {
            Mail message = session.getPlayer().getMail(mailId);
            message.isRead = true;
            session.getPlayer().replaceMailByIndex(mailId, message);
            updatedMail.add(message);
        }
        session.send(new PacketMailChangeNotify(session.getPlayer(), updatedMail));
    }
}