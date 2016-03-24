package ganymedes01.ganysend.tileentities;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ITickable;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class TileEntityBlockShifter extends TileEntity implements ITickable {

	public BlockPos receiver;
	public int receiverDim;
	public boolean tagged;
	public boolean direction;

	public TileEntityBlockShifter() {
		receiver = null;
		receiverDim = 0;
		tagged = false;
	}

	@Override
	public void update() {
		if (worldObj.isRemote)
			return;

		if (receiverDim == worldObj.provider.getDimensionId())
			if (tagged)
				if (direction)
					teleportFromTo(pos.up(), receiver.up());
				else
					teleportFromTo(receiver.up(), pos.up());
	}

	protected void teleportFromTo(BlockPos from, BlockPos to) {
		if (!worldObj.isAirBlock(from) && worldObj.isAirBlock(to)) {
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
		nbt.setInteger("receiverX", receiver.getX());
		nbt.setInteger("receiverY", receiver.getY());
		nbt.setInteger("receiverZ", receiver.getZ());
		nbt.setInteger("receiverDim", receiverDim);
		nbt.setBoolean("tagged", tagged);
		nbt.setBoolean("direction", direction);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		int x = nbt.getInteger("receiverX");
		int y = nbt.getInteger("receiverY");
		int z = nbt.getInteger("receiverZ");
		receiver = new BlockPos(x, y, z);
		receiverDim = nbt.getInteger("receiverDim");
		tagged = nbt.getBoolean("tagged");
		direction = nbt.getBoolean("direction");
	}
}