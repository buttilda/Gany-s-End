package ganymedes01.ganysend.tileentities;

import net.minecraft.block.Block;
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
				if (teleported.getBlockHardness(worldObj, fromX, fromY, fromZ) > 0.0F) {
					TileEntity fromTile = worldObj.getTileEntity(fromX, fromY, fromZ);
					if (fromTile != null) {
						int meta = worldObj.getBlockMetadata(fromX, fromY, fromZ);
						worldObj.setBlock(toX, toY, toZ, teleported);
						worldObj.setBlockMetadataWithNotify(toX, toY, toZ, meta, 3);
						NBTTagCompound data = new NBTTagCompound();
						fromTile.writeToNBT(data);
						TileEntity toTile = TileEntity.createAndLoadEntity(data);
						toTile.xCoord = toX;
						toTile.yCoord = toY;
						toTile.zCoord = toZ;
						worldObj.setTileEntity(toX, toY, toZ, toTile);
						fromTile.invalidate();
						worldObj.setBlockToAir(fromX, fromY, fromZ);
					} else {
						int meta = worldObj.getBlockMetadata(fromX, fromY, fromZ);
						worldObj.setBlock(toX, toY, toZ, teleported);
						worldObj.setBlockMetadataWithNotify(toX, toY, toZ, meta, 3);
						worldObj.setBlockToAir(fromX, fromY, fromZ);
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