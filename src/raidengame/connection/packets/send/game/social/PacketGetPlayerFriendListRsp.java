package raidengame.connection.packets.send.game.social;

// Imports
import static raidengame.connection.base.PacketRetcodes.RETCODE_SUCC;
import static raidengame.game.chat.ServerBot.getFriendObject;
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.game.player.Player;
import raidengame.game.player.social.SocialObject;

// Protocol buffers
import raidengame.cache.protobuf.GetPlayerFriendListRspOuterClass.GetPlayerFriendListRsp;

/**
 * Packet to send friend list.
 */
public class PacketGetPlayerFriendListRsp extends BasePacket {
    public PacketGetPlayerFriendListRsp(Player player) {
        super(PacketIds.GetPlayerFriendListRsp);

        GetPlayerFriendListRsp.Builder proto =
                GetPlayerFriendListRsp.newBuilder()
                        .setRetcode(RETCODE_SUCC)
                        .addFriendList(getFriendObject());

        for (SocialObject friendship : player.getFriendsList().getFriends().values()) {
            proto.addFriendList(friendship.toProto());
        }

        for (SocialObject friendship : player.getFriendsList().getPendingFriends().values()) {
            proto.addAskFriendList(friendship.toProto());
        }

        this.setData(proto);
    }
}