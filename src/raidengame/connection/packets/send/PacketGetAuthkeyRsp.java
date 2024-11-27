package raidengame.connection.packets.send;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;

// Protocol buffers
import raidengame.cache.protobuf.GetAuthkeyReqOuterClass.GetAuthkeyReq;
import raidengame.cache.protobuf.GetAuthkeyRspOuterClass.GetAuthkeyRsp;

/**
 * Packet to send authorization key.
 */
public class PacketGetAuthkeyRsp extends BasePacket {
    public PacketGetAuthkeyRsp(GetAuthkeyReq req, String game_biz, String auth_key) {
        super(PacketIds.GetAuthkeyRsp);

        GetAuthkeyRsp proto =
                GetAuthkeyRsp.newBuilder()
                        .setRetcode(0)
                        .setGameBiz(game_biz)
                        .setAuthkeyVer(req.getAuthkeyVer())
                        .setAuthAppid(req.getAuthAppid())
                        .setSignType(req.getSignType())
                        .setAuthkey(auth_key)
                        .build();

        this.setData(proto);
    }
}