package raidengame.connection.packets.receive.game.chat;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;

// Packets
import raidengame.connection.packets.send.game.chat.PacketGetChatEmojiCollectionRsp;

/**
 * Handler for send the collection of chat emojis.
 */
@PacketOpcode(PacketIds.GetChatEmojiCollectionReq)
public class HandlerGetChatEmojiCollectionReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        session.send(new PacketGetChatEmojiCollectionRsp(session.getPlayer().getChatEmojiIdList()));
    }
}