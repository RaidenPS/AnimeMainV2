package raidengame.connection.packets.send.game.social;

// Imports
import static raidengame.connection.base.PacketRetcodes.RETCODE_SUCC;
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import java.util.Set;

// Protocol buffers
import raidengame.cache.protobuf.GetProfilePictureDataRspOuterClass.GetProfilePictureDataRsp;

/**
 * Packet to send all character pictures (including special) in player's profile.
 */
public class PacketGetProfilePictureDataRsp extends BasePacket {
    public PacketGetProfilePictureDataRsp(Set<Integer> specialProfilePictures) {
        super(PacketIds.GetProfilePictureDataRsp);

        GetProfilePictureDataRsp proto =
                GetProfilePictureDataRsp.newBuilder()
                        .setRetcode(RETCODE_SUCC)
                        .addAllSpecialProfilePictureList(specialProfilePictures)
                        .build();

        this.setData(proto);
    }
}