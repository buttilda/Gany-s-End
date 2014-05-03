package ganymedes01.ganysend.network;

import io.netty.buffer.ByteBuf;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public interface IPacketHandlingTile {

	void readPacketData(ByteBuf buffer);
}