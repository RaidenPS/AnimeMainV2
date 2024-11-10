package raidengame.misc.classes;

public final class Timestamp {
    /**
     * Gives the current seconds.
     * @return current seconds
     */
    public static int getCurrentSeconds() {
        return (int) (System.currentTimeMillis() / 1000.0);
    }
}
