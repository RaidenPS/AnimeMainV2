package raidengame.game.player.social;

// Imports
import dev.morphia.annotations.Entity;
import lombok.Getter;
import lombok.Setter;

// Protocol buffers
import raidengame.cache.protobuf.BirthdayOuterClass.Birthday;

@Getter
@Setter
@Entity
public class PlayerBirthday {
    int day;
    int month;

    /**
     * Creates a new instance of PlayerBirthday. (Empty birthday)
     */
    public PlayerBirthday() {
        this.day = 0;
        this.month = 0;
    }

    /**
     * Creates a new instance of PlayerBirthday.
     * @param day Day of birth.
     * @param month Month of birth.
     */
    public PlayerBirthday(int day, int month) {
        this.day = day;
        this.month = month;
    }

    /**
     * Creates a new instance of Birthday protobuf.
     * @return The birthday protobuf.
     */
    public Birthday.Builder toProto() {
        if (this.getDay() > 0) {
            return Birthday.newBuilder().setDay(this.getDay()).setMonth(this.getMonth());
        }
        return Birthday.newBuilder();
    }

    /**
     * Sets a birthday.
     * @param birth Birthday object.
     * @return PlayerBirthday instance.
     */
    public PlayerBirthday set(PlayerBirthday birth) {
        this.day = birth.day;
        this.month = birth.month;

        return this;
    }

    /**
     * Sets a birthday
     * @param day Day of birth.
     * @param month Month of birth.
     * @return PlayerBirthday instance
     */
    public PlayerBirthday set(int day, int month) {
        this.day = day;
        this.month = month;
        return this;
    }
}