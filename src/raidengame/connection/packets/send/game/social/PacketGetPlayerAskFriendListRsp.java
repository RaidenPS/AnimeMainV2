package raidengame.connection.packets.send.game.social;

// Imports
import static raidengame.connection.base.PacketRetcodes.RETCODE_SUCC;
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.game.player.Player;
import raidengame.game.player.social.SocialObject;

// Protocol buffers
import raidengame.cache.protobuf.GetPlayerAskFriendListRspOuterClass.GetPlayerAskFriendListRsp;

/**
 * Packet to send all friend requests.
 */
public class PacketGetPlayerAskFriendListRsp extends BasePacket {
    public PacketGetPlayerAskFriendListRsp(Player player) {
        super(PacketIds.GetPlayerAskFriendListRsp);

        GetPlayerAskFriendListRsp.Builder proto =
                GetPlayerAskFriendListRsp.newBuilder()
                        .setRetcode(RETCODE_SUCC);

        for (SocialObject friendship : player.getFriendsList().getPendingFriends().values()) {
            if(friendship.getAskerId() == player.getUid()) continue;
            proto.addAskFriendList(friendship.toProto());
        }
        this.setData(proto);
    }
}