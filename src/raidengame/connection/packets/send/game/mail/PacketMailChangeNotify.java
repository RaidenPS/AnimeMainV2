package raidengame.connection.packets.send.game.mail;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.game.mail.MailItemInfo;
import raidengame.game.mail.Mail;
import raidengame.game.player.Player;
import java.util.ArrayList;
import java.util.List;

// Protocol buffers
import raidengame.cache.protobuf.EquipParamOuterClass.EquipParam;
import raidengame.cache.protobuf.MailChangeNotifyOuterClass.MailChangeNotify;
import raidengame.cache.protobuf.MailDataOuterClass.MailData;
import raidengame.cache.protobuf.MailItemOuterClass.MailItem;

/**
 * Packet to change the state of a mail.
 */
public class PacketMailChangeNotify extends BasePacket {
    /**
     * Changes the state of mails that won't be deleted.
     * @param player The player.
     * @param mailList The mail list.
     */
    public PacketMailChangeNotify(Player player, List<Mail> mailList) {
        this(player, mailList, null);
    }

    /**
     * Changes the state of mails that may be deleted.
     * @param player The player.
     * @param mailList The mail list.
     * @param delMailIdList The mail list that will be deleted.
     */
    public PacketMailChangeNotify(Player player, List<Mail> mailList, List<Integer> delMailIdList) {
        super(PacketIds.MailChangeNotify);

        var proto = MailChangeNotify.newBuilder();
        if (mailList != null) {
            for (Mail message : mailList) {
                var mailTextContent = MailData.MailTextContent.newBuilder();
                var mailData = MailData.newBuilder();
                List<MailItem> mailItems = new ArrayList<>();

                mailTextContent.setTitle(message.mailContentInfo.title);
                mailTextContent.setContent(message.mailContentInfo.content);
                mailTextContent.setSender(message.mailContentInfo.sender);
                for (MailItemInfo item : message.mailBoxItemList) {
                    var mailItem = MailItem.newBuilder();
                    var itemParam = EquipParam.newBuilder();
                    itemParam.setItemId(item.itemId);
                    itemParam.setItemNum(item.itemCount);
                    itemParam.setItemLevel(item.itemLevel);
                    mailItem.setEquipParam(itemParam.build());
                    mailItems.add(mailItem.build());
                }

                mailData.setMailId(player.getMailId(message));
                mailData.setMailTextContent(mailTextContent.build());
                mailData.addAllItemList(mailItems);
                mailData.setSendTime((int)message.sendTime);
                mailData.setExpireTime((int)message.expireTime);
                mailData.setImportance(message.importance);
                mailData.setIsRead(message.isRead);
                mailData.setIsAttachmentGot(message.isAttachmentGot);
                mailData.setCollectStateValue(message.stateValue);
                mailData.setConfigId(message.configId);
                proto.addMailList(mailData.build());
            }
        }

        if (delMailIdList != null) {
            proto.addAllDelMailIdList(delMailIdList);
        }

        this.setData(proto.build());
    }
}