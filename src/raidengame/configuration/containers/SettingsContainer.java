package raidengame.configuration.containers;

// Imports
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SettingsContainer {
    // Boolean
    public boolean isDebug = true;
    public boolean isMaintenance = true;
    // Objects
    public KCPInfo kcpInfo = new KCPInfo();
    public DatabaseInfo databaseInfo = new DatabaseInfo();
    public Maintenance maintenance = new Maintenance();
    public GameInfo gameInfo = new GameInfo();

    public static class KCPInfo {
        public int kcpInterval = 3;
        public int kcpTimeout = 1000 * 30; // 30 seconds.
        public int kcpMtu = 1400;
        public int kcpSndwnd = 256;
        public int kcpRcvwnd = 256;
        public String gateserverIP = "127.0.0.1";
        public int gateserverPort = 8882;
    }

    public static class DatabaseInfo {
        public String mongourl = "mongodb://localhost:27017";
        public String databasename = "raidenps";
        public int startPlayerCounterPosition = 100001;
    }

    public static class Maintenance {
        public int start_time = 0;
        public int end_time = 0;
        public String message = "";
        public String url = "";
    }

    public static class GameInfo {
        public boolean isBeta = false;
        public List<String> betaAccountIds = new ArrayList<>();
        public int maxPlayers = -1;
        public int maxBetaPlayers = -1;
        public List<String> forbiddenIPs = new ArrayList<>(); // Arrays.asList("127.0.0.1", "127.0.0.2");
        public String gameVersion = "OSRELWin5.0.0";
        public String gameRawVersion = "OSRELWin5.0.0_R26458901_S26368837_D26487341";
        public boolean enableSwitchCharacters = true;
        public boolean enableResources = false;
        public boolean enableQuests = false;
        public int chatCooldown = 2; // 2 seconds
        public int reportCooldown = 30; // 30 seconds.
    }
}