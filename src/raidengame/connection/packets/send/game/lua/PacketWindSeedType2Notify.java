package raidengame.connection.packets.send.game.lua;

// Imports
import raidengame.Main;
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.misc.classes.FileMan;
import com.google.protobuf.ByteString;
import java.io.IOException;

// Protocol buffers
import raidengame.cache.protobuf.WindSeedType2NotifyOuterClass.WindSeedType2Notify;

/**
 * Packet to execute lua script on Level 2.
 */
public class PacketWindSeedType2Notify extends BasePacket {
    public PacketWindSeedType2Notify(String luaFile) {
        super(PacketIds.WindSeedType2Notify);

        try {
            byte[] data = FileMan.readResourcesFile(luaFile);
            if(data == null) return;

            WindSeedType2Notify proto =
                WindSeedType2Notify
                        .newBuilder()
                        .setPayload(ByteString.copyFrom(data))
                        .build();

            this.setData(proto);
        }catch (IOException e) {
            Main.getLogger().error("[WindSeedType2Notify] Unable to read the lua file %s", luaFile);
        }
    }
}