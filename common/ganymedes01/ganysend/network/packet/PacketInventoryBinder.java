package ganymedes01.ganysend.network.packet;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.network.PacketTypeHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class PacketInventoryBinder extends CustomPacket {

	public int x, y, z;
	public String playerName;

	public PacketInventoryBinder() {
		super(PacketTypeHandler.INVENTORY_BINDER);
	}

	public PacketInventoryBinder(int x, int y, int z, String playerName) {
		super(PacketTypeHandler.INVENTORY_BINDER);
		this.x = x;
		this.y = y;
		this.z = z;
		this.playerName = playerName;
	}

	@Override
	public void writeData(DataOutputStream data) throws IOException {
		data.writeInt(x);
		data.writeInt(y);
		data.writeInt(z);
		data.writeUTF(playerName);
	}

	@Override
	public void readData(DataInputStream data) throws IOException {
		x = data.readInt();
		y = data.readInt();
		z = data.readInt();
		playerName = data.readUTF();
	}

	@Override
	public void execute() {
		GanysEnd.proxy.handleInventoryBinderPacket(x, y, z, playerName);
	}
}
