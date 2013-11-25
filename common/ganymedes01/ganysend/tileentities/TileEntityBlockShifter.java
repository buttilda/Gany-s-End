package ganymedes01.ganysend.tileentities;

import net.minecraft.block.Block;
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

	public TileEntityBlockShifter() {
		receiverX = 0;
		receiverY = 0;
		receiverZ = 0;
		receiverDim = 0;
		tagged = false;
	}

	@Override
	public void updateEntity() {
		if (!worldObj.isRemote)
			if (receiverDim == worldObj.provider.dimensionId)
				if (tagged)
					//if (worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))
					teleportFromTo(xCoord, yCoord + 1, zCoord, receiverX, receiverY + 1, receiverZ);
		//else
		//teleportBlockFromTo(receiverX, receiverY + 1, receiverZ, xCoord, yCoord + 1, zCoord);
	}

	protected void teleportFromTo(int fromX, int fromY, int fromZ, int toX, int toY, int toZ) {
		if (!worldObj.isAirBlock(fromX, fromY, fromZ))
			if (worldObj.isAirBlock(toX, toY, toZ)) {
				Block teleported = Block.blocksList[worldObj.getBlockId(fromX, fromY, fromZ)];
				if (teleported.blockHardness > 0.0F)
					if (worldObj.blockHasTileEntity(fromX, fromY, fromZ)) {
						worldObj.setBlock(toX, toY, toZ, teleported.blockID, worldObj.getBlockMetadata(fromX, fromY, fromZ), 3);
						NBTTagCompound data = new NBTTagCompound();
						worldObj.getBlockTileEntity(fromX, fromY, fromZ).writeToNBT(data);
						worldObj.setBlockTileEntity(toX, toY, toZ, TileEntity.createAndLoadEntity(data));
						if (worldObj.getBlockTileEntity(fromX, fromY, fromZ) instanceof IInventory) {
							IInventory invt = (IInventory) worldObj.getBlockTileEntity(fromX, fromY, fromZ);
							for (int i = 0; i < invt.getSizeInventory(); i++)
								invt.setInventorySlotContents(i, null);
						}
						worldObj.setBlockToAir(fromX, fromY, fromZ);
						worldObj.notifyBlockOfNeighborChange(fromX, fromY - 1, fromZ, -1);
						worldObj.notifyBlockOfNeighborChange(toX, toY, toZ, teleported.blockID);
					} else {
						worldObj.setBlock(toX, toY, toZ, teleported.blockID, worldObj.getBlockMetadata(fromX, fromY, fromZ), 3);
						worldObj.setBlockToAir(fromX, fromY, fromZ);
						worldObj.notifyBlocksOfNeighborChange(toX, toY, toZ, teleported.blockID);
						worldObj.notifyBlocksOfNeighborChange(fromX, fromY, fromZ, teleported.blockID);
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
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		receiverX = nbt.getInteger("receiverX");
		receiverY = nbt.getInteger("receiverY");
		receiverZ = nbt.getInteger("receiverZ");
		receiverDim = nbt.getInteger("receiverDim");
		tagged = nbt.getBoolean("tagged");
	}
}