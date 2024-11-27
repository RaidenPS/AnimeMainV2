package raidengame.connection.packets.receive;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;
import raidengame.game.player.account.Account;

// Packets
import raidengame.connection.packets.send.PacketGetAuthkeyRsp;

// Packet buffers
import raidengame.cache.protobuf.GetAuthkeyReqOuterClass.GetAuthkeyReq;

/**
 * Handler for send authorization key.
 */
@PacketOpcode(PacketIds.GetAuthkeyReq)
public class HandlerGetAuthkeyReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        GetAuthkeyReq req = GetAuthkeyReq.parseFrom(data);
        Account account = session.getAccount();

        session.send(new PacketGetAuthkeyRsp(req, account.getGameBiz(), account.getSessionKey()));
    }
}