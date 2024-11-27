package raidengame.connection.packets.receive;

// Imports
import raidengame.configuration.GameConstants;
import raidengame.connection.GameSession;
import raidengame.connection.base.*;
import raidengame.game.player.Player;

// Packets
import raidengame.connection.packets.send.game.PacketPlayerGameKickRsp;
import raidengame.connection.packets.send.PacketSetPlayerBornDataRsp;

// Protocol buffers
import raidengame.cache.protobuf.SetPlayerBornDataReqOuterClass.SetPlayerBornDataReq;
import raidengame.misc.WordFilter;

/**
 * Handler for send selected character from the screen.
 */
@PacketOpcode(PacketIds.SetPlayerBornDataReq)
public class HandlerSetPlayerBornDataReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        SetPlayerBornDataReq req = SetPlayerBornDataReq.parseFrom(data);
        int avatarId = req.getAvatarId();
        if(avatarId == GameConstants.MAIN_CHARACTER_MALE || avatarId == GameConstants.MAIN_CHARACTER_FEMALE)
        {
            String nickname = req.getNickname().replace(" ", "");
            if(nickname.isEmpty()) {
                // The nickname is empty.
                session.send(new PacketSetPlayerBornDataRsp(PacketRetcodes.RET_NICKNAME_IS_EMPTY));
                return;
            }

            if(!WordFilter.checkIsValidUTF8(nickname)) {
                // Non-ascii nickname.
                session.send(new PacketSetPlayerBornDataRsp(PacketRetcodes.RET_NICKNAME_UTF_8_ERROR));
                return;
            }

            if(nickname.length() > GameConstants.NICKNAME_MAX_LEN) {
                // Too large nickname.
                session.send(new PacketSetPlayerBornDataRsp(PacketRetcodes.RET_NICKNAME_TOO_LONG));
                return;
            }

            if(WordFilter.getOnlyNumbers(nickname).length() > GameConstants.NICKNAME_DIGIT_MAX_LEN) {
                // Too many digits.
                session.send(new PacketSetPlayerBornDataRsp(PacketRetcodes.RET_NICKNAME_TOO_MANY_DIGITS));
                return;
            }

            if(WordFilter.checkIsBadWord(nickname)) {
                // The nickname contains a blacklisted word or invalid symbol.
                session.send(new PacketSetPlayerBornDataRsp(PacketRetcodes.RET_NICKNAME_WORD_ILLEGAL));
                return;
            }

            Player player = session.getPlayer();
            player.setNickname(req.getNickname());
            player.addAvatar(req.getAvatarId(), true, false);
            player.setSceneId(3);
            player.getTeamManager().getCurrentSinglePlayerTeamInfo().getAvatars().add(req.getAvatarId());

            session.send(new PacketSetPlayerBornDataRsp(PacketRetcodes.RETCODE_SUCC));
            player.onLogin(true);
        }
        else {
            session.getAccount().setSuspended(true);
            session.getAccount().saveAccount();
            session.send(new PacketPlayerGameKickRsp());
        }
    }
}