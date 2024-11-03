package raidengame.connection.packets.receive.game.world;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;

// Packets
import raidengame.connection.packets.send.game.world.PacketEnterWorldAreaRsp;

// Protocol buffers
import raidengame.cache.protobuf.EnterWorldAreaReqOuterClass.EnterWorldAreaReq;

/**
 * Handler for enter the world area.
 */
@PacketOpcode(PacketIds.EnterWorldAreaReq)
public class HandlerEnterWorldAreaReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        EnterWorldAreaReq req = EnterWorldAreaReq.parseFrom(data);
        session.getPlayer().setArea(req.getAreaId(), req.getAreaType());
        session.send(new PacketEnterWorldAreaRsp(req));
    }
}