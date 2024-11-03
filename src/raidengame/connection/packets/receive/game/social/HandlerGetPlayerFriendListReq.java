package raidengame.connection.packets.receive.game.social;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;

// Packets
import raidengame.connection.packets.send.game.social.PacketGetPlayerFriendListRsp;

/**
 * Handler for send friend list.
 */
@PacketOpcode(PacketIds.GetPlayerFriendListReq)
public class HandlerGetPlayerFriendListReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        session.send(new PacketGetPlayerFriendListRsp(session.getPlayer()));
    }
}