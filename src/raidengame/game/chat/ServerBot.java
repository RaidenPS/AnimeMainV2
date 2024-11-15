package raidengame.game.chat;

// Imports
import raidengame.enums.FriendOnlineState;
import raidengame.enums.player.PlatformType;
import raidengame.misc.classes.Timestamp;

// Protocol buffers
import raidengame.cache.protobuf.BirthdayOuterClass.Birthday;
import raidengame.cache.protobuf.FriendBriefOuterClass.FriendBrief;
import raidengame.cache.protobuf.ProfilePictureOuterClass.ProfilePicture;
import raidengame.cache.protobuf.SocialDetailOuterClass.SocialDetail;

public final class ServerBot {
    public static final int UID = 10000;
    public static final String OnlineId = "1000";
    private static final String Username = "<color=#fc033d>Console [ RAIDEN PS ]</color>";
    private static final String Signature = "The command prompt where you execute all commands.";
    private static final int AR = 0;
    private static final int WR = 0;
    private static final int NameCardId = 210201;
    private static final int AvatarId = 10000007;

    public static FriendBrief getFriendObject() {
        return FriendBrief.newBuilder()
                .setUid(UID)
                .setOnlineId(OnlineId)
                .setNickname(Username)
                .setSignature(Signature)
                .setLevel(AR)
                .setWorldLevel(WR)
                .setNameCardId(NameCardId)
                .setProfilePicture(ProfilePicture.newBuilder().setHeadImageId(AvatarId))
                .setLastActiveTime(Timestamp.getCurrentSeconds())
                .setOnlineState(FriendOnlineState.ONLINE.getValue())
                .setParam(1)
                .setIsGameSource(true)
                .setIsPsnSource(true) // appear in playstation
                .setIsMpModeAvailable(false)
                .setIsInDuel(false)
                .setPlatformType(PlatformType.Editor.getValue())
                .build();
    }

    public static SocialDetail.Builder getSocialDetailObject()  {
        return SocialDetail.newBuilder()
                .setUid(UID)
                .setOnlineId(OnlineId)
                .setNickname(Username)
                .setSignature(Signature)
                .setProfilePicture(ProfilePicture.newBuilder().setHeadImageId(AvatarId))
                .setNameCardId(NameCardId)
                .setBirthday(Birthday.newBuilder().setDay(26).setMonth(6).build())
                .setLevel(AR)
                .setWorldLevel(WR)
                .setIsShowAvatar(false)
                .setIsShowConstellationNum(false)
                .setIsMpModeAvailable(false)
                .setParam(1)
                .setIsPlaying50VersionValue(1);
    }
}