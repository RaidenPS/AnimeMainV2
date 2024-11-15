package raidengame.game.chat;

// Imports
import com.mongodb.lang.Nullable;
import raidengame.Main;
import raidengame.game.player.Player;
import org.reflections.Reflections;
import java.util.*;

// Events
import raidengame.server.events.game.ExecuteCommandEvent;

public final class CommandMap {
    private final Map<String, CommandHandler> commands = new TreeMap<>();
    private final Map<String, CommandHandler> aliases = new TreeMap<>();
    private final Map<String, Command> annotations = new TreeMap<>();

    /**
     * Creates a new instance of CommandMap without add all commands to server.
     */
    public CommandMap() {
        this(false);
    }

    /**
     * Creates a new instance of CommandMap.
     * @param scan Scan for commands
     */
    public CommandMap(boolean scan) {
        if (scan) this.scan();
    }

    /**
     * Gets the annotations.
     * @return Tje annotations.
     */
    public Map<String, Command> getAnnotations() {
        return new LinkedHashMap<>(this.annotations);
    }

    /**
     * Gets the annotations represented as list.
     * @return The annotations.
     */
    public List<Command> getAnnotationsAsList() {
        return new ArrayList<>(this.annotations.values());
    }

    /**
     * Returns a list of all registered commands.
     *
     * @return All command handlers as a list.
     */
    public List<CommandHandler> getHandlersAsList() {
        return new ArrayList<>(this.commands.values());
    }

    /**
     * Returns all registered commands.
     *
     * @return All command handlers.
     */
    public Map<String, CommandHandler> getHandlers() {
        return this.commands;
    }

    /**
     * Gets the CommandMap object.
     * @return CommandMap object.
     */
    public static CommandMap getInstance() {
        return Main.getCommandMap();
    }

    /**
     * Returns a handler by label/alias.
     *
     * @param label The command label.
     * @return The command handler.
     */
    public CommandHandler getHandler(String label) {
        CommandHandler handler = this.commands.get(label);
        if (handler == null) {
            handler = this.aliases.get(label);
        }
        return handler;
    }

    /**
     * Returns the target by priority.
     * @param player The player who executed the command.
     * @param targetPlayer The target player.
     * @param args The command arguments.
     * @return The player object.
     */
    private Player getTargetPlayer(Player player, Player targetPlayer, List<String> args, boolean allowTarget) {
        if(args.isEmpty()) return player;

        String id = args.getLast();
        if (id.startsWith("@") && allowTarget) {
            targetPlayer = this.getPlayerFromUid(id.substring(1));
            if (targetPlayer == null) {
                CommandHandler.sendMessage(player, "The player is not specified or does not exist.");
            }
            return targetPlayer;
        }

        if(targetPlayer != null) {
            return targetPlayer;
        }
        return player;
    }

    /**
     * Converts the id to int or gets player's id from account id.
     * @param input The id represented as string.
     * @return The id as integer.
     */
    private @Nullable Player getPlayerFromUid(String input) {
        if(input == null) return null;

        try {
            return Main.getGameServer().getPlayerByUid(Integer.parseInt(input), true);
        }catch (Exception _) {
            return null;
        }
    }

    /**
     * Register a command handler.
     *
     * @param label The command label.
     * @param command The command handler.
     * @return Instance chaining.
     */
    public CommandMap registerCommand(String label, CommandHandler command) {
        label = label.toLowerCase();
        Command annotation = command.getClass().getAnnotation(Command.class);
        this.annotations.put(label, annotation);
        this.commands.put(label, command);

        for (String alias : annotation.aliases()) {
            this.aliases.put(alias, command);
            this.annotations.put(alias, annotation);
        }
        return this;
    }

    /** Scans for all classes annotated with {@link Command} and registers them. */
    private void scan() {
        Reflections reflector = Main.reflector;
        Set<Class<?>> classes = reflector.getTypesAnnotatedWith(Command.class);
        classes.forEach(
                annotated -> {
                    try {
                        Command cmdData = annotated.getAnnotation(Command.class);
                        Object object = annotated.getDeclaredConstructor().newInstance();
                        if (object instanceof CommandHandler) {
                            this.registerCommand(cmdData.label(), (CommandHandler)object);
                            Main.getLogger().debug("Registered a new chat command: " + cmdData.label());
                        }

                    } catch (Exception exception) {
                        Main.getLogger().error("Failed to register command handler for " + annotated.getSimpleName(), exception);
                    }
                }
        );
    }

    /**
     * Removes a registered command handler.
     *
     * @param label The command label.
     * @return Instance chaining.
     */
    public CommandMap unregisterCommand(String label) {
        Main.getLogger().debug("[Chat] Unregistered command: " + label);

        CommandHandler handler = this.commands.get(label);
        if (handler == null) return this;

        Command annotation = handler.getClass().getAnnotation(Command.class);
        this.annotations.remove(label);
        this.commands.remove(label);

        // Unregister aliases.
        for (String alias : annotation.aliases()) {
            this.aliases.remove(alias);
            this.annotations.remove(alias);
        }

        return this;
    }

    /**
     * Invoke a command handler with the given arguments.
     *
     * @param player The player invoking the command or null for the server console.
     * @param rawMessage The messaged used to invoke the command.
     */
    public void invoke(Player player, Player targetPlayer, String rawMessage) {
        var event = new ExecuteCommandEvent(player, targetPlayer, rawMessage);
        if (!event.call()) return;

        player = event.getSender();
        targetPlayer = event.getTarget();
        rawMessage = event.getCommand();

        rawMessage = rawMessage.trim();
        if (rawMessage.isEmpty()) {
            return;
        }

        String[] split = rawMessage.split(" ");
        String label = split[0].toLowerCase();
        List<String> args = new ArrayList<>(Arrays.asList(split).subList(1, split.length));
        String playerId = player.getAccount().getId();
        CommandHandler handler = this.getHandler(label);
        if (handler == null) {
            CommandHandler.sendMessage(player, "The command does not exist.");
            return;
        }

        Command annotation = this.annotations.get(label);
        targetPlayer = this.getTargetPlayer(player, targetPlayer, args, annotation.targetRequirement() != Command.TargetRequirement.NONE);
        if(targetPlayer == null || !Main.getPermissionHandler().checkPermission(player, annotation.permission())) {
            return;
        }

        if(annotation.disabled()) {
            CommandHandler.sendMessage(player, "The command is disabled by the administrator.");
            return;
        }

        Command.TargetRequirement targetRequirement = annotation.targetRequirement();
        if (targetRequirement != Command.TargetRequirement.NONE) {
            if ((targetRequirement == Command.TargetRequirement.ONLINE) && !targetPlayer.isOnline()) {
                CommandHandler.sendMessage(player, "The player must be online.");
                return;
            }

            if ((targetRequirement == Command.TargetRequirement.OFFLINE) && targetPlayer.isOnline()) {
                CommandHandler.sendMessage(player, "The player must be offline.");
                return;
            }
        }

        final var playerF = player;
        final var targetPlayerF = targetPlayer;
        final var handlerF = handler;
        Runnable runnable = () -> handlerF.execute(playerF, targetPlayerF, args);
        if (annotation.threading()) {
            new Thread(runnable).start();
        } else {
            runnable.run();
        }

        Main.getLogger().debug("[Chat] "+player.getAccount().getUsername()+" used the command: "+rawMessage);
    }
}