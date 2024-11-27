package raidengame.connection.packets.send.game.world;

// Imports
import static raidengame.connection.base.PacketRetcodes.RETCODE_SUCC;
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import java.util.List;

// Protocol buffers
import raidengame.cache.protobuf.GetAreaExplorePointRspOuterClass.GetAreaExplorePointRsp;
import raidengame.resources.GameData;

/**
 * Packet to send all scene points of an area.
 */
public class PacketGetAreaExplorePointRsp extends BasePacket {
    public PacketGetAreaExplorePointRsp(List<Integer> area_ids) {
        super(PacketIds.GetAreaExplorePointRsp);

        GetAreaExplorePointRsp proto =
                GetAreaExplorePointRsp.newBuilder()
                        .setRetcode(RETCODE_SUCC)
                        .addAllAreaIdList(area_ids)
                        .addAllExplorePointList(GameData.getScenePointIdList())
                        .build();

        this.setData(proto);
    }
}