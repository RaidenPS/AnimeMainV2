package raidengame.game.chat.commands;

// Imports
import raidengame.game.chat.Command;
import raidengame.game.chat.CommandHandler;
import raidengame.game.player.Player;
import java.util.List;

@Command(
        label = "ping",
        description = "Gets the ping of given player.",
        usage = "@UID",
        permission = "moderation.ping",
        targetRequirement = Command.TargetRequirement.ONLINE
)
public class PingCommand implements CommandHandler {
    @Override
    public void execute(final Player sender, final Player targetPlayer, final List<String> args) {
        CommandHandler.sendMessage(sender, targetPlayer.getNickname() +"'s ping is " + targetPlayer.getPing());
    }
}