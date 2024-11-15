package raidengame.game.mail;

// Imports
import raidengame.configuration.GameConstants;
import raidengame.database.DatabaseHelper;
import raidengame.game.player.BasePlayerManager;
import raidengame.game.player.Player;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Getter;

// Events
import raidengame.server.events.player.PlayerReceiveMailEvent;

// Packets
import raidengame.connection.packets.send.game.mail.PacketDelMailRsp;
import raidengame.connection.packets.send.game.mail.PacketMailChangeNotify;

@Getter
public class MailHandler extends BasePlayerManager {
    private final List<Mail> mail;

    /**
     * Creates a new instance of MailHandler.
     * @param player The given player.
     */
    public MailHandler(Player player) {
        super(player);
        this.mail = new ArrayList<>();
    }

    /**
     * Gets email object by given id.
     * @param index The given id.
     * @return The Mail.
     */
    public Mail getMailById(int index) {
        return this.mail.get(index);
    }

    /**
     * Gets email object by index.
     * @param mail The given mail object.
     * @return The index of where mail is located.
     */
    public int getMailIndex(Mail mail) {
        return this.mail.indexOf(mail);
    }

    /**
     * Loads all mails from the database.
     */
    public void loadFromDatabase() {
        List<Mail> mailList = DatabaseHelper.getAllMail(this.player);
        this.mail.addAll(mailList);
    }

    /**
     * Deletes mail by given ids.
     * @param mailList The given mail ids.
     */
    public void deleteMail(List<Integer> mailList) {
        List<Integer> deleted = new ArrayList<>();
        for (int mailId : mailList) {
            Mail message = this.getMailById(mailId);
            if (message != null) {
                this.mail.remove(mailId);
                message.expireTime = 0;
                message.save();
                deleted.add(mailId);
            }
        }

        player.getSession().send(new PacketMailChangeNotify(player, null, deleted));
        player.getSession().send(new PacketDelMailRsp(player, deleted, 0));
    }

    /**
     * Sets a new position of given message.
     * @param index The position to se.
     * @param message The message.
     */
    public void replaceMailByIndex(int index, Mail message) {
        if (this.getMailById(index) != null) {
            this.mail.set(index, message);
            message.save();
        }
    }

    /**
     * Sends a new mail.
     * @param message The mail message.
     */
    public void sendMail(Mail message) {
        PlayerReceiveMailEvent event = new PlayerReceiveMailEvent(this.player, message);
        event.call();
        if (event.isCanceled()) return;
        message = event.getMessage();

        message.setOwnerId(this.player.getUid());
        message.save();

        this.mail.add(message);
        if (this.player.isOnline()) {
            this.player.sendPacket(new PacketMailChangeNotify(this.player, Collections.singletonList(message)));
        }
    }

    /**
     * Sends mail for first-time players.
     */
    public void sendWelcomeMailMessage() {
        MailContentInfo content = new MailContentInfo("Welcome to RaidenPS", GameConstants.welcomeMessage);
        long time = System.currentTimeMillis() + 86400;

        List<MailItemInfo> mailItems = new ArrayList<>();
        mailItems.add(new MailItemInfo(223, 100));

        Mail message = new Mail(content, mailItems, time, 0, 3);
        this.sendMail(message);
    }
}