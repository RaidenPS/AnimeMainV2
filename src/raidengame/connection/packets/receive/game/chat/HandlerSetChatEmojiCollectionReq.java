package raidengame.connection.packets.receive.game.chat;

// Imports
import raidengame.configuration.GameConstants;
import raidengame.connection.GameSession;
import raidengame.connection.base.*;
import java.util.List;

// Packets
import raidengame.connection.packets.send.game.chat.PacketSetChatEmojiCollectionRsp;

// Protocol buffers
import raidengame.cache.protobuf.SetChatEmojiCollectionReqOuterClass.SetChatEmojiCollectionReq;

/**
 * Handler for set favorite chat stickers.
 */
@PacketOpcode(PacketIds.SetChatEmojiCollectionReq)
public class HandlerSetChatEmojiCollectionReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        var req = SetChatEmojiCollectionReq.parseFrom(data);
        List<Integer> emotes = req.getChatEmojiCollectionData().getEmojiIdListList();
        if(emotes.size() > GameConstants.CHAT_EMOTES_MAX_SIZE) {
            session.send(new PacketSetChatEmojiCollectionRsp(PacketRetcodes.RET_EMOJI_COLLECTION_NUM_EXCEED_LIMIT));
        }
        else {
            session.getPlayer().setChatEmojiIdList(emotes);
            session.send(new PacketSetChatEmojiCollectionRsp(PacketRetcodes.RETCODE_SUCC));
        }
    }
}