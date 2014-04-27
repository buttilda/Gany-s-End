package ganymedes01.ganysend.lib;

import io.netty.buffer.ByteBuf;

public interface IPacketHandlingTile {

	void readPacketData(ByteBuf buffer);
}