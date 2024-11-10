package raidengame.connection.packets.receive.game.chat;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;

// Protocol buffers
import raidengame.cache.protobuf.SetChatEmojiCollectionReqOuterClass.SetChatEmojiCollectionReq;

/**
 * Handler for set favorite chat stickers.
 */
@PacketOpcode(PacketIds.SetChatEmojiCollectionReq)
public class HandlerSetChatEmojiCollectionReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        // Todo: Implement SetChatEmojiCollectionRsp

        var req = SetChatEmojiCollectionReq.parseFrom(data);
        session.getPlayer().setChatEmojiIdList(req.getChatEmojiCollectionData().getEmojiIdListList());
    }
}