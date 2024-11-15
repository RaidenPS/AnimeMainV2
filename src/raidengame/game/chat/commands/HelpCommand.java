package raidengame.game.chat.commands;

// Imports
import raidengame.game.chat.*;
import raidengame.game.player.Player;
import raidengame.game.player.account.Account;
import java.util.ArrayList;
import java.util.List;

@Command(
        label = "help",
        description = "List of all server commands.",
        usage = "command",
        targetRequirement = Command.TargetRequirement.NONE
)
public final class HelpCommand implements CommandHandler {
    /**
     * Internal function to print the command name and description.
     * @param command The given command.
     * @return Text message that contains the command.
     */
    private String createCommand(CommandHandler command) {
        return "/" + command.getLabel() + " - " + command.getDescription();
    }

    /**
     * Internal function to print the usage of the given command.
     * @param command The given command.
     * @return Text message that contains the information related to that command.
     */
    private String createCommandUsage(CommandHandler command) {
        StringBuilder builder = new StringBuilder("/" + command.getLabel());
        Command annotation = command.getClass().getAnnotation(Command.class);
        if(annotation.aliases().length > 0) {
            builder.append(" (Aliases: ");
            builder.append(String.join(", ", annotation.aliases()));
            builder.append(")");
        }

        builder.append("\n\t");
        builder.append(command.getDescription());
        builder.append("\n\t");
        builder.append("Usage: ").append(command.getUsage());
        builder.append("\n\t");
        builder.append("Permissions: ").append((annotation.permission().isEmpty()) ? "-" : annotation.permission());
        return builder.toString();
    }

    @Override
    public void execute(Player player, Player targetPlayer, List<String> args) {
        Account account = (player == null) ? null : player.getAccount();
        var commandMap = CommandMap.getInstance();
        List<String> commands = new ArrayList<>();
        if (args.isEmpty()) {
            commandMap
                    .getHandlers()
                    .forEach(
                            (_, command) -> {
                                Command annotation = command.getClass().getAnnotation(Command.class);
                                if ((player == null || account.hasPermission(annotation.permission())) && !annotation.disabled()) {
                                    commands.add(createCommand(command));
                                }
                            });
            CommandHandler.sendMessage(player, "Available commands:");

        } else {
            String command_str = args.removeFirst().toLowerCase();
            CommandHandler command = commandMap.getHandler(command_str);
            if (command == null) {
                CommandHandler.sendMessage(player, "Unknown command");
                return;
            }

            Command annotation = command.getClass().getAnnotation(Command.class);
            if(!annotation.disabled()) commands.add((player == null || account.hasPermission(annotation.permission())) ? this.createCommandUsage(command) : "You don't have permission to use this command.");
            else commands.add("The command is disabled by the administrator.");
        }

        final int HELP_TOTAL_COMMANDS_SEND = 10;
        for (int i = 0; i < commands.size(); i += HELP_TOTAL_COMMANDS_SEND) {
            String batchMessage = String.join("\n\t", commands.subList(i, Math.min(i + HELP_TOTAL_COMMANDS_SEND, commands.size())));
            CommandHandler.sendMessage(player, batchMessage);
        }
    }
}