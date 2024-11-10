package raidengame.connection.packets.send.game.social;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;

// Protocol buffers
import raidengame.cache.protobuf.DeleteFriendNotifyOuterClass.DeleteFriendNotify;

public class PacketDeleteFriendNotify extends BasePacket {
    public PacketDeleteFriendNotify(int targetId) {
        super(PacketIds.DeleteFriendNotify);

        DeleteFriendNotify proto =
                DeleteFriendNotify.newBuilder()
                        .setTargetUid(targetId)
                        .build();

        this.setData(proto);
    }
}