package raidengame.connection.packets.send.game.social;

// Imports
import static raidengame.connection.base.PacketRetcodes.RETCODE_SUCC;
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;

// Protocol buffers
import raidengame.cache.protobuf.GetPlayerSocialDetailRspOuterClass.GetPlayerSocialDetailRsp;
import raidengame.cache.protobuf.SocialDetailOuterClass.SocialDetail;

/**
 * Sends a packet for show profile information.
 */
public class PacketGetPlayerSocialDetailRsp extends BasePacket {
    public PacketGetPlayerSocialDetailRsp(SocialDetail.Builder detail, int retcode) {
        super(PacketIds.GetPlayerSocialDetailRsp);

        GetPlayerSocialDetailRsp proto;
        if(retcode == RETCODE_SUCC) {
            proto = GetPlayerSocialDetailRsp.newBuilder()
                    .setRetcode(retcode)
                    .setDetailData(detail)
                    .build();
        }
        else {
            proto = GetPlayerSocialDetailRsp.newBuilder().setRetcode(retcode).build();
        }
        this.setData(proto);
    }
}