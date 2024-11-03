package raidengame.connection.packets.send.game.social;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.game.player.Player;

// Protocol buffers
import raidengame.cache.protobuf.SetPlayerBirthdayRspOuterClass.SetPlayerBirthdayRsp;

/**
 * Sends a packet for change player's birthday.
 */
public class PacketSetPlayerBirthdayRsp extends BasePacket {
    public PacketSetPlayerBirthdayRsp(Player player, int retcode) {
        super(PacketIds.SetPlayerBirthdayRsp);

        SetPlayerBirthdayRsp proto =
                SetPlayerBirthdayRsp.newBuilder()
                        .setRetcode(retcode)
                        .setBirthday(player.getBirthday().toProto())
                        .build();

        this.setData(proto);
    }
}