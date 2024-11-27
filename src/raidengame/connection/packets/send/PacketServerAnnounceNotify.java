package raidengame.connection.packets.send;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;
import raidengame.game.systems.announce.AnnounceItem;

// Protocol buffers
import raidenemu.cache.protobuf.ServerAnnounceNotifyOuterClass.ServerAnnounceNotify;

/**
 * Packet to send a server announcement.
 */
public class PacketServerAnnounceNotify extends BasePacket {
    /**
     * Sends game announcement.
     * @param announceItem The announcement as object.
     */
    public PacketServerAnnounceNotify(AnnounceItem announceItem) {
        super(PacketIds.ServerAnnounceNotify);

        ServerAnnounceNotify proto =
                ServerAnnounceNotify.newBuilder()
                        .addAnnounceDataList(announceItem.toProto())
                        .build();

        this.setData(proto);
    }
}