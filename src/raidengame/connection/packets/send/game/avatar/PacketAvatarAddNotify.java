package raidengame.connection.packets.send.game.avatar;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.game.avatar.Avatar;

// Protocol buffers
import raidengame.cache.protobuf.AvatarAddNotifyOuterClass.AvatarAddNotify;

/**
 * Packet to add avatar.
 */
public class PacketAvatarAddNotify extends BasePacket {
    public PacketAvatarAddNotify(Avatar avatar, boolean addToCurrentTeam) {
        super(PacketIds.AvatarAddNotify);

        AvatarAddNotify proto =
                AvatarAddNotify.newBuilder()
                        .setAvatarInfo(avatar.toProto())
                        .setIsInTeam(addToCurrentTeam)
                        .build();

        this.setData(proto);
    }
}