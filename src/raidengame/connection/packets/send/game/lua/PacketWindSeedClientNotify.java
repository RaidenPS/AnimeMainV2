package raidengame.connection.packets.send.game.lua;

// Imports
import raidengame.Main;
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.game.player.Player;
import raidengame.misc.classes.FileMan;
import com.google.protobuf.ByteString;
import java.io.IOException;

// Protocol buffers
import raidengame.cache.protobuf.AreaNotifyOuterClass.AreaNotify;
import raidengame.cache.protobuf.WindSeedClientNotifyOuterClass.WindSeedClientNotify;

/**
 * Executes a compiled lua script using WindSeedClientNotify.
 */
public class PacketWindSeedClientNotify extends BasePacket {
    public PacketWindSeedClientNotify(Player player, String luaFile) {
        super(PacketIds.WindSeedClientNotify);

        try {
            byte[] data = FileMan.readResourcesFile(luaFile);
            if(data == null) return;

            WindSeedClientNotify proto =
                    WindSeedClientNotify.newBuilder()
                            .setAreaNotify(
                                    AreaNotify.newBuilder()
                                            .setAreaId(player.getAreaId())
                                            .setAreaType(player.getAreaType())
                                            .setAreaCode(ByteString.copyFrom(data))
                                            .build()
                            )
                            .build();

            this.setData(proto);
        }catch (IOException e) {
            Main.getLogger().error("[WindSeedClientNotify] Unable to read the lua file %s", luaFile);
        }
    }
}