package raidengame.game.mail;

// Imports
import dev.morphia.annotations.Entity;

// Protocol buffers
import raidengame.cache.protobuf.EquipParamOuterClass.EquipParam;
import raidengame.cache.protobuf.MailItemOuterClass.MailItem;

@Entity
public class MailItemInfo {
    public int itemId;
    public int itemCount;
    public int itemLevel;

    /**
     * Creates a new MailItem object by given id.
     * @param itemId The given id.
     */
    public MailItemInfo(int itemId) {
        this(itemId, 1, 1);
    }

    /**
     * Creates a new MailItem object by given id and amount.
     * @param itemId The given id.
     * @param itemCount The given amount.
     */
    public MailItemInfo(int itemId, int itemCount) {
        this(itemId, itemCount, 1);
    }

    /**
     * Creates a new MailItem object by given id, amount and level.
     * @param itemId The given id.
     * @param itemCount The given amount.
     * @param itemLevel The given item id's level.
     */
    public MailItemInfo(int itemId, int itemCount, int itemLevel) {
        this.itemId = itemId;
        this.itemCount = itemCount;
        this.itemLevel = itemLevel;
    }

    /**
     * Returns a protobuf object of MailItem.
     */
    public MailItem toProto() {
        return MailItem.newBuilder()
                .setEquipParam(
                        EquipParam.newBuilder()
                                .setItemId(this.itemId)
                                .setItemNum(this.itemCount)
                                .setItemLevel(this.itemLevel)
                                .setPromoteLevel(0)
                                .build())
                .build();
    }
}