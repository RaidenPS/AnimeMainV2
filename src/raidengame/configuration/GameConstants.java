package raidengame.configuration;

// Imports
import raidengame.game.world.Position;

/**
 * Constants that are associated with the official game. Do not change if you dont know what are you doing.
 */
public final class GameConstants {
    // don't touch these if you don't know what are you doing.
    public static final int MINIMUM_MINOR_AGE = 13;
    public static final String connect_gate_ticket = "I_AM_SPYWARE_GIVE_ME_DATA_UWU";
    public static final String security_library_md5 = "f822bc67f41e4677e52e5099bb6a9c52";
    public static final int MAIN_CHARACTER_MALE = 10000005;
    public static final int MAIN_CHARACTER_FEMALE = 10000007;

    // nickname
    public static final int NICKNAME_MAX_LEN = 14;
    public static final int NICKNAME_DIGIT_MAX_LEN = 6;

    // default
    public static final int DEFAULT_NAME_CARD_ID = 210001;
    public static final Position SPAWN_POSITION = new Position(2747, 194, -1719);
    public static final String welcomeMessage = "Welcome to RaidenPS. Remember to wear mask and keep distance.";

    // inventory
    public static final int furnitureCapacity = 2800;
    public static final int weaponCapacity = 2000;
    public static final int itemCapacity = 2500;
    public static final int reliquaryCapacity = 3000;
    public static final int inventoryCapacity = 30000;

    // Friends
    public static final int friendRequestsMax = 50;
    public static final int friendsMax = 100;
    public static final int blockedsMax = 10;
}