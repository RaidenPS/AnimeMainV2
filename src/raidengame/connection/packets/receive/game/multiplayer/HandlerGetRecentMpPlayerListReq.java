package raidengame.connection.packets.receive.game.multiplayer;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;

// Protocol buffers
import raidengame.connection.packets.send.game.multiplayer.PacketGetRecentMpPlayerListRsp;

/**
 * Handler for send all co-op requests in friends menu.
 */
@PacketOpcode(PacketIds.GetRecentMpPlayerListReq)
public class HandlerGetRecentMpPlayerListReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        session.send(new PacketGetRecentMpPlayerListRsp(session.getServer().getMultiplayerSystem().getCoOpRequests().get(session.getPlayer())));
    }
}