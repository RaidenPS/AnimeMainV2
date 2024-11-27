package raidengame.connection.packets.send.game.social;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;

// Protocol buffers
import raidengame.cache.protobuf.ProfilePictureOuterClass.ProfilePicture;
import raidengame.cache.protobuf.SetPlayerHeadImageRspOuterClass.SetPlayerHeadImageRsp;

/**
 * Packet for change player's profile avatar image.
 */
public class PacketSetPlayerHeadImageRsp extends BasePacket {
    public PacketSetPlayerHeadImageRsp(int head_avatar_id, int retcode) {
        super(PacketIds.SetPlayerHeadImageRsp);

        SetPlayerHeadImageRsp proto =
                SetPlayerHeadImageRsp.newBuilder()
                        .setRetcode(retcode)
                        .setProfilePicture(ProfilePicture.newBuilder().setHeadImageId(head_avatar_id))
                        .build();

        this.setData(proto);
    }
}