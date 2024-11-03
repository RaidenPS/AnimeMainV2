package raidengame.connection.packets.send.game.social;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.game.player.Player;

// Protocol buffers
import raidengame.cache.protobuf.SetPlayerNameRspOuterClass.SetPlayerNameRsp;

/**
 * Sends a packet for change player's nickname.
 */
public class PacketSetPlayerNameRsp extends BasePacket {
    public PacketSetPlayerNameRsp(Player player, int retcode) {
        super(PacketIds.SetPlayerNameRsp);

        SetPlayerNameRsp proto =
                SetPlayerNameRsp.newBuilder()
                        .setRetcode(retcode)
                        .setNickName(player.getNickname())
                        .build();

        this.setData(proto);
    }
}