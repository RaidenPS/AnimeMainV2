package raidengame.connection.packets.send.game.social;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.enums.game.social.DealAddFriendResultType;

// Protocol buffers
import raidengame.cache.protobuf.DealAddFriendRspOuterClass.DealAddFriendRsp;

/**
 * Packet to accept or deny a friend request.
 */
public class PacketDealAddFriendRsp extends BasePacket {
    public PacketDealAddFriendRsp(int targetUid, DealAddFriendResultType result, int retcode) {
        super(PacketIds.DealAddFriendRsp);

        DealAddFriendRsp proto =
                DealAddFriendRsp.newBuilder()
                        .setRetcode(retcode)
                        .setTargetUid(targetUid)
                        .setDealAddFriendResult(result.getValue())
                        .build();

        this.setData(proto);
    }
}