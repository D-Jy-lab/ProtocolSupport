package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_beta;

import java.util.EnumMap;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityAnimation;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityAnimation extends MiddleEntityAnimation {

	protected static final EnumMap<Animation, Integer> animationIds = new EnumMap<>(Animation.class);
	static {
		animationIds.put(Animation.SWING_ARM, 1);
		animationIds.put(Animation.WAKE_UP, 3);
	}

	public EntityAnimation(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_ANIMATION_ID);
		serializer.writeInt(entityId);
		serializer.writeByte(animationIds.getOrDefault(animation, 0));
		return RecyclableSingletonList.create(serializer);
	}

}