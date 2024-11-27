package raidengame.connection.packets.receive.game;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;
import raidengame.game.player.Player;

// Packets
import raidengame.connection.packets.send.game.PacketGetWidgetSlotRsp;

/**
 * Handler for sending GetWidgetSlotRsp.
 */
@PacketOpcode(PacketIds.GetWidgetSlotReq)
public class HandlerGetWidgetSlotReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        Player player = session.getPlayer();
        session.send(new PacketGetWidgetSlotRsp(player));
    }
}