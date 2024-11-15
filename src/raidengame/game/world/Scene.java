package raidengame.game.world;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.enums.game.scene.SceneType;
import raidengame.game.player.Player;
import lombok.Getter;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

// Packets
import raidengame.connection.packets.send.scene.PacketSceneTimeNotify;
import raidengame.resources.excels.scene.SceneData;

public class Scene {
    @Getter private final World world;
    @Getter private final SceneData sceneData;
    @Getter private final List<Player> players;
    @Getter private boolean isPaused;
    private final int sceneId;
    private final long startWorldTime;

    public Scene(World world, int sceneId) {
        // Non-Resources way
        this.world = world;
        this.sceneData = null;
        this.players = new CopyOnWriteArrayList<>();
        this.sceneId = sceneId;
        this.isPaused = false;
        this.startWorldTime = world.getWorldTime();
    }

    public Scene(World world, SceneData sceneData) {
        this.world = world;
        this.sceneData = sceneData;
        this.players = new CopyOnWriteArrayList<>();
        this.sceneId = sceneData.getId();
        this.isPaused = false;
        this.startWorldTime = world.getWorldTime();
    }

    /**
     * Adds a player to the scene.
     * @param player The player to add.
     */
    public synchronized void addPlayer(Player player) {
        if (this.players.contains(player)) {
            // Player is already in the scene.
            return;
        }

        if (player.getScene() != null) {
            player.getScene().removePlayer(player);
        }

        player.setSceneId(this.getId());
        player.setScene(this);
        this.players.add(player);
    }

    /**
     * Removes a player from the scene.
     * @param player The player to remove.
     */
    public synchronized void removePlayer(Player player) {
        this.players.remove(player);
        player.setScene(null);
    }
 
    /**
     * Gets the id of current scene.
     * @return The id of scene.
     */
    public int getId() {
        return this.sceneId;
    }

    /**
     * Gets the type of current scene.
     * @return The scene type.
     */
    public SceneType getSceneType() {
        return (this.sceneData == null) ? SceneType.SCENE_WORLD : this.sceneData.getSceneType();
    }

    /**
     * Gets the time in seconds since the scene started.
     *
     * @return The time in seconds since the scene started.
     */
    public int getSceneTime() {
        return (int) (this.world.getWorldTime() - this.startWorldTime);
    }

    /**
     * Sends a packet to every player in current scene.
     * @param packet The packet to send.
     */
    public void broadcastPacket(BasePacket packet) {
        for (Player player : this.players) {
            player.getSession().send(packet);
        }
    }

    /**
     * Sets the scene's pause state. Sends the current scene's time to all players.
     *
     * @param paused The new pause state.
     */
    public void setPaused(boolean paused) {
        if (this.isPaused != paused) {
            this.isPaused = paused;
            this.broadcastPacket(new PacketSceneTimeNotify(this));
        }
    }
}