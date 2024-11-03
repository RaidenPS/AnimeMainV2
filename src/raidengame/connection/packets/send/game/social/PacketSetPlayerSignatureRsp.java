package raidengame.connection.packets.send.game.social;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.game.player.Player;

// Protocol buffers
import raidengame.cache.protobuf.SetPlayerSignatureRspOuterClass.SetPlayerSignatureRsp;

/**
 * Sends a packet for change player's signature.
 */
public class PacketSetPlayerSignatureRsp extends BasePacket {
    public PacketSetPlayerSignatureRsp(Player player, int retcode) {
        super(PacketIds.SetPlayerSignatureRsp);

        SetPlayerSignatureRsp proto =
                SetPlayerSignatureRsp.newBuilder()
                        .setRetcode(retcode)
                        .setSignature(player.getSignature())
                        .build();

        this.setData(proto);
    }
}