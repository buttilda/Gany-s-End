package ganymedes01.ganysend.tileentities;

import ganymedes01.ganysend.core.utils.InventoryUtils;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class TileEntityAdvancedFilteringHopper extends TileEntityFilteringHopper {

	private ItemStack[] filter = new ItemStack[5];

	@Override
	public void setBasic() {
		super.setBasic();
		MAX_COOL_DOWN = 2;
		name = Utils.getConainerName(Strings.ADVANCED_FILTERING_HOPPER_NAME);
	}

	@Override
	public void setExclusive() {
		super.setExclusive();
		MAX_COOL_DOWN = 2;
		name = Utils.getConainerName(Strings.ADVANCED_EXCLUSIVE_FILTERING_HOPPER_NAME);
	}

	public int getFilterInventorySize() {
		return filter.length;
	}

	@Override
	protected boolean shouldPull(ItemStack stack) {
		for (int i = FILER_SLOT; i < FILER_SLOT + filter.length; i++) {
			if (getStackInSlot(i) == null)
				continue;
			if (InventoryUtils.areStacksTheSame(stack, getStackInSlot(i), false))
				return !isExclusive();
		}
		return isExclusive();
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		if (slot >= FILER_SLOT)
			return filter[slot - FILER_SLOT];
		return inventory[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int size) {
		if (slot >= FILER_SLOT)
			if (filter[slot - FILER_SLOT] != null) {
				ItemStack stack;
				if (filter[slot - FILER_SLOT].stackSize <= size) {
					stack = filter[slot - FILER_SLOT];
					filter[slot - FILER_SLOT] = null;
					return stack;
				} else {
					stack = filter[slot - FILER_SLOT].splitStack(size);
					if (filter[slot - FILER_SLOT].stackSize == 0)
						filter[slot - FILER_SLOT] = null;
					return stack;
				}
			} else
				return null;
		else
			return super.decrStackSize(slot, size);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if (slot >= FILER_SLOT)
			return filter[slot - FILER_SLOT];
		if (inventory[slot] != null) {
			ItemStack itemstack = inventory[slot];
			inventory[slot] = null;
			return itemstack;
		} else
			return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		if (slot >= FILER_SLOT) {
			filter[slot - FILER_SLOT] = stack;
			if (stack != null && stack.stackSize > getInventoryStackLimit())
				stack.stackSize = getInventoryStackLimit();
			return;
		}
		inventory[slot] = stack;

		if (stack != null && stack.stackSize > getInventoryStackLimit())
			stack.stackSize = getInventoryStackLimit();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList tags = nbt.getTagList("filters", 10);
		filter = new ItemStack[5];

		for (int i = 0; i < tags.tagCount(); i++) {
			NBTTagCompound data = tags.getCompoundTagAt(i);
			int j = data.getByte("Slot") & 255;
			if (j >= 0 && j < filter.length)
				filter[j] = ItemStack.loadItemStackFromNBT(data);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		NBTTagList tags = new NBTTagList();

		for (int i = 0; i < filter.length; i++)
			if (filter[i] != null) {
				NBTTagCompound data = new NBTTagCompound();
				data.setByte("Slot", (byte) i);
				filter[i].writeToNBT(data);
				tags.appendTag(data);
			}

		nbt.setTag("filters", tags);
	}
}