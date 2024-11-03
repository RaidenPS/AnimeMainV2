package raidengame.connection.base;

public final class PacketRetcodes {
    // Normal
    public static final int RETCODE_SUCC = 0;
    public static final int RETCODE_FAIL = -1;
    public static final int RET_INTERNAL_ERROR = 1;
    public static final int RET_UNKNOWN_ERROR = 2;
    public static final int RET_FREQUENT = 3;

    // GetPlayerTokenRsp and PlayerLoginRsp
    public static final int RET_BETA_ACCESS_DENIED = 10;
    public static final int RET_GAME_UNDER_MAINTENANCE = 11;
    public static final int RET_ACCOUNT_VEIRFY_ERROR = 12;
    public static final int RET_ACCOUNT_FREEZE = 13;
    public static final int RET_REPEAT_LOGIN = 14;
    public static final int RET_CLIENT_VERSION_ERROR = 15;
    public static final int RET_TOKEN_ERROR = 16;
    public static final int RET_ACCOUNT_NOT_EXIST = 17;
    public static final int RET_WAIT_OTHER_LOGIN = 18;
    public static final int RET_ANOTHER_LOGIN = 19;
    public static final int RET_CLIENT_FORCE_UPDATE = 20;
    public static final int RET_BLACK_UID = 21;
    public static final int RET_MAX_PLAYER = 25;
    public static final int RET_BETA_REGISTER_IS_FULL = 30;
    public static final int RET_CHECKSUM_INVALID = 31;
    public static final int RET_BLACK_REGISTER_IP = 32;
    public static final int RET_BLACK_LOGIN_IP = 37;
    public static final int RET_MINOR_REGISTER_FOBIDDEN = 41;
    public static final int RET_SECURITY_LIBRARY_ERROR = 42;
    public static final int RET_GATE_TICKET_CHECK_ERROR = 43;

    // SetPlayerBornDataRsp and SetPlayerNameRsp
    public static final int RET_NICKNAME_UTF_8_ERROR = 130;
    public static final int RET_NICKNAME_TOO_LONG = 131;
    public static final int RET_NICKNAME_WORD_ILLEGAL = 132;
    public static final int RET_NICKNAME_TOO_MANY_DIGITS = 133;
    public static final int RET_NICKNAME_IS_EMPTY = 134;
    public static final int RET_NICKNAME_MONTHLY_LIMIT = 135;
    public static final int RET_NICKNAME_NOT_CHANGED = 136;

    // Chat
    public static final int RET_CHAT_FORBIDDEN = 798;
    public static final int RET_CHAT_FREQUENTLY = 800;
    public static final int RET_RPIVATE_CHAT_INVALID_CONTENT_TYPE = 8901;
    public static final int RET_PRIVATE_CHAT_TARGET_IS_NOT_FRIEND = 8902;
    public static final int RET_PRIVATE_CHAT_CONTENT_NOT_SUPPORTED = 8903;
    public static final int RET_PRIVATE_CHAT_CONTENT_TOO_LONG = 8904;
    public static final int RET_PRIVATE_CHAT_PULL_TOO_FAST = 8905;
    public static final int RET_PRIVATE_CHAT_REPEAT_READ = 8906;
    public static final int RET_PRIVATE_CHAT_READ_NOT_FRIEND = 8907;

    // ToTheMoonEnterSceneRsp and PathfindingEnterSceneRsp
    public static final int RET_PATHFINDING_DATA_NOT_EXIST = 6001;
    public static final int RET_PATHFINDING_DESTINATION_NOT_EXIST = 6002;
    public static final int RET_PATHFINDING_ERROR_SCENE = 6003;
    public static final int RET_PATHFINDING_SCENE_DATA_LOADING = 6004;
    public static final int RET_TOTHEMOON_ERROR_SCENE = 6301;
    public static final int RET_TOTHEMOON_PLAYER_NOT_EXIST = 6302;

    // SetPlayerBirthdayRsp
    public static final int RET_BIRTHDAY_CANNOT_BE_SET_TWICE = 7009;
    public static final int RET_BIRTHDAY_FORMAT_ERROR = 7022;

    // SetPlayerSignatureRsp
    public static final int RET_SIGNATURE_ILLEGAL = 7011;
    public static final int RET_SIGNATURE_NOT_CHANGED = 7040;
    public static final int RET_SIGNATURE_MONTHLY_LIMIT = 7041;

    // SetNameCardRsp
    public static final int RET_NAME_CARD_NOT_UNLOCKED = 7014;

    // PlayerReportRsp
    public static final int RET_REPORT_CD = 7026;
    public static final int RET_REPORT_CONTENT_ILLEGAL = 7027;

    // GetPlayerSocialDetailRsp
    public static final int RET_PSN_GET_PLAYER_SOCIAL_DETAIL_FAIL = 7043;

    // Friends
    public static final int RET_FRIEND_COUNT_EXCEEDED = 7001;
    public static final int RET_PLAYER_NOT_EXIST = 7002;
    public static final int RET_ALREADY_SENT_ADD_REQUEST = 7003;
    public static final int RET_ASK_FRIEND_LIST_FULL = 7004;
    public static final int RET_PLAYER_ALREADY_IS_FRIEND = 7005;
    public static final int RET_PLAYER_NOT_ASK_FRIEND = 7006;
    public static final int RET_TARGET_FRIEND_COUNT_EXCEED = 7007;
    public static final int RET_NOT_FRIEND = 7008;
    public static final int RET_CANNOT_ADD_SELF_FRIEND = 7010;
    public static final int RET_PS_PLAYER_CANNOT_ADD_FRIENDS = 7012;
    public static final int RET_PS_PLAYER_CANNOT_REMOVE_FRIENDS = 7013;
    public static final int RET_BLACKLIST_PLAYER_CANNOT_ADD_FRIEND = 7019;
    public static final int RET_CANNOT_ADD_TARGET_FRIEND = 7021;
}