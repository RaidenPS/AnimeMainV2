package raidengame.game.mail;

// Imports
import dev.morphia.annotations.Entity;
import raidengame.game.player.Player;

// Protocol buffers
import raidengame.cache.protobuf.MailDataOuterClass.MailData.MailTextContent;

@Entity
public class MailContentInfo {
    public String title;
    public String content;
    public String sender;

    /**
     * Creates a new MailContentInfo object by given title and message.
     * @param title The mail's title.
     * @param content The mail's content.
     */
    public MailContentInfo(String title, String content) {
        this(title, content, "RaidenPS");
    }

    /**
     * Creates a new MailContentInfo object by given title and message.
     * @param title The mail's title.
     * @param content The mail's content.
     * @param sender The sender.
     */
    public MailContentInfo(String title, String content, Player sender) {
        this(title, content, sender.getNickname());
    }

    /**
     * Creates a new MailContentInfo object by given title and message.
     * @param title The mail's title.
     * @param content The mail's content.
     * @param sender The sender.
     */
    public MailContentInfo(String title, String content, String sender) {
        this.title = title;
        this.content = content;
        this.sender = sender;
    }

    /**
     * Returns a protobuf object of MailTextContent.
     */
    public MailTextContent toProto() {
        return MailTextContent.newBuilder()
                .setTitle(this.title)
                .setContent(this.content)
                .setSender(this.sender)
                .build();
    }
}