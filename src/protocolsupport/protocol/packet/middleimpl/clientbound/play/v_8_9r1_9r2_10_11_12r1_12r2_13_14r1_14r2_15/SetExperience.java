package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSetExperience;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class SetExperience extends MiddleSetExperience {

	public SetExperience(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData setexperience = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_SET_EXPERIENCE);
		setexperience.writeFloat(exp);
		VarNumberSerializer.writeVarInt(setexperience, level);
		VarNumberSerializer.writeVarInt(setexperience, totalExp);
		codec.write(setexperience);
	}

}
