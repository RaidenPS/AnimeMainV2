package raidengame.connection.packets.receive.game.world;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;
import raidengame.game.player.Player;

// Packets
import raidengame.connection.packets.send.game.world.PacketPlayerSetPauseRsp;

// Protocol buffers
import raidengame.cache.protobuf.PlayerSetPauseReqOuterClass.PlayerSetPauseReq;

/**
 * Handler for set pause in the world.
 */
@PacketOpcode(PacketIds.PlayerSetPauseReq)
public class HandlerPlayerSetPauseReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        PlayerSetPauseReq req = PlayerSetPauseReq.parseFrom(data);
        Player player = session.getPlayer();

        if (player.isInMultiplayer()) {
            // You can't pause the game in multiplayer.
            session.send(new PacketPlayerSetPauseRsp(PacketRetcodes.RETCODE_FAIL));
        } else {
            player.getWorld().setPaused(req.getIsPaused());
            session.send(new PacketPlayerSetPauseRsp(PacketRetcodes.RETCODE_SUCC));
        }
    }
}