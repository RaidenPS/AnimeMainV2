package raidengame.connection.packets.send.game.social;

// Imports
import static raidengame.connection.base.PacketRetcodes.RETCODE_SUCC;
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.game.player.Player;
import raidengame.game.player.social.SocialObject;

// Protocol buffers
import raidengame.cache.protobuf.GetPlayerBlacklistRspOuterClass.GetPlayerBlacklistRsp;

/**
 * Packet to send all blocked people.
 */
public class PacketGetPlayerBlacklistRsp extends BasePacket {
    public PacketGetPlayerBlacklistRsp(Player player) {
        super(PacketIds.GetPlayerBlacklistRsp);

        GetPlayerBlacklistRsp.Builder proto =
                GetPlayerBlacklistRsp.newBuilder()
                        .setRetcode(RETCODE_SUCC);

        for (SocialObject friendship : player.getFriendsList().getBlockedPeople().values()) {
            proto.addBlacklist(friendship.toProto());
        }

        this.setData(proto);
    }
}