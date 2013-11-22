package ganymedes01.ganysend.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.power.PowerHandler.PowerReceiver;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class TileEntityEnergyPortal extends TileEntity implements IPowerReceptor {

	private int receiverX, receiverY, receiverZ, receiverDim;
	private boolean tagged;

	public void setReceiverCoord(int x, int y, int z, int dim) {
		receiverX = x;
		receiverY = y;
		receiverZ = z;
		receiverDim = dim;
		tagged = true;
	}

	public boolean isTagged() {
		return tagged;
	}

	@Override
	public void updateEntity() {
		if (!worldObj.isRemote)
			if (tagged)
				if (receiverDim == worldObj.provider.dimensionId) {
					TileEntity tile = worldObj.getBlockTileEntity(receiverX, receiverY, receiverZ);
					if (!(tile instanceof IPowerReceptor)) {
						tagged = false;
						int id = worldObj.getBlockId(xCoord, yCoord, zCoord);
						worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, id);
					}
				}
	}

	@Override
	public PowerReceiver getPowerReceiver(ForgeDirection side) {
		if (!worldObj.isRemote)
			if (receiverDim == worldObj.provider.dimensionId && tagged) {
				TileEntity tile = worldObj.getBlockTileEntity(receiverX, receiverY, receiverZ);
				if (tile instanceof IPowerReceptor) {
					IPowerReceptor machine = (IPowerReceptor) tile;
					return machine.getPowerReceiver(side);
				}
			}
		return null;
	}

	@Override
	public void doWork(PowerHandler workProvider) {
		if (!worldObj.isRemote)
			if (receiverDim == worldObj.provider.dimensionId && tagged) {
				TileEntity tile = worldObj.getBlockTileEntity(receiverX, receiverY, receiverZ);
				if (tile instanceof IPowerReceptor) {
					IPowerReceptor machine = (IPowerReceptor) tile;
					machine.doWork(workProvider);
				}
			}
	}

	@Override
	public World getWorld() {
		return worldObj;
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		super.readFromNBT(data);
		receiverX = data.getInteger("X");
		receiverY = data.getInteger("Y");
		receiverZ = data.getInteger("Z");
		receiverDim = data.getInteger("DIM");
		tagged = data.getBoolean("tagged");
	}

	@Override
	public void writeToNBT(NBTTagCompound data) {
		super.writeToNBT(data);
		data.setInteger("X", receiverX);
		data.setInteger("Y", receiverY);
		data.setInteger("Z", receiverZ);
		data.setInteger("DIM", receiverDim);
		data.setBoolean("tagged", tagged);
	}
}