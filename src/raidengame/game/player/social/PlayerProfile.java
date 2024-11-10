package raidengame.game.player.social;

// Imports
import raidengame.Main;
import raidengame.game.player.Player;
import raidengame.misc.classes.Timestamp;
import com.mongodb.lang.Nullable;
import dev.morphia.annotations.AlsoLoad;
import dev.morphia.annotations.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter
public class PlayerProfile {
    @AlsoLoad("id") private int uid;
    private int nameCard;
    private int avatarId;
    private String name;
    private String signature;
    private int playerLevel;
    private int worldLevel;
    private int lastActiveTime;
    private boolean isInDuel;
    private boolean isDuelObservable;
    private boolean isChatNoDisturb;
    private boolean isMultiplayerAvailable;
    @Getter private int enterHomeOption;

    /**
     * Creates a new instance of PlayerProfile.
     * @param player The player
     */
    public PlayerProfile(Player player) {
        this.uid = player.getUid();
        this.syncWithCharacter(player);
    }

    /**
     * Gets total time since last login of given player.
     * @return Days
     */
    public int getDaysSinceLogin() {
        return (int) Math.floor((Timestamp.getCurrentSeconds() - this.lastActiveTime) / 86400.0);
    }

    /**
     * Gets a player object from current id.
     * @return Player object.
     */
    @Nullable public Player getPlayer() {
        var player = Main.getGameServer().getPlayerByUid(this.uid, true);
        this.syncWithCharacter(player);
        return player;
    }

    /**
     * Synchronization of player's profile.
     * @param player The given player
     */
    public void syncWithCharacter(Player player) {
        /// TODO: Finish synchronization.

        if (player == null) {
            Main.getLogger().info("NOT OK");
            return;
        }

        this.uid = player.getUid();
        this.name = player.getNickname();
        this.avatarId = player.getHeadImage();
        this.signature = player.getSignature();
        this.nameCard = player.getNameCardId();
        this.playerLevel = player.getLevel();
        this.worldLevel = player.getWorldLevel();
        this.isInDuel = false;
        this.isDuelObservable = false;
        this.isChatNoDisturb = player.isChatNoDisturb();
        this.isMultiplayerAvailable = true;
        this.enterHomeOption = 0;
        this.updateLastActiveTime();
    }

    /**
     * Synchronizes the last active time.
     */
    private void updateLastActiveTime() {
        this.lastActiveTime = Timestamp.getCurrentSeconds();
    }
}