package raidengame.connection.packets.receive.game;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;

// Packets
import raidengame.connection.packets.send.game.PacketGetShopmallDataRsp;

/**
 * Handler for open the shop mall.
 */
@PacketOpcode(PacketIds.GetShopmallDataReq)
public class HandlerGetShopmallDataReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        session.send(new PacketGetShopmallDataRsp());
    }
}