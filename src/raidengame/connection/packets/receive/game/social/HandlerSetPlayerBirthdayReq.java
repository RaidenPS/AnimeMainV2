package raidengame.connection.packets.receive.game.social;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;

// Packets
import raidengame.connection.packets.send.game.social.PacketSetPlayerBirthdayRsp;

// Protocol buffers
import raidengame.cache.protobuf.SetPlayerBirthdayReqOuterClass.SetPlayerBirthdayReq;

/**
 * Handler for set birthday.
 */
@PacketOpcode(PacketIds.SetPlayerBirthdayReq)
public class HandlerSetPlayerBirthdayReq extends Packet {
    private boolean isValidBirthday(int month, int day) {
        return switch (month) {
            case 1, 3, 5, 7, 8, 10, 12 -> day > 0 & day <= 31;
            case 4, 6, 9, 11 -> day > 0 && day <= 30;
            case 2 -> day > 0 & day <= 29;
            default -> false;
        };
    }

    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        SetPlayerBirthdayReq req = SetPlayerBirthdayReq.parseFrom(data);
        var player = session.getPlayer();
        if (player.getBirthday().getDay() > 0 || player.getBirthday().getMonth() > 0) {
            // Cannot set a birthday twice.
            session.send(new PacketSetPlayerBirthdayRsp(player, PacketRetcodes.RET_BIRTHDAY_CANNOT_BE_SET_TWICE));
            return;
        }

        int month = req.getBirthday().getMonth();
        int day = req.getBirthday().getDay();
        if (!isValidBirthday(month, day)) {
            // Invalid birthday mm/d format.
            session.send(new PacketSetPlayerBirthdayRsp(player, PacketRetcodes.RET_BIRTHDAY_FORMAT_ERROR));
            return;
        }

        player.setBirthday(day, month);
        player.updateProfile();
        session.send(new PacketSetPlayerBirthdayRsp(player, PacketRetcodes.RETCODE_SUCC));
    }
}