package ganymedes01.ganysend.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class TileEntityAnchoredEnderChest extends TileEntityInventoryBinder {

	public int playersUsing;
	public float prevLidAngle, lidAngle;

	@Override
	protected IInventory getPlayerInventory() {
		EntityPlayer player = worldObj.getPlayerEntityByName(playerName);
		return player != null ? player.getInventoryEnderChest() : null;
	}

	@Override
	public int getSizeInventory() {
		if (isConnected())
			return getPlayerInventory().getSizeInventory();
		else
			return 0;
	}

	@Override
	public void updateEntity() {
		prevLidAngle = lidAngle;

		if (playersUsing > 0 && lidAngle == 0.0F)
			worldObj.playSoundEffect(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, "random.chestopen", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);

		if (playersUsing == 0 && lidAngle > 0.0F || playersUsing > 0 && lidAngle < 1.0F) {
			float oldAngle = lidAngle;

			if (playersUsing > 0)
				lidAngle += 0.1F;
			else
				lidAngle -= 0.1F;

			if (lidAngle > 1.0F)
				lidAngle = 1.0F;

			if (lidAngle < 0.5F && oldAngle >= 0.5F)
				worldObj.playSoundEffect(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, "random.chestclosed", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);

			if (lidAngle < 0.0F)
				lidAngle = 0.0F;
		}
	}

	@Override
	public boolean receiveClientEvent(int id, int data) {
		if (id == 0) {
			playersUsing = data;
			return true;
		}
		return false;
	}

	@Override
	public void openInventory() {
		if (playersUsing < 0)
			playersUsing = 0;

		playersUsing++;
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, getBlockType(), 0, playersUsing);
	}

	@Override
	public void closeInventory() {
		playersUsing--;
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, getBlockType(), 0, playersUsing);
	}

	@Override
	public boolean canUpdate() {
		return true;
	}
}