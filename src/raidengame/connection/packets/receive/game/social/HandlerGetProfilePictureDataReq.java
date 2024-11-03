package raidengame.connection.packets.receive.game.social;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;

// Packets
import raidengame.connection.packets.send.game.social.PacketGetProfilePictureDataRsp;

/**
 * Handler for send all profile pictures (avatars).
 */
@PacketOpcode(PacketIds.GetProfilePictureDataReq)
public class HandlerGetProfilePictureDataReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        session.send(new PacketGetProfilePictureDataRsp(session.getPlayer().getSpecialProfilePictureList()));
    }
}