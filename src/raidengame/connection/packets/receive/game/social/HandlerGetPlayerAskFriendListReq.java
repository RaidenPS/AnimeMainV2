package raidengame.connection.packets.receive.game.social;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;

// Packets
import raidengame.connection.packets.send.game.social.PacketGetPlayerAskFriendListRsp;

/**
 * Handler for get all friend requests.
 */
@PacketOpcode(PacketIds.GetPlayerAskFriendListReq)
public class HandlerGetPlayerAskFriendListReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        session.send(new PacketGetPlayerAskFriendListRsp(session.getPlayer()));
    }
}