package raidengame.connection.packets.send.game.world;

// Imports
import static raidengame.connection.base.PacketRetcodes.RETCODE_SUCC;
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.game.player.Player;

// Protocol buffers
import raidengame.cache.protobuf.GetMapAreaRspOuterClass.GetMapAreaRsp;
import raidengame.cache.protobuf.MapAreaInfoOuterClass.MapAreaInfo;

/**
 * Packet to send the unlocked map areas.
 */
public class PacketGetMapAreaRsp extends BasePacket {
    public PacketGetMapAreaRsp(Player player) {
        super(PacketIds.GetMapAreaRsp);

        GetMapAreaRsp.Builder proto =
                GetMapAreaRsp.newBuilder()
                        .setRetcode(RETCODE_SUCC);

        player.getUnlockedMapAreas().forEach(info -> info.forEach((key, value) -> proto.addMapAreaInfoList(
                MapAreaInfo.newBuilder().setMapAreaId(key).setIsOpen(value).build()
        )));

        this.setData(proto.build());
    }
}