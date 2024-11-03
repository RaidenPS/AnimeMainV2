package raidengame.connection.packets.receive.game.social;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;

// Packets
import raidengame.connection.packets.send.game.social.PacketGetPlayerBlacklistRsp;

/**
 * Handler for send all blocked players.
 */
@PacketOpcode(PacketIds.GetPlayerBlacklistReq)
public class HandlerGetPlayerBlacklistReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        session.send(new PacketGetPlayerBlacklistRsp(session.getPlayer()));
    }
}