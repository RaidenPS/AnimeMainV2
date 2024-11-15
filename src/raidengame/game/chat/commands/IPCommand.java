package raidengame.game.chat.commands;

// Imports
import raidengame.game.chat.Command;
import raidengame.game.chat.CommandHandler;
import raidengame.game.player.Player;
import java.util.List;

@Command(
        label = "ip",
        description = "Gets the IP Address of given player.",
        usage = "@UID",
        permission = "moderation.ip",
        targetRequirement = Command.TargetRequirement.ONLINE
)
public class IPCommand implements CommandHandler {
    @Override
    public void execute(final Player sender, final Player targetPlayer, final List<String> args) {
        CommandHandler.sendMessage(sender, targetPlayer.getNickname() +"'s IP Address is " + targetPlayer.getIPAddress());
    }
}