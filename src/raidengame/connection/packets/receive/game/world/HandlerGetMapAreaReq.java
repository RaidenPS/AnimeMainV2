package raidengame.connection.packets.receive.game.world;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;

// Protocol buffers
import raidengame.connection.packets.send.game.world.PacketGetMapAreaRsp;

/**
 * Handler for send the map areas.
 */
@PacketOpcode(PacketIds.GetMapAreaReq)
public class HandlerGetMapAreaReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        session.send(new PacketGetMapAreaRsp(session.getPlayer()));
    }
}