package raidengame.connection.packets.receive.game.chat;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;

// Packets
import raidengame.connection.packets.send.game.chat.PacketReadPrivateChatRsp;

// Protocol buffers
import raidengame.cache.protobuf.ReadPrivateChatReqOuterClass.ReadPrivateChatReq;

/**
 * Handler for read the private chat.
 */
@PacketOpcode(PacketIds.ReadPrivateChatReq)
public class HandlerReadPrivateChatReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        ReadPrivateChatReq req = ReadPrivateChatReq.parseFrom(data);

        if(!session.getPlayer().getFriendsList().isFriendsWith(req.getTargetUid())) {
            session.send(new PacketReadPrivateChatRsp(PacketRetcodes.RET_INTERNAL_ERROR));
        }
        session.send(new PacketReadPrivateChatRsp(PacketRetcodes.RETCODE_SUCC));
    }
}