package raidengame.connection.packets.receive.game.social;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;

// Packets
import raidengame.connection.packets.send.game.social.PacketSetNameCardRsp;

// Protocol buffers
import raidengame.cache.protobuf.SetNameCardReqOuterClass.SetNameCardReq;

/**
 * Handler for set current name card.
 */
@PacketOpcode(PacketIds.SetNameCardReq)
public class HandlerSetNameCardReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        SetNameCardReq req = SetNameCardReq.parseFrom(data);
        int card = req.getNameCardId();
        if(!session.getPlayer().getNameCardList().contains(card)) {
            session.send(new PacketSetNameCardRsp(card, PacketRetcodes.RET_NAME_CARD_NOT_UNLOCKED));
            return;
        }

        session.getPlayer().setNameCardId(card);
        session.send(new PacketSetNameCardRsp(card, PacketRetcodes.RETCODE_SUCC));
    }
}