package raidengame.connection.packets.receive.game.avatar;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;

// Packets
import raidengame.connection.packets.send.game.avatar.PacketGetAvatarRecommendedArtifactsRsp;

// Protocol buffers
import raidengame.cache.protobuf.GetAvatarRecommendedArtifactsReqOuterClass.GetAvatarRecommendedArtifactsReq;

/**
 * Handler for suggest the best artifacts/weapons and other stuff in character.
 */
@PacketOpcode(PacketIds.GetAvatarRecommendedArtifactsReq)
public class HandlerGetAvatarRecommendedArtifactsReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        GetAvatarRecommendedArtifactsReq req = GetAvatarRecommendedArtifactsReq.parseFrom(data);

        session.send(new PacketGetAvatarRecommendedArtifactsRsp(req));
    }
}