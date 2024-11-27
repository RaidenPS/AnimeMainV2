package raidengame.connection.packets.send.game;

// Imports
import static raidengame.connection.base.PacketRetcodes.RETCODE_SUCC;
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;

// Protocol buffers
import raidengame.cache.protobuf.GetShopmallDataRspOuterClass.GetShopmallDataRsp;

/**
 * Packet for open the shop mall.
 */
public class PacketGetShopmallDataRsp extends BasePacket {
    public PacketGetShopmallDataRsp() {
        super(PacketIds.GetShopmallDataRsp);

        GetShopmallDataRsp proto =
                GetShopmallDataRsp.newBuilder()
                        .setRetcode(RETCODE_SUCC)
                        .addShopTypeList(900) // Recommended
                        .addShopTypeList(1052) // Character Outfits
                        .addShopTypeList(902) // Gift shop
                        .addShopTypeList(1001) // Paimon's Bargains
                        .addShopTypeList(903) // Crystal Top-Up
                        .build();

        this.setData(proto);
    }
}