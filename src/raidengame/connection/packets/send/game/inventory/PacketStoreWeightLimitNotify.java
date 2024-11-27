package raidengame.connection.packets.send.game.inventory;

// Imports
import raidengame.configuration.GameConstants;
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;

// Protocol buffers
import static raidengame.cache.protobuf.StoreTypeOuterClass.StoreType.STORE_PACK;
import raidengame.cache.protobuf.StoreWeightLimitNotifyOuterClass.StoreWeightLimitNotify;

/**
 * Packet to send inventory limits.
 */
public class PacketStoreWeightLimitNotify extends BasePacket {
    public PacketStoreWeightLimitNotify() {
        super(PacketIds.StoreWeightLimitNotify);

        StoreWeightLimitNotify proto =
                StoreWeightLimitNotify.newBuilder()
                        .setStoreType(STORE_PACK)
                        .setWeightLimit(GameConstants.inventoryCapacity)
                        .setWeaponCountLimit(GameConstants.weaponCapacity)
                        .setReliquaryCountLimit(GameConstants.reliquaryCapacity)
                        .setMaterialCountLimit(GameConstants.itemCapacity)
                        .setFurnitureCountLimit(GameConstants.furnitureCapacity)
                        .build();

        this.setData(proto);
    }
}