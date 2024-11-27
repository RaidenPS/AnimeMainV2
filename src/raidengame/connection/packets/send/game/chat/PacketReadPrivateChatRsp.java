package raidengame.connection.packets.send.game.chat;

// Imports
import raidengame.connection.base.BasePacket;
import raidengame.connection.base.PacketIds;

// Protocol buffers
import raidengame.cache.protobuf.ReadPrivateChatRspOuterClass.ReadPrivateChatRsp;

/**
 * Packet to read the private chat.
 */
public class PacketReadPrivateChatRsp extends BasePacket {
    public PacketReadPrivateChatRsp(int retcode) {
        super(PacketIds.ReadPrivateChatRsp);

        ReadPrivateChatRsp proto =
                ReadPrivateChatRsp.newBuilder()
                        .setRetcode(retcode)
                        .build();

        this.setData(proto);
    }
}