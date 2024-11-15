package raidengame.game.chat.commands;

// Imports
import raidengame.game.chat.Command;
import raidengame.game.chat.CommandHandler;
import raidengame.game.player.Player;
import java.util.List;

// Packets
import raidengame.connection.packets.send.game.lua.PacketPlayerLuaShellNotify;

// Protocol buffers
import raidengame.cache.protobuf.PlayerLuaShellNotifyOuterClass.PlayerLuaShellNotify.LuaShellType;

@Command(
        label = "eval",
        aliases = {"exec", "luac"},
        description = "Executes any lua scripted file using PlayerLuaShellNotify.",
        usage = "normal|security|shellcode*,filename*",
        permission = "lua.exec",
        disabled = true
)
public final class EvalCommand implements CommandHandler {
    @Override
    public void execute(final Player sender, final Player targetPlayer, final List<String> args) {
        if(args.size() != 2) {
            CommandHandler.sendMessage(sender, "Invalid arguments.");
            return;
        }

        String type = args.getFirst();
        LuaShellType shellType = switch (type) {
            case "normal" -> LuaShellType.LUASHELL_NORMAL;
            case "security" -> LuaShellType.LUASHELL_SECURITY;
            case "shellcode" -> LuaShellType.LUASHELL_SHELL_CODE;
            default -> LuaShellType.LUASHELL_NONE;
        };
        String file = "lua/" + args.get(1) + ".luac";

        targetPlayer.sendPacket(new PacketPlayerLuaShellNotify(file, 1, 1, shellType));
        CommandHandler.sendMessage(sender, "Done.");
    }
}