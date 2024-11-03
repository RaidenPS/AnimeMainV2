package raidengame.connection.packets.receive.game.world;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;

// Packets
import raidengame.connection.packets.send.game.world.PacketGetAreaExplorePointRsp;

// Protocol buffers
import raidengame.cache.protobuf.GetAreaExplorePointReqOuterClass.GetAreaExplorePointReq;

/**
 * Handler for get area explore point.
 */
@PacketOpcode(PacketIds.GetAreaExplorePointReq)
public class HandlerGetAreaExplorePointReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        GetAreaExplorePointReq req = GetAreaExplorePointReq.parseFrom(data);
        session.send(new PacketGetAreaExplorePointRsp(session.getPlayer(), req.getAreaIdListList()));
    }
}