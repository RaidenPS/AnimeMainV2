package raidengame.connection.packets.send.game.inventory;

// Imports
import raidengame.configuration.GameConstants;
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.game.inventory.GameItem;
import raidengame.game.player.Player;
import java.util.ArrayList;

// Protocol buffers
import static raidengame.cache.protobuf.StoreTypeOuterClass.StoreType.STORE_PACK;
import raidengame.cache.protobuf.PlayerStoreNotifyOuterClass.PlayerStoreNotify;

/**
 * Packet to send all items in the inventory.
 */
public class PacketPlayerStoreNotify extends BasePacket {
    public PacketPlayerStoreNotify(Player player) {
        super(PacketIds.PlayerStoreNotify);

        PlayerStoreNotify proto =
                PlayerStoreNotify.newBuilder()
                        .setStoreType(STORE_PACK)
                        .setWeightLimit(GameConstants.inventoryCapacity)
                        .addAllItemList(new ArrayList<>() {{
                            for (GameItem item : player.getInventory()) add(item.toProto());
                        }})
                        .build();

        this.setData(proto);
    }
}