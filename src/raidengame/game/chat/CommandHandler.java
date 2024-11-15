package raidengame.game.chat;

// Imports
import raidengame.game.player.Player;
import java.util.List;

// Events
import raidengame.server.events.game.ReceiveCommandFeedbackEvent;

public interface CommandHandler {
    /**
     * Gets the description of current command.
     * @return The description.
     */
    default String getDescription() {
        return this.getClass().getAnnotation(Command.class).description();
    }

    /**
     * Gets the name of current command.
     * @return The name.
     */
    default String getLabel() {
        return this.getClass().getAnnotation(Command.class).label();
    }

    /**
     * Gets the usage of current command.
     * @return The usage.
     */
    default String getUsage() {
        StringBuilder builder = new StringBuilder();
        builder.append("/").append(this.getClass().getAnnotation(Command.class).label()).append(" ");
        String[] tokens = this.getClass().getAnnotation(Command.class).usage().split(",");

        for (String token : tokens) {
            if (token.endsWith("*")) {
                builder.append("[").append(token.replace("*", "")).append("] ");
            } else {
                builder.append("(").append(token).append(") ");
            }
        }
        return builder.toString().trim();
    }

    /**
     * Send a message to the target.
     *
     * @param player The player to send the message to, or null for the server console.
     * @param message The message to send.
     */
    static void sendMessage(Player player, String message) {
        ReceiveCommandFeedbackEvent event = new ReceiveCommandFeedbackEvent(player, message);
        event.call();
        if (event.isCanceled()) {
            return;
        }

        player.getServer().getChatSystem().sendPrivateMessageFromServer(player.getUid(), event.getMessage().replace("\n\t", "\n\n"));
    }

    /**
     * Called when a player/console invokes a command.
     *
     * @param sender The player/console that invoked the command.
     * @param args The arguments to the command.
     */
    default void execute(Player sender, Player targetPlayer, List<String> args) {}
}