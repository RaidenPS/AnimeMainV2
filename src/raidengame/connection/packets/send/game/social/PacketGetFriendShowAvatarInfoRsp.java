package raidengame.connection.packets.send.game.social;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import java.util.List;

// Protocol buffers
import raidengame.cache.protobuf.GetFriendShowAvatarInfoRspOuterClass.GetFriendShowAvatarInfoRsp;
import raidengame.cache.protobuf.ShowAvatarInfoOuterClass.ShowAvatarInfo;

/**
 * Packet to send additional information of friend's avatar.
 */
public class PacketGetFriendShowAvatarInfoRsp extends BasePacket {
    public PacketGetFriendShowAvatarInfoRsp(int uid, List<ShowAvatarInfo> showAvatarInfoList, int retcode) {
        super(PacketIds.GetFriendShowAvatarInfoRsp);

        GetFriendShowAvatarInfoRsp.Builder proto =
                GetFriendShowAvatarInfoRsp.newBuilder()
                        .setRetcode(retcode);

        if(showAvatarInfoList != null) {
            proto.setUid(uid);
            proto.addAllShowAvatarInfoList(showAvatarInfoList);
        }

        this.setData(proto.build());
    }
}