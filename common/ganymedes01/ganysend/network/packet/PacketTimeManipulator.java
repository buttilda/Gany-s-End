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

public class PacketTimeManipulator extends CustomPacket {

	private int x, y, z;
	private boolean revertTime, advanceTime;

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
	public void execute() {
		GanysEnd.proxy.handleTimeManipulatorPacket(x, y, z, revertTime, advanceTime);
	}
}
