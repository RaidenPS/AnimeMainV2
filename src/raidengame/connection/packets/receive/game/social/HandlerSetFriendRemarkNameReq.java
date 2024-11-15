package raidengame.connection.packets.receive.game.social;

// Imports
import raidengame.configuration.GameConstants;
import raidengame.connection.GameSession;
import raidengame.connection.base.*;
import raidengame.game.chat.ServerBot;
import raidengame.misc.WordFilter;

// Packets
import raidengame.connection.packets.send.game.social.PacketSetFriendRemarkNameRsp;

// Protocol buffers
import raidengame.cache.protobuf.SetFriendRemarkNameReqOuterClass.SetFriendRemarkNameReq;

/**
 * Handler for set a friend (remark) nickname to a friend.
 */
@PacketOpcode(PacketIds.SetFriendRemarkNameReq)
public class HandlerSetFriendRemarkNameReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        SetFriendRemarkNameReq req = SetFriendRemarkNameReq.parseFrom(data);
        String name = req.getRemarkName();
        int uid = req.getUid();

        if(uid == ServerBot.UID) {
            session.send(new PacketSetFriendRemarkNameRsp(uid, name, PacketRetcodes.RETCODE_FAIL));
            return;
        }

        if(name.isEmpty()) {
            session.send(new PacketSetFriendRemarkNameRsp(uid, name, PacketRetcodes.RET_REMARK_IS_EMPTY));
            return;
        }

        if(!WordFilter.checkIsValidUTF8(name)) {
            session.send(new PacketSetFriendRemarkNameRsp(uid, name, PacketRetcodes.RET_REMARK_UTF_8_ERROR));
            return;
        }

        if(WordFilter.checkIsBadWord(name)) {
            session.send(new PacketSetFriendRemarkNameRsp(uid, name, PacketRetcodes.RET_REMARK_WORD_ILLEGAL));
            return;
        }

        if(name.length() > GameConstants.NICKNAME_MAX_LEN) {
            session.send(new PacketSetFriendRemarkNameRsp(uid, name, PacketRetcodes.RET_REMARK_TOO_LONG));
            return;
        }

        session.getPlayer().getFriendsList().getFriendById(uid).setRemarkName(name);
        session.getPlayer().getFriendsList().getFriendById(uid).saveDatabase();
        session.send(new PacketSetFriendRemarkNameRsp(uid, name, PacketRetcodes.RETCODE_SUCC));
    }
}