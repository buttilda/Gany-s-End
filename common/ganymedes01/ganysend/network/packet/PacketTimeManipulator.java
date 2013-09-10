package ganymedes01.ganysend.network.packet;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.network.PacketTypeHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.network.INetworkManager;
import cpw.mods.fml.common.network.Player;

public class PacketTimeManipulator extends CustomPacket {

	public int x, y, z;
	public boolean revertTime, advanceTime;

	public PacketTimeManipulator() {
		super(PacketTypeHandler.TIME_MANIPULATOR);
	}

	public PacketTimeManipulator(int x, int y, int z, boolean revertTime, boolean advanceTime) {
		super(PacketTypeHandler.TIME_MANIPULATOR);
		this.x = x;
		this.y = y;
		this.z = z;
		this.revertTime = revertTime;
		this.advanceTime = advanceTime;
	}

	@Override
	public void writeData(DataOutputStream data) throws IOException {
		data.writeInt(x);
		data.writeInt(y);
		data.writeInt(z);
		data.writeBoolean(revertTime);
		data.writeBoolean(advanceTime);
	}

	@Override
	public void readData(DataInputStream data) throws IOException {
		x = data.readInt();
		y = data.readInt();
		z = data.readInt();
		revertTime = data.readBoolean();
		advanceTime = data.readBoolean();
	}

	@Override
	public void execute(INetworkManager manager, Player player) {
		GanysEnd.proxy.handleTileWithItemPacket(x, y, z, revertTime, advanceTime);
	}
}
