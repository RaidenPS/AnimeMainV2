package raidengame.game.chat.perms;

// Imports
import raidengame.game.chat.CommandHandler;
import raidengame.game.player.Player;
import raidengame.game.player.account.Account;

public class DefaultPermissionHandler implements PermissionHandler {
    @Override
    public boolean checkPermission(Player player, String permissionNode) {
        if (player == null) {
            return true;
        }

        Account account = player.getAccount();
        if (!permissionNode.isEmpty() && !account.hasPermission(permissionNode)) {
            CommandHandler.sendMessage(player, "You don't have permission to execute this command.");
            return false;
        }
        return true;
    }
}