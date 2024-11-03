package raidengame.connection.packets.send.game.lua;

// Imports
import raidengame.Main;
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.misc.classes.FileMan;
import com.google.protobuf.ByteString;
import java.io.IOException;

// Protocol buffers
import raidengame.cache.protobuf.WindSeedType1NotifyOuterClass.WindSeedType1Notify;

/**
 * Packet to execute lua script on Level 1.
 */
public class PacketWindSeedType1Notify extends BasePacket {
    public PacketWindSeedType1Notify(String luaFile) {
        super(PacketIds.WindSeedType1Notify);

        try {
            byte[] data = FileMan.readResourcesFile(luaFile);
            if(data == null) return;

            WindSeedType1Notify proto =
                WindSeedType1Notify
                        .newBuilder()
                        .setPayload(ByteString.copyFrom(data))
                        .build();

            this.setData(proto);
        }catch (IOException e) {
            Main.getLogger().error("[WindSeedType1Notify] Unable to read the lua file %s", luaFile);
        }
    }
}