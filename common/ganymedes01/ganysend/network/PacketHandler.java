package ganymedes01.ganysend.network;

import ganymedes01.ganysend.network.packet.CustomPacket;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class PacketHandler implements IPacketHandler {

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		CustomPacket packetEE = PacketTypeHandler.buildPacket(packet.data);
		packetEE.execute(manager, player);
	}
}
