package raidengame.game.chat;

// Imports
import raidengame.game.player.Player;
import raidengame.server.GameServer;

public interface ChatSystemHandler {
    GameServer getServer();

    void handlePullRecentChatReq(Player player, int seq, int pull_num);

    void handlePullPrivateChatReq(Player player, int partnerId, int seq, int pull_num);

    void sendPrivateMessageFromServer(int targetUid, String message);

    void sendPrivateMessage(Player player, int targetUid, int icon);

    void sendPrivateMessage(Player player, int targetUid, String message);
}