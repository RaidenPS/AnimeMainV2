package raidengame.game.chat;

// Imports
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
    String label() default "";

    String[] aliases() default {};

    String description() default "";

    String usage() default "";

    String permission() default "";

    TargetRequirement targetRequirement() default TargetRequirement.NONE;

    boolean threading() default false;

    boolean disabled() default false;

    enum TargetRequirement {
        NONE,
        OFFLINE,
        ONLINE,
        BOTH
    }
}