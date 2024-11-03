package raidengame.connection.packets.send.game.social;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;

// Protocol buffers
import raidengame.cache.protobuf.RemoveBlacklistRspOuterClass.RemoveBlacklistRsp;

/**
 * Packet to remove player from blocklist.
 */
public class PacketRemoveBlacklistRsp extends BasePacket {
    public PacketRemoveBlacklistRsp(int targetUid, int retcode) {
        super(PacketIds.RemoveBlacklistRsp);

        RemoveBlacklistRsp proto =
                RemoveBlacklistRsp.newBuilder()
                        .setRetcode(retcode)
                        .setTargetUid(targetUid)
                        .build();

        this.setData(proto);
    }
}