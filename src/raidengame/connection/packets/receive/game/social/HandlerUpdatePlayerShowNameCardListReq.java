package raidengame.connection.packets.receive.game.social;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;

// Packets
import raidengame.connection.packets.send.game.social.PacketUpdatePlayerShowNameCardListRsp;

// Protocol buffers
import raidengame.cache.protobuf.UpdatePlayerShowNameCardListReqOuterClass.UpdatePlayerShowNameCardListReq;

/**
 * Handler for update all shown name cards.
 */
@PacketOpcode(PacketIds.UpdatePlayerShowNameCardListReq)
public class HandlerUpdatePlayerShowNameCardListReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        var req = UpdatePlayerShowNameCardListReq.parseFrom(data);
        var nameCards = req.getShowNameCardIdListList();

        session.getPlayer().setShowNameCardList(nameCards);
        session.send(new PacketUpdatePlayerShowNameCardListRsp(nameCards));
    }
}