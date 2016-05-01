package ganymedes01.ganysend.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ITickable;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class TileEntityAnchoredEnderChest extends TileEntityInventoryBinder implements ITickable {

	public int playersUsing;
	public float prevLidAngle, lidAngle;

	@Override
	public IInventory getPlayerInventory() {
		if (profile == null)
			return null;
		EntityPlayer player = worldObj.getPlayerEntityByName(profile.getName());
		return player != null ? player.getInventoryEnderChest() : null;
	}

	@Override
	public int getSizeInventory() {
		IInventory inventory = getPlayerInventory();
		return inventory != null ? inventory.getSizeInventory() : 0;
	}

	@Override
	public void update() {
		prevLidAngle = lidAngle;

		if (playersUsing > 0 && lidAngle == 0.0F)
			worldObj.playSoundEffect(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, "random.chestopen", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);

		if (playersUsing == 0 && lidAngle > 0.0F || playersUsing > 0 && lidAngle < 1.0F) {
			float oldAngle = lidAngle;

			if (playersUsing > 0)
				lidAngle += 0.1F;
			else
				lidAngle -= 0.1F;

			if (lidAngle > 1.0F)
				lidAngle = 1.0F;

			if (lidAngle < 0.5F && oldAngle >= 0.5F)
				worldObj.playSoundEffect(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, "random.chestclosed", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);

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
	public void openInventory(EntityPlayer player) {
		if (playersUsing < 0)
			playersUsing = 0;

		playersUsing++;
		worldObj.addBlockEvent(pos, getBlockType(), 0, playersUsing);
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		playersUsing--;
		worldObj.addBlockEvent(pos, getBlockType(), 0, playersUsing);
	}

	@Override
	protected void updateProfile() {
	}

	@Override
	public boolean canRenderBreaking() {
		return true;
	}
}