package ganymedes01.ganysend.tileentities;

import ganymedes01.ganysend.core.utils.InventoryUtils;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.recipes.EnderFurnaceFuelsRegistry;
import ganymedes01.ganysend.recipes.EnderFurnaceRegistry;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class TileEntityEnderFurnace extends GanysInventory implements ISidedInventory, ITickable {

	private int burnTime, currentBurnTime, cookTime;
	private boolean update, canSmelt;
	public int lightLevel = 0;

	public TileEntityEnderFurnace() {
		super(6, Strings.ENDER_FURNACE_NAME);
		update = true;
	}

	@Override
	public boolean receiveClientEvent(int eventId, int eventData) {
		switch (eventId) {
			case 1:
				lightLevel = eventData;
				worldObj.func_147451_t(xCoord, yCoord, zCoord);
				return true;
			default:
				return false;
		}
	}

	@Override
	public void update() {
		if (worldObj.isRemote)
			return;

		boolean inventoryChanged = false;
		if (burnTime > 0)
			burnTime--;
		if (canSmelt() && burnTime <= 0) {
			currentBurnTime = burnTime = EnderFurnaceFuelsRegistry.INSTANCE.getBurnTime(inventory[0]);

			if (burnTime > 0)
				if (inventory[0] != null) {
					if (--inventory[0].stackSize <= 0)
						inventory[0] = inventory[0].getItem().getContainerItem(inventory[0]);
					inventoryChanged = true;
				}
		}

		if (burnTime > 0 && canSmelt()) {
			if (++cookTime >= 200) {
				cookTime = 0;
				smelt();
				inventoryChanged = true;
			}
		} else
			cookTime = 0;

		sendClientUpdates();

		if (inventoryChanged)
			markDirty();
	}

	private void sendClientUpdates() {
		int old = lightLevel;
		lightLevel = burnTime > 0 ? 15 : 0;

		if (lightLevel != old)
			worldObj.addBlockEvent(xCoord, yCoord, zCoord, getBlockType(), 1, lightLevel);
	}

	private void smelt() {
		if (inventory[5] == null)
			inventory[5] = EnderFurnaceRegistry.INSTANCE.getOuput(getRecipeInput());
		else
			inventory[5].stackSize += EnderFurnaceRegistry.INSTANCE.getOuput(getRecipeInput()).stackSize;
		for (int i = 1; i <= 4; i++)
			if (inventory[i] != null && --inventory[i].stackSize <= 0)
				inventory[i] = null;

		markDirty();
	}

	private boolean canSmelt() {
		if (update) {
			ItemStack result = EnderFurnaceRegistry.INSTANCE.getOuput(getRecipeInput());

			if (result == null)
				canSmelt = false;
			else if (inventory[5] == null)
				canSmelt = true;
			else if (InventoryUtils.areStacksTheSame(inventory[5], result, false))
				canSmelt = inventory[5].getMaxStackSize() >= inventory[5].stackSize + result.stackSize;
			else
				canSmelt = false;

			update = false;
		}
		return canSmelt;
	}

	private ItemStack[] getRecipeInput() {
		return new ItemStack[] { inventory[1], inventory[2], inventory[3], inventory[4] };
	}

	@Override
	public void markDirty() {
		super.markDirty();
		update = true;
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, getBlockType(), 1, lightLevel);
		worldObj.func_147479_m(xCoord, yCoord, zCoord);
	}

	public void sendGUIData(Container container, ICrafting craft) {
		craft.sendProgressBarUpdate(container, 0, burnTime);
		craft.sendProgressBarUpdate(container, 1, currentBurnTime);
		craft.sendProgressBarUpdate(container, 2, cookTime);
	}

	public void getGUIData(int id, int value) {
		switch (id) {
			case 0:
				burnTime = value;
				break;
			case 1:
				currentBurnTime = value;
				break;
			case 2:
				cookTime = value;
				break;
		}
	}

	public int getBurnTimeScaled(int scale) {
		if (currentBurnTime == 0)
			return 0;
		return (int) (scale * ((float) burnTime / currentBurnTime));
	}

	public int getCookTimeScaled(int scale) {
		return (int) (scale * ((float) cookTime / 200));
	}

	public boolean isFuel(ItemStack stack) {
		return EnderFurnaceFuelsRegistry.INSTANCE.getBurnTime(stack) > 0;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return side == 1 ? new int[] { 1, 2, 3, 4 } : side == 0 ? new int[] { 5 } : new int[] { 0 };
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return slot == 5 ? false : slot == 0 ? isFuel(stack) : true;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return isItemValidForSlot(slot, stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return slot == 5;
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		super.readFromNBT(data);
		burnTime = data.getInteger("burnTime");
		currentBurnTime = data.getInteger("currentBurnTime");
		cookTime = data.getInteger("cookTime");
		update = true;
	}

	@Override
	public void writeToNBT(NBTTagCompound data) {
		super.writeToNBT(data);
		data.setInteger("burnTime", burnTime);
		data.setInteger("currentBurnTime", currentBurnTime);
		data.setInteger("cookTime", cookTime);
	}
}