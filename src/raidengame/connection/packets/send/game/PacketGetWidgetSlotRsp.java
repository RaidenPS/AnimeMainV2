package raidengame.connection.packets.send.game;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.game.player.Player;

// Protocol buffers
import raidengame.cache.protobuf.GetWidgetSlotRspOuterClass.GetWidgetSlotRsp;
import raidengame.cache.protobuf.WidgetSlotDataOuterClass.WidgetSlotData;
import raidengame.cache.protobuf.WidgetSlotTagOuterClass.WidgetSlotTag;

/**
 * Packet to send GetWidgetSlot.
 */
public class PacketGetWidgetSlotRsp extends BasePacket {
    public PacketGetWidgetSlotRsp(Player player) {
        super(PacketIds.GetWidgetSlotRsp);

        GetWidgetSlotRsp proto =
                GetWidgetSlotRsp.newBuilder()
                        .addSlotList(WidgetSlotData.newBuilder()
                                .setTag(WidgetSlotTag.WIDGET_SLOT_QUICK_USE)
                                .setIsActive(true)
                                .setMaterialId(player.getWidgetId())
                                .build())
                        .addSlotList(WidgetSlotData.newBuilder()
                                .setTag(WidgetSlotTag.WIDGET_SLOT_ATTACH_AVATAR)
                                .build())
                        .build();

        this.setData(proto);
    }
}