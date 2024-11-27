package raidengame.connection.packets.receive.game.avatar;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;

// Packets
import raidengame.connection.packets.send.game.avatar.PacketChangeAvatarRsp;

// Protocol buffers
import raidengame.cache.protobuf.ChangeAvatarReqOuterClass.ChangeAvatarReq;

/**
 * Handler for change player's avatar in current team.
 */
@PacketOpcode(PacketIds.ChangeAvatarReq)
public class HandlerChangeAvatarReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        ChangeAvatarReq req = ChangeAvatarReq.parseFrom(data);

        session.getPlayer().getTeamManager().changeAvatar(req.getGuid());

        session.send(new PacketChangeAvatarRsp(req.getGuid()));
    }
}