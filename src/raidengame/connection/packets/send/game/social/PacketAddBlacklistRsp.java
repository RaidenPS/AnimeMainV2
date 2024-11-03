package raidengame.connection.packets.send.game.social;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;

// Protocol buffers
import raidengame.cache.protobuf.AddBlacklistRspOuterClass.AddBlacklistRsp;
import raidengame.cache.protobuf.FriendBriefOuterClass.FriendBrief;

/**
 * Packet to add player to blocklist.
 */
public class PacketAddBlacklistRsp extends BasePacket {
    public PacketAddBlacklistRsp(FriendBrief player, int retcode) {
        super(PacketIds.AddBlacklistRsp);

        AddBlacklistRsp proto =
                AddBlacklistRsp.newBuilder()
                        .setRetcode(retcode)
                        .setTargetFriendBrief(player)
                        .build();

        this.setData(proto);
    }
}