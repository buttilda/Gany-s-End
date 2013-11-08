package ganymedes01.ganysend.tileentities;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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

	public int receiverX;
	public int receiverY;
	public int receiverZ;
	public int receiverDim;
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
			if (tagged) {
				int telX, telY, telZ, telDim, recX, recY, recZ, recDim;
				if (worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) {
					telX = xCoord;
					telY = yCoord + 1;
					telZ = zCoord;
					telDim = worldObj.provider.dimensionId;

					recX = receiverX;
					recY = receiverY + 1;
					recZ = receiverZ;
					recDim = receiverDim;
				} else {
					telX = receiverX;
					telY = receiverY + 1;
					telZ = receiverZ;
					telDim = receiverDim;

					recX = xCoord;
					recY = yCoord + 1;
					recZ = zCoord;
					recDim = worldObj.provider.dimensionId;
				}
				if (recDim != telDim)
					return;
				if (!worldObj.isAirBlock(telX, telY, telZ)) {
					Block teleported = Block.blocksList[worldObj.getBlockId(telX, telY, telZ)];
					if (worldObj.isAirBlock(recX, recY, recZ) && !(teleported.getBlockHardness(worldObj, telX, telY, telZ) <= 0.0F) && teleported.blockMaterial != Material.water && teleported.blockMaterial != Material.lava)
						if (worldObj.blockHasTileEntity(telX, telY, telZ)) {
							worldObj.setBlock(recX, recY, recZ, teleported.blockID, worldObj.getBlockMetadata(telX, telY, telZ), 3);
							NBTTagCompound data = new NBTTagCompound();
							worldObj.getBlockTileEntity(telX, telY, telZ).writeToNBT(data);
							worldObj.setBlockTileEntity(recX, recY, recZ, TileEntity.createAndLoadEntity(data));
							if (worldObj.getBlockTileEntity(telX, telY, telZ) instanceof IInventory) {
								IInventory invt = (IInventory) worldObj.getBlockTileEntity(telX, telY, telZ);
								for (int i = 0; i < invt.getSizeInventory(); i++)
									invt.setInventorySlotContents(i, null);
							}
							worldObj.setBlockToAir(telX, telY, telZ);
							worldObj.notifyBlockOfNeighborChange(telX, telY - 1, telZ, -1);
							worldObj.notifyBlockOfNeighborChange(recX, recY, recZ, teleported.blockID);
						} else {
							worldObj.setBlock(recX, recY, recZ, teleported.blockID, worldObj.getBlockMetadata(telX, telY, telZ), 3);
							worldObj.setBlockToAir(telX, telY, telZ);
							worldObj.notifyBlockOfNeighborChange(telX, telY - 1, telZ, -1);
							worldObj.notifyBlockOfNeighborChange(recX, recY, recZ, teleported.blockID);
						}
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
