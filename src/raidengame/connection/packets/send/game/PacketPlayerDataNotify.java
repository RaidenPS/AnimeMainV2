package raidengame.connection.packets.send.game;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.game.player.Player;

// Protocol buffers
import raidengame.cache.protobuf.PlayerDataNotifyOuterClass.PlayerDataNotify;
import raidengame.cache.protobuf.PropValueOuterClass.PropValue;

/**
 * Packet for send player's nickname in the game world.
 */
public class PacketPlayerDataNotify extends BasePacket {
    public PacketPlayerDataNotify(Player player) {
        super(PacketIds.PlayerDataNotify, 2);

        PlayerDataNotify.Builder proto =
                PlayerDataNotify.newBuilder()
                        .setNickName(player.getNickname())
                        .setServerTime(System.currentTimeMillis())
                        .setRegionId(player.getRegionId())
                        .setIsFirstLoginToday(true);

        player.getProperties().forEach((key, value) -> {proto.putPropMap(key, PropValue.newBuilder().setType(key).setIval(value).setVal(value).build());});
        this.setData(proto.build());
    }
}