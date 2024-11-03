package raidengame.connection.packets.receive.game.social;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;

// Packets
import raidengame.connection.packets.send.game.social.PacketGetAllUnlockNameCardRsp;

/**
 * Handler for send all unlocked name cards.
 */
@PacketOpcode(PacketIds.GetAllUnlockNameCardReq)
public class HandlerGetAllUnlockNameCardReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        session.send(new PacketGetAllUnlockNameCardRsp(session.getPlayer().getNameCardList()));
    }
}