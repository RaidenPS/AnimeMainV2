package raidengame.game.chat.perms;

// Imports
import raidengame.game.player.Player;

public interface PermissionHandler {
    /**
     * Checks if player have permissions to execute the current command on himself or somebody else.
     * @param player The player
     * @param permissionNode The permission need to execute on himself.
     * @return True if he has permission.
     */
    boolean checkPermission(Player player, String permissionNode);
}