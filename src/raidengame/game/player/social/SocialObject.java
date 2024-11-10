package raidengame.game.player.social;

// Imports
import raidengame.Main;
import raidengame.database.DatabaseHelper;
import raidengame.enums.FriendOnlineState;
import raidengame.enums.player.PlatformType;
import raidengame.game.player.Player;
import dev.morphia.annotations.*;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

// Protocol buffers
import raidengame.cache.protobuf.FriendBriefOuterClass.FriendBrief;
import raidengame.cache.protobuf.ProfilePictureOuterClass.ProfilePicture;

@Entity(value = "friendships", useDiscriminator = false)
public class SocialObject {
    @Id private ObjectId id;
    @Transient @Getter @Setter Player owner;
    @Indexed @Getter @Setter private int ownerId;
    @Indexed @Getter @Setter private int friendId;
    @Getter @Setter private boolean isFriend;
    @Getter @Setter private boolean isBlocked;
    @Getter @Setter private int askerId;
    @Getter @Setter private String remarkName;
    @Getter private PlayerProfile friendProfile;

    /**
     * Creates a new instance of SocialObject.
     * @param owner The owner.
     * @param friend The friend.
     * @param asker The asker.
     */
    public SocialObject(Player owner, Player friend, Player asker) {
        this.setOwner(owner);
        this.ownerId = owner.getUid();
        this.friendId = friend.getUid();
        this.remarkName = friend.getNickname();
        this.friendProfile = friend.getPlayerProfile();
        this.askerId = asker.getUid();
    }

    /**
     * Sets the friend profile.
     * @param player The player to set.
     */
    public void setProfile(Player player) {
        if (player == null || this.friendId != player.getUid()) return;
        this.friendProfile = player.getPlayerProfile();
    }

    /**
     * @return True if friend is online.
     */
    public boolean isOnline() {
        return this.friendProfile.getPlayer() != null;
    }

    /**
     * Creates an protobuf object of FriendBrief.
     * @return FriendBrief protobuf object
     */
    public FriendBrief toProto() {
        Player player = this.friendProfile.getPlayer();

        FriendBrief.Builder builder =
                FriendBrief.newBuilder()
                        .setUid(this.friendProfile.getUid())
                        .setNickname(this.friendProfile.getName())
                        .setRemarkName(this.getRemarkName())
                        .setSignature(this.friendProfile.getSignature())
                        .setProfilePicture(ProfilePicture.newBuilder().setHeadImageId(friendProfile.getAvatarId()))
                        .setIsChatNoDisturb(this.friendProfile.isChatNoDisturb())
                        .setIsInDuel(this.friendProfile.isInDuel())
                        .setIsDuelObservable(this.friendProfile.isDuelObservable())
                        .setIsGameSource(true) /// todo check
                        .setParam(1)
                        .setLevel(this.friendProfile.getPlayerLevel())
                        .setWorldLevel(this.friendProfile.getWorldLevel())
                        .setIsMpModeAvailable(true)
                        .setNameCardId(this.friendProfile.getNameCard())
                        .setOnlineState(this.isOnline() ? FriendOnlineState.ONLINE.getValue() : FriendOnlineState.OFFLINE.getValue())
                        .setFriendEnterHomeOption(0);

        if(player != null) {
            builder.setPlatformType(player.getPlatform().getValue()).setOnlineId(player.getOnlineId()).setIsPsnSource(player.getPlatform() == PlatformType.PS4 || player.getPlatform() == PlatformType.PS5);
        }

        return builder.build();
    }

    public void saveDatabase() {
        DatabaseHelper.saveFriendship(this);
    }

    public void delete() {
        DatabaseHelper.deleteFriendship(this);
    }
}