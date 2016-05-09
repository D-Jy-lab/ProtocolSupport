package protocolsupport.protocol.packet.middlepacket.clientbound.play;

import java.io.IOException;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.packet.middlepacket.ClientBoundMiddlePacket;

public abstract class MiddleWorldBorder<T> extends ClientBoundMiddlePacket<T> {

	protected Action action;
	protected double radius;
	protected double oldRadius;
	protected double newRadius;
	protected long speed;
	protected double x;
	protected double z;
	protected int teleportBound;
	protected int warnTime;
	protected int warnBlocks;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) throws IOException {
		action = Action.values()[serializer.readVarInt()];
		switch (action) {
			case SET_SIZE: {
				radius = serializer.readDouble();
				break;
			}
			case LERP_SIZE: {
				oldRadius = serializer.readDouble();
				newRadius = serializer.readDouble();
				speed = serializer.readVarLong();
				break;
			}
			case SET_CENTER: {
				x = serializer.readDouble();
				z = serializer.readDouble();
				break;
			}
			case INIT: {
				x = serializer.readDouble();
				z = serializer.readDouble();
				oldRadius = serializer.readDouble();
				newRadius = serializer.readDouble();
				speed = serializer.readVarLong();
				teleportBound = serializer.readVarInt();
				warnTime = serializer.readVarInt();
				warnBlocks = serializer.readVarInt();
				break;
			}
			case SET_WARN_TIME: {
				warnTime = serializer.readVarInt();
				break;
			}
			case SET_WARN_BLOCKS: {
				warnBlocks = serializer.readVarInt();
				break;
			}
		}
	}

	protected static enum Action {
		SET_SIZE, LERP_SIZE, SET_CENTER, INIT, SET_WARN_TIME, SET_WARN_BLOCKS
	}

}