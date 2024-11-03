package raidengame.connection.packets.receive.game.social;

// Imports
import raidengame.configuration.GameConstants;
import raidengame.connection.GameSession;
import raidengame.connection.base.*;
import raidengame.misc.WordFilter;

// Packets
import raidengame.connection.packets.send.game.social.PacketSetPlayerNameRsp;

// Protocol buffers
import raidengame.cache.protobuf.SetPlayerNameReqOuterClass.SetPlayerNameReq;

/**
 * Handler for set a new nickname.
 */
@PacketOpcode(PacketIds.SetPlayerNameReq)
public class HandlerSetPlayerNameReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        SetPlayerNameReq req = SetPlayerNameReq.parseFrom(data);
        String nickname = req.getNickName().replace(" ", "");
        var player = session.getPlayer();
        if(nickname.isEmpty()) {
            // The nickname is empty.
            session.send(new PacketSetPlayerNameRsp(player, PacketRetcodes.RET_NICKNAME_IS_EMPTY));
            return;
        }

        if(!WordFilter.checkIsValidUTF8(nickname)) {
            // Non-ascii nickname.
            session.send(new PacketSetPlayerNameRsp(player, PacketRetcodes.RET_NICKNAME_UTF_8_ERROR));
            return;
        }

        if(nickname.length() > GameConstants.NICKNAME_MAX_LEN) {
            // Too large nickname.
            session.send(new PacketSetPlayerNameRsp(player, PacketRetcodes.RET_NICKNAME_TOO_LONG));
            return;
        }

        if(WordFilter.getOnlyNumbers(nickname).length() > GameConstants.NICKNAME_DIGIT_MAX_LEN) {
            // Too many digits.
            session.send(new PacketSetPlayerNameRsp(player, PacketRetcodes.RET_NICKNAME_TOO_MANY_DIGITS));
            return;
        }

        if(WordFilter.checkIsBadWord(nickname)) {
            // The nickname contains a blacklisted word or invalid symbol.
            session.send(new PacketSetPlayerNameRsp(player, PacketRetcodes.RET_NICKNAME_WORD_ILLEGAL));
            return;
        }

        player.setNickname(req.getNickName());
        player.updateProfile();
        session.send(new PacketSetPlayerNameRsp(player, PacketRetcodes.RETCODE_SUCC));
    }
}