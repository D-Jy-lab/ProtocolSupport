package protocolsupport.protocol.v_1_5.clientboundtransformer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.AttributeKey;

import java.io.IOException;

import net.minecraft.server.v1_8_R2.EnumProtocol;
import net.minecraft.server.v1_8_R2.EnumProtocolDirection;
import net.minecraft.server.v1_8_R2.NetworkManager;
import net.minecraft.server.v1_8_R2.Packet;
import net.minecraft.server.v1_8_R2.PacketListener;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.pipeline.PublicPacketEncoder;

public class PacketEncoder extends MessageToByteEncoder<Packet<PacketListener>> implements PublicPacketEncoder {

	private ProtocolVersion version;
	public PacketEncoder(ProtocolVersion version) {
		this.version = version;
	}

	private static final EnumProtocolDirection direction = EnumProtocolDirection.CLIENTBOUND;
	private static final AttributeKey<EnumProtocol> currentStateAttrKey = NetworkManager.c;

	private final PacketTransformer[] transformers = new PacketTransformer[] {
			new HandshakePacketTransformer(),
			new PlayPacketTransformer(),
			new StatusPacketTransformer(),
			new LoginPacketTransformer()
	};

	@Override
	protected void encode(ChannelHandlerContext ctx, Packet<PacketListener> packet, ByteBuf output) throws Exception {
		Channel channel = ctx.channel();
		EnumProtocol currentProtocol = channel.attr(currentStateAttrKey).get();
		final Integer packetId = currentProtocol.a(direction, packet);
		if (packetId == null) {
			throw new IOException("Can't serialize unregistered packet");
		}
		PacketDataSerializer serializer = new PacketDataSerializer(output, version);
		transformers[currentProtocol.ordinal()].tranform(channel, packetId, packet, serializer);
		channel.flush();
	}

	@Override
	public void publicEncode(ChannelHandlerContext ctx, Packet<PacketListener> packet, ByteBuf output) throws Exception {
		encode(ctx, packet, output);
	}

}
