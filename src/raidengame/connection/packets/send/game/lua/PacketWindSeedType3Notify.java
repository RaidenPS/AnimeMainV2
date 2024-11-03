package raidengame.connection.packets.send.game.lua;

// Imports
import raidengame.Main;
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.misc.classes.FileMan;
import com.google.protobuf.ByteString;
import java.io.IOException;

// Protocol buffers
import raidengame.cache.protobuf.WindSeedType3NotifyOuterClass.WindSeedType3Notify;

/**
 * Packet to execute lua script on Level 3.
 */
public class PacketWindSeedType3Notify extends BasePacket {
    public PacketWindSeedType3Notify(String luaFile) {
        super(PacketIds.WindSeedType3Notify);

        try {
            byte[] data = FileMan.readResourcesFile(luaFile);
            if(data == null) return;

            WindSeedType3Notify proto =
                WindSeedType3Notify
                        .newBuilder()
                        .setPayload(ByteString.copyFrom(data))
                        .build();

            this.setData(proto);
        }catch (IOException e) {
            Main.getLogger().error("[WindSeedType3Notify] Unable to read the lua file %s", luaFile);
        }
    }
}