package raidengame.connection.packets.receive.game.social;

// Imports
import raidengame.connection.GameSession;
import raidengame.connection.base.*;

// Packets
import raidengame.connection.packets.send.game.social.PacketSetPlayerHeadImageRsp;

// Protocol buffers
import raidengame.cache.protobuf.SetPlayerHeadImageReqOuterClass.SetPlayerHeadImageReq;

/**
 * Handler for change player's profile avatar image.
 */
@PacketOpcode(PacketIds.SetPlayerHeadImageReq)
public class HandlerSetPlayerHeadImageReq extends Packet {
    @Override
    public void handle(GameSession session, byte[] header, byte[] data) throws Exception {
        SetPlayerHeadImageReq req = SetPlayerHeadImageReq.parseFrom(data);
        int id = req.getHeadImageId();
        var player = session.getPlayer();

        if(!player.getAvatars().hasAvatar(id) && !player.getSpecialProfilePictureList().contains(id)) {
            session.send(new PacketSetPlayerHeadImageRsp(id, PacketRetcodes.RET_PROFILE_PICTURE_NOT_UNLOCKED));
            return;
        }

        player.setHeadImage(id);
        session.send(new PacketSetPlayerHeadImageRsp(id, PacketRetcodes.RETCODE_SUCC));
    }
}