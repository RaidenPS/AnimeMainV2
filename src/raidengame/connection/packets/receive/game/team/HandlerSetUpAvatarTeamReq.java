package raidengame.connection.packets.receive.game.team;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;

// Protocol buffers
import raidengame.cache.protobuf.SetUpAvatarTeamReqOuterClass.SetUpAvatarTeamReq;

/**
 * Adds avatars to given team.
 */
@PacketOpcode(PacketIds.SetUpAvatarTeamReq)
public class HandlerSetUpAvatarTeamReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        SetUpAvatarTeamReq req = SetUpAvatarTeamReq.parseFrom(payload);

        session.getPlayer().getTeamManager().setupAvatarTeam(req.getTeamId(), req.getAvatarTeamGuidListList());
    }
}