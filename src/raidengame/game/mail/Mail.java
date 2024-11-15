package raidengame.game.mail;

// Imports
import raidengame.database.DatabaseHelper;
import raidengame.game.player.Player;
import dev.morphia.annotations.*;
import java.time.Instant;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

// Protocol buffers
import raidengame.cache.protobuf.MailDataOuterClass.MailData;

@Entity(value = "mail", useDiscriminator = false)
public final class Mail {
    @Getter @Id private ObjectId id;
    @Setter @Getter @Indexed private int ownerId;
    public MailContentInfo mailContentInfo;
    public List<MailItemInfo> mailBoxItemList;
    public long sendTime;
    public long expireTime;
    public int importance;
    public boolean isRead;
    public boolean isAttachmentGot;
    public int stateValue;
    public int configId;

    /**
     * Creates a new Mail object by given content, items and expiration date.
     * @param mailContentInfo The message.
     * @param mailBoxItemList The item list.
     * @param expireTime The expiration time.
     */
    public Mail(MailContentInfo mailContentInfo, List<MailItemInfo> mailBoxItemList, long expireTime) {
        this(mailContentInfo, mailBoxItemList, expireTime, 0);
    }

    /**
     * Creates a new Mail object by given content, items and expiration date.
     * @param mailContentInfo The message.
     * @param mailBoxItemList The item list.
     * @param expireTime The expiration time.
     * @param importance Is the email set as favorite.
     */
    public Mail(MailContentInfo mailContentInfo, List<MailItemInfo> mailBoxItemList, long expireTime, int importance) {
        this(mailContentInfo, mailBoxItemList, expireTime, importance, 1);
    }

    /**
     * Creates a new Mail object by given content, items, expiration date and state.
     * @param mailContentInfo The message.
     * @param mailBoxItemList The item list.
     * @param expireTime The expiration time.
     * @param importance Is the email set as favorite.
     */
    public Mail(MailContentInfo mailContentInfo, List<MailItemInfo> mailBoxItemList, long expireTime, int importance, int state) {
        this.mailContentInfo = mailContentInfo;
        this.mailBoxItemList = mailBoxItemList;
        this.sendTime = (int) Instant.now().getEpochSecond();
        this.expireTime = expireTime;
        this.importance = importance;
        this.isRead = false;
        this.isAttachmentGot = false;
        this.stateValue = state;
        this.configId = 0;
    }

    /**
     * Creates a new Mail object by given arguments and mail config id.
     * @param message The message arguments.
     * @param configId The config id.
     */
    public Mail(String message, int configId) {
        this.configId = configId;
        this.mailContentInfo = new MailContentInfo("", message);
    }

    /**
     * Saves an mail.
     */
    public void save() {
        if (this.expireTime * 1000 < System.currentTimeMillis()) {
            DatabaseHelper.deleteMail(this);
        } else {
            DatabaseHelper.saveMail(this);
        }
    }

    /**
     * Returns a protobuf object of MailData.
     */
    public MailData toProto(Player player) {
        return MailData.newBuilder()
                .setMailId(player.getMailId(this))
                .setMailTextContent(this.mailContentInfo.toProto())
                .addAllItemList(this.mailBoxItemList.stream().map(MailItemInfo::toProto).toList())
                .setSendTime((int)this.sendTime)
                .setExpireTime((int)this.expireTime)
                .setImportance(this.importance)
                .setIsRead(this.isRead)
                .setIsAttachmentGot(this.isAttachmentGot)
                .setConfigId(this.configId)
                .setCollectState(MailData.MailCollectState.forNumber(this.stateValue))
                .build();
    }
}