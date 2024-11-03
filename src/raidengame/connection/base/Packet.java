package raidengame.connection.base;

// Imports
import raidengame.connection.GameSession;

public abstract class Packet {
    public abstract void handle(GameSession session, byte[] header, byte[] data) throws Exception;
}
