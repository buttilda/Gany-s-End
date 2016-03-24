package ganymedes01.ganysend.tileentities;

import ganymedes01.ganysend.core.utils.Utils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public abstract class GanysInventory extends TileEntityLockable implements IInventory {

	protected ItemStack[] inventory;
	private final String invName;

	public GanysInventory(int invSize, String invName) {
		this.invName = invName;
		inventory = new ItemStack[invSize];
	}

	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		try {
			return inventory[slot];
		} finally {
			markDirty();
		}
	}

	@Override
	public ItemStack decrStackSize(int slot, int size) {
		try {
			if (inventory[slot] != null) {
				ItemStack itemstack;
				if (inventory[slot].stackSize <= size) {
					itemstack = inventory[slot];
					inventory[slot] = null;
					return itemstack;
				} else {
					itemstack = inventory[slot].splitStack(size);
					if (inventory[slot].stackSize == 0)
						inventory[slot] = null;
					return itemstack;
				}
			} else
				return null;
		} finally {
			markDirty();
		}
	}

	@Override
	public ItemStack removeStackFromSlot(int slot) {
		try {
			if (inventory[slot] != null) {
				ItemStack itemstack = inventory[slot];
				inventory[slot] = null;
				return itemstack;
			} else
				return null;
		} finally {
			markDirty();
		}
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		inventory[slot] = stack;

		if (stack != null && stack.stackSize > getInventoryStackLimit())
			stack.stackSize = getInventoryStackLimit();

		markDirty();
	}

	@Override
	public String getName() {
		return Utils.getConainerName(invName);
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public IChatComponent getDisplayName() {
		return new ChatComponentTranslation(getName());
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getTileEntity(pos) == this;
	}

	@Override
	public void openInventory(EntityPlayer player) {
	}

	@Override
	public void closeInventory(EntityPlayer player) {
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return true;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList tags = nbt.getTagList("Items", 10);
		inventory = new ItemStack[getSizeInventory()];

		for (int i = 0; i < tags.tagCount(); i++) {
			NBTTagCompound data = tags.getCompoundTagAt(i);
			int j = data.getByte("Slot") & 255;

			if (j >= 0 && j < inventory.length)
				inventory[j] = ItemStack.loadItemStackFromNBT(data);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		NBTTagList tags = new NBTTagList();

		for (int i = 0; i < inventory.length; i++)
			if (inventory[i] != null) {
				NBTTagCompound data = new NBTTagCompound();
				data.setByte("Slot", (byte) i);
				inventory[i].writeToNBT(data);
				tags.appendTag(data);
			}

		nbt.setTag("Items", tags);
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		inventory = new ItemStack[inventory.length];
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer player) {
		return null;
	}

	@Override
	public String getGuiID() {
		return null;
	}
}