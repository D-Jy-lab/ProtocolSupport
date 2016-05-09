package protocolsupport.protocol.packet.middlepacketimpl.serverbound.play.v_1_4_1_5;

import net.minecraft.server.v1_9_R1.Packet;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middlepacket.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middlepacketimpl.PacketCreator;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class PositionLook extends ServerBoundMiddlePacket {

	protected double x;
	protected double y;
	protected double yhead;
	protected double z;
	protected float yaw;
	protected float pitch;
	protected boolean onGround;

	@Override
	public void readFromClientData(PacketDataSerializer serializer) {
		 x = serializer.readDouble();
		 y = serializer.readDouble();
		 yhead = serializer.readDouble();
		 z = serializer.readDouble();
		 yaw = serializer.readFloat();
		 pitch = serializer.readFloat();
		 onGround = serializer.readBoolean();
	}

	@Override
	public RecyclableCollection<? extends Packet<?>> toNative() throws Exception {
		if ((y == -999.0D) && (yhead == -999.0D)) {
			PacketCreator creator = PacketCreator.create(ServerBoundPacket.PLAY_LOOK.get());
			creator.writeFloat(yaw);
			creator.writeFloat(pitch);
			creator.writeBoolean(onGround);
			return RecyclableSingletonList.create(creator.create());
		} else {
			if (!sharedstorage.isTeleportConfirmNeeded()) {
				PacketCreator creator = PacketCreator.create(ServerBoundPacket.PLAY_POSITION_LOOK.get());
				creator.writeDouble(x);
				creator.writeDouble(y);
				creator.writeDouble(z);
				creator.writeFloat(yaw);
				creator.writeFloat(pitch);
				creator.writeBoolean(onGround);
				return RecyclableSingletonList.create(creator.create());
			} else {
				int teleportId = sharedstorage.tryTeleportConfirm(x, y, z);
				if (teleportId == -1) {
					return RecyclableEmptyList.get();
				} else {
					PacketCreator creator = PacketCreator.create(ServerBoundPacket.PLAY_TELEPORT_ACCEPT.get());
					creator.writeVarInt(teleportId);
					return RecyclableSingletonList.create(creator.create());
				}
			}
		}
	}

}