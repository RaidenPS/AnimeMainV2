package raidengame.game.chat.commands;

// Imports
import raidengame.game.chat.Command;
import raidengame.game.chat.CommandHandler;
import raidengame.game.player.Player;
import java.util.List;

// Packets
import raidengame.connection.packets.send.game.lua.*;

@Command(
        label = "windy",
        description = "Executes any lua scripted file using WindSeedType1-3.",
        usage = "level*,filename*",
        permission = "lua.exec"
)
public final class WindyCommand implements CommandHandler
{
    @Override
    public void execute(final Player sender, final Player targetPlayer, final List<String> args) {
        if(args.size() != 2) {
            CommandHandler.sendMessage(sender, "Invalid arguments.");
            return;
        }

        int level = Integer.parseInt(args.getFirst());
        String file = "lua/" + args.get(1) + ".luac";

        switch(level) {
            case 1:
                targetPlayer.sendPacket(new PacketWindSeedType1Notify(file));
                CommandHandler.sendMessage(sender, "Done. Level: 1");
                break;
            case 2:
                targetPlayer.sendPacket(new PacketWindSeedType2Notify(file));
                CommandHandler.sendMessage(sender, "Done. Level: 2");
                break;
            case 3:
                targetPlayer.sendPacket(new PacketWindSeedType3Notify(file));
                CommandHandler.sendMessage(sender, "Done. Level: 3");
                break;
            default:
                CommandHandler.sendMessage(sender, "Invalid level.");
                break;
        }
    }
}