package raidengame.connection.packets.receive.game.world;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;

// Packets
import raidengame.connection.packets.send.game.world.PacketPlayerGameTimeNotify;
import raidengame.connection.packets.send.game.world.PacketSkipPlayerGameTimeRsp;

// Protocol buffers
import raidengame.cache.protobuf.SkipPlayerGameTimeReqOuterClass.SkipPlayerGameTimeReq;

/**
 * Handler for skip/update the time in the game.
 */
@PacketOpcode(PacketIds.SkipPlayerGameTimeReq)
public class HandlerSkipPlayerGameTimeReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        var req = SkipPlayerGameTimeReq.parseFrom(data);
        var player = session.getPlayer();

        var newTime = req.getGameTime() * 1000L;
        player.updatePlayerGameTime(newTime);
        player.getScene().broadcastPacket(new PacketPlayerGameTimeNotify(player));
        player.sendPacket(new PacketSkipPlayerGameTimeRsp(req));
    }
}