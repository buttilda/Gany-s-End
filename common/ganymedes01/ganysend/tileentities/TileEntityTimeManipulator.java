package ganymedes01.ganysend.tileentities;

import ganymedes01.ganysend.network.PacketTypeHandler;
import ganymedes01.ganysend.network.packet.PacketTimeManipulator;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class TileEntityTimeManipulator extends TileEntity {

	public boolean revertTime, advanceTime;

	@Override
	public void updateEntity() {
		if (worldObj.provider.dimensionId != 0)
			return;
		if (revertTime && !advanceTime)
			if (worldObj.provider.getWorldTime() >= 50)
				worldObj.provider.setWorldTime(worldObj.provider.getWorldTime() - 50);
			else {
				worldObj.provider.setWorldTime(0);
				revertTime = false;
			}

		if (advanceTime && !revertTime)
			if (worldObj.provider.getWorldTime() <= 12500 - 50)
				worldObj.provider.setWorldTime(worldObj.provider.getWorldTime() + 50);
			else {
				worldObj.provider.setWorldTime(12500);
				advanceTime = false;
			}
	}

	@Override
	public Packet getDescriptionPacket() {
		return PacketTypeHandler.populatePacket(new PacketTimeManipulator(xCoord, yCoord, zCoord, revertTime, advanceTime));
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		super.readFromNBT(data);
		revertTime = data.getBoolean("revertTime");
		advanceTime = data.getBoolean("advanceTime");
	}

	@Override
	public void writeToNBT(NBTTagCompound data) {
		super.writeToNBT(data);
		data.setBoolean("revertTime", revertTime);
		data.setBoolean("advanceTime", advanceTime);
	}
}
