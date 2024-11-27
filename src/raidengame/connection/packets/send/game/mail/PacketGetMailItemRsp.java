package raidengame.connection.packets.send.game.mail;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.enums.ActionReason;
import raidengame.game.inventory.GameItem;
import raidengame.game.mail.MailItemInfo;
import raidengame.game.mail.Mail;
import raidengame.game.player.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Protocol buffers
import raidengame.cache.protobuf.EquipParamOuterClass.EquipParam;
import raidengame.cache.protobuf.GetMailItemRspOuterClass.GetMailItemRsp;
import raidengame.resources.GameData;

/**
 * Packet to get all items from given mail.
 */
public class PacketGetMailItemRsp extends BasePacket {
    public PacketGetMailItemRsp(Player player, List<Integer> mailList) {
        super(PacketIds.GetMailItemRsp);

        List<Mail> claimedMessages = new ArrayList<>();
        List<EquipParam> claimedItems = new ArrayList<>();
        for (int mailId : mailList) {
            Mail message = player.getMail(mailId);
            if (!message.isAttachmentGot) {
                for (MailItemInfo mailItem : message.mailBoxItemList) {
                    EquipParam.Builder item = EquipParam.newBuilder();
                    int promoteLevel = GameItem.getMinPromoteLevel(mailItem.itemLevel);

                    item.setItemId(mailItem.itemId);
                    item.setItemNum(mailItem.itemCount);
                    item.setItemLevel(mailItem.itemLevel);
                    item.setPromoteLevel(promoteLevel);
                    claimedItems.add(item.build());

                    GameItem gameItem = new GameItem(GameData.getItemDataMap().get(mailItem.itemId));
                    gameItem.setCount(mailItem.itemCount);
                    gameItem.setLevel(mailItem.itemLevel);
                    gameItem.setPromoteLevel(promoteLevel);
                    player.getInventory().addItem(gameItem, ActionReason.MailAttachment);
                }

                message.isAttachmentGot = true;
                message.stateValue = 3;
                claimedMessages.add(message);
                player.replaceMailByIndex(mailId, message);
            }
        }

        GetMailItemRsp proto =
                GetMailItemRsp.newBuilder()
                        .addAllMailIdList(claimedMessages.stream().map(player::getMailId).collect(Collectors.toList()))
                        .addAllItemList(claimedItems)
                        .build();

        player.getSession().send(new PacketMailChangeNotify(player, claimedMessages));
        this.setData(proto);
    }
}