package raidengame.connection.packets.receive.game;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;

// Packets
import raidengame.connection.packets.send.game.PacketSetOpenStateRsp;

// Protocol buffers
import raidengame.cache.protobuf.SetOpenStateReqOuterClass.SetOpenStateReq;

/**
 * Handler to set player's open state.
 */
@PacketOpcode(PacketIds.SetOpenStateReq)
public class HandlerSetOpenStateReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        SetOpenStateReq req = SetOpenStateReq.parseFrom(payload);
        session.getPlayer().getProgressManager().handleSetOpenState(req.getKey(), req.getValue());
    }
}