package ganymedes01.ganysend.tileentities;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class TileEntityBlockShifter extends TileEntity {

	public int receiverX, receiverY, receiverZ, receiverDim;
	public boolean tagged;
	public boolean direction;

	public TileEntityBlockShifter() {
		receiverX = 0;
		receiverY = 0;
		receiverZ = 0;
		receiverDim = 0;
		tagged = false;
	}

	@Override
	public void updateEntity() {
		if (worldObj.isRemote)
			return;

		if (receiverDim == worldObj.provider.dimensionId)
			if (tagged)
				if (direction)
					teleportFromTo(xCoord, yCoord + 1, zCoord, receiverX, receiverY + 1, receiverZ);
				else
					teleportFromTo(receiverX, receiverY + 1, receiverZ, xCoord, yCoord + 1, zCoord);
	}

	protected void teleportFromTo(int fromX, int fromY, int fromZ, int toX, int toY, int toZ) {
		if (!worldObj.isAirBlock(fromX, fromY, fromZ))
			if (worldObj.isAirBlock(toX, toY, toZ)) {
				Block teleported = worldObj.getBlock(fromX, fromY, fromZ);
				if (teleported.getBlockHardness(worldObj, fromX, fromY, fromZ) > 0.0F)
					if (worldObj.getTileEntity(fromX, fromY, fromZ) != null) {
						worldObj.setBlock(toX, toY, toZ, teleported, worldObj.getBlockMetadata(fromX, fromY, fromZ), 3);
						NBTTagCompound data = new NBTTagCompound();
						worldObj.getTileEntity(fromX, fromY, fromZ).writeToNBT(data);
						worldObj.setTileEntity(toX, toY, toZ, TileEntity.createAndLoadEntity(data));
						if (worldObj.getTileEntity(fromX, fromY, fromZ) instanceof IInventory) {
							IInventory invt = (IInventory) worldObj.getTileEntity(fromX, fromY, fromZ);
							for (int i = 0; i < invt.getSizeInventory(); i++)
								invt.setInventorySlotContents(i, null);
						}
						worldObj.setBlockToAir(fromX, fromY, fromZ);
						worldObj.notifyBlockOfNeighborChange(fromX, fromY - 1, fromZ, Blocks.air);
						worldObj.notifyBlockOfNeighborChange(toX, toY, toZ, teleported);
					} else {
						worldObj.setBlock(toX, toY, toZ, teleported, worldObj.getBlockMetadata(fromX, fromY, fromZ), 3);
						worldObj.setBlockToAir(fromX, fromY, fromZ);
						worldObj.notifyBlocksOfNeighborChange(toX, toY, toZ, teleported);
						worldObj.notifyBlocksOfNeighborChange(fromX, fromY, fromZ, teleported);
					}
			}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("receiverX", receiverX);
		nbt.setInteger("receiverY", receiverY);
		nbt.setInteger("receiverZ", receiverZ);
		nbt.setInteger("receiverDim", receiverDim);
		nbt.setBoolean("tagged", tagged);
		nbt.setBoolean("direction", direction);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		receiverX = nbt.getInteger("receiverX");
		receiverY = nbt.getInteger("receiverY");
		receiverZ = nbt.getInteger("receiverZ");
		receiverDim = nbt.getInteger("receiverDim");
		tagged = nbt.getBoolean("tagged");
		direction = nbt.getBoolean("direction");
	}
}