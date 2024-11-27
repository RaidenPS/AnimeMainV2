package raidengame.connection.packets.send.game.lua;

// Imports
import raidengame.Main;
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.misc.classes.FileMan;
import com.google.protobuf.ByteString;
import java.io.IOException;

// Protocol buffers
import raidengame.cache.protobuf.PlayerLuaShellNotifyOuterClass.PlayerLuaShellNotify;

/**
 * Executes a compiled lua script using PlayerLuaShellNotify.
 */
public class PacketPlayerLuaShellNotify extends BasePacket {
    public PacketPlayerLuaShellNotify(String luaFile, int id, int use_type, PlayerLuaShellNotify.LuaShellType shell_type) {
        super(PacketIds.PlayerLuaShellNotify);

        try {
            byte[] data = FileMan.readResourcesFile(luaFile);
            if(data == null) return;

            PlayerLuaShellNotify proto =
                    PlayerLuaShellNotify.newBuilder()
                            .setId(id)
                            .setUseType(use_type)
                            .setLuaShell(ByteString.copyFrom(data))
                            .setShellType(shell_type)
                            .build();

            this.setData(proto);
        }catch (IOException e) {
            Main.getLogger().error("[PlayerLuaShellNotify] Unable to read the lua file %s", luaFile);
        }
    }
}