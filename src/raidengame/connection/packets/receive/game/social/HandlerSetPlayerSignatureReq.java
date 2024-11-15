package raidengame.connection.packets.receive.game.social;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;
import raidengame.game.player.Player;
import raidengame.misc.WordFilter;

// Packets
import raidengame.connection.packets.send.game.social.PacketSetPlayerSignatureRsp;

// Protocol buffers
import raidengame.cache.protobuf.SetPlayerSignatureReqOuterClass.SetPlayerSignatureReq;

/**
 * Handler for set a new signature.
 */
@PacketOpcode(PacketIds.SetPlayerSignatureReq)
public class HandlerSetPlayerSignatureReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        SetPlayerSignatureReq req = SetPlayerSignatureReq.parseFrom(data);
        String signature = req.getSignature();
        Player player = session.getPlayer();

        if(signature.isEmpty() || WordFilter.checkIsBadWord(signature)) {
            // The signature is empty or illegal.
            session.send(new PacketSetPlayerSignatureRsp(player, PacketRetcodes.RET_SIGNATURE_ILLEGAL));
            return;
        }

        player.setSignature(req.getSignature());
        player.updateProfile();
        session.send(new PacketSetPlayerSignatureRsp(session.getPlayer(), PacketRetcodes.RETCODE_SUCC));
    }
}