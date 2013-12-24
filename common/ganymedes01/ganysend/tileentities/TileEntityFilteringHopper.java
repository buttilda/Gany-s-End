package ganymedes01.ganysend.tileentities;

import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;

import java.util.Iterator;
import java.util.List;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.StatCollector;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class TileEntityFilteringHopper extends TileEntity implements IInventory {

	protected ItemStack[] inventory = new ItemStack[5];
	private ItemStack filter;
	protected int transferCooldown = -1;
	public final static int FILER_SLOT = 5;
	protected int MAX_COOL_DOWN;
	protected boolean EXCLUSIVE;
	protected String line;
	protected String name;

	public void setBasic() {
		MAX_COOL_DOWN = 8;
		EXCLUSIVE = false;
		line = StatCollector.translateToLocal("pullonly");
		name = Utils.getConainerName(Strings.BASIC_FILTERING_HOPPER_NAME);
	}

	public void setExclusive() {
		MAX_COOL_DOWN = 8;
		EXCLUSIVE = true;
		line = StatCollector.translateToLocal("pullallbut");
		name = Utils.getConainerName(Strings.EXCLUSIVE_FILTERING_HOPPER_NAME);
	}

	public void setSpeedyOnly() {
		MAX_COOL_DOWN = 1;
		EXCLUSIVE = false;
		line = "";
		name = Utils.getConainerName(Strings.SPEEDY_HOPPER_NAME);
	}

	public void setSpeedy() {
		MAX_COOL_DOWN = 2;
		if (EXCLUSIVE)
			name = Utils.getConainerName(Strings.SPEEDY_EXCLUSIVE_FILTERING_HOPPER_NAME);
		else
			name = Utils.getConainerName(Strings.SPEEDY_BASIC_FILTERING_HOPPER_NAME);
	}

	public String getLine() {
		return line;
	}

	public boolean isExclusive() {
		return EXCLUSIVE;
	}

	public boolean isFilter() {
		return true;
	}

	public int getMaxCoolDown() {
		return MAX_COOL_DOWN;
	}

	@Override
	public String getInvName() {
		return name;
	}

	protected boolean shouldPull(ItemStack stack) {
		if (getStackInSlot(FILER_SLOT) == null)
			return false;
		return isExclusive() ^ areItemStacksEqualItem(stack, getStackInSlot(FILER_SLOT));
	}

	protected boolean areItemStacksEqualItem(ItemStack stack1, ItemStack stack2) {
		return stack1.itemID != stack2.itemID ? false : stack1.getItemDamage() != stack2.getItemDamage() ? false : stack1.stackSize > stack1.getMaxStackSize() ? false : ItemStack.areItemStackTagsEqual(stack1, stack2);
	}

	@Override
	public void updateEntity() {
		if (worldObj.isRemote)
			return;

		transferCooldown--;
		if (transferCooldown <= 0) {
			boolean suckedItems = suckItemsIntoHopper();
			boolean indertedItems = insertItemToInventory();
			if (suckedItems || indertedItems)
				onInventoryChanged();
			transferCooldown = MAX_COOL_DOWN;
		}
	}

	private boolean insertItemToInventory() {
		IInventory inventoryToInsert = getInventoryToInsert();

		if (inventoryToInsert == null)
			return false;
		for (int i = 0; i < inventory.length; i++)
			if (inventory[i] == null)
				continue;
			else if (inventory[i].stackSize <= 0) {
				inventory[i] = null;
				continue;
			} else if (shouldPull(inventory[i]))
				return insertOneItemFromStack(inventoryToInsert, i);
		return false;
	}

	private boolean insertOneItemFromStack(IInventory inventoryToInsert, int stackSlot) {
		boolean flag = Utils.addStackToInventory(inventoryToInsert, inventory[stackSlot].splitStack(1), getSideOfInventory());
		try {
			return flag;
		} finally {
			if (!flag)
				inventory[stackSlot].stackSize++;
		}
	}

	private boolean suckItemsIntoHopper() {
		return suckEntitiesAbove() || suckItemFromInventory();
	}

	private boolean suckItemFromInventory() {
		IInventory inventoryToPull = getInventoryAbove();

		if (inventoryToPull == null)
			return false;
		if (inventoryToPull instanceof ISidedInventory)
			for (int slot : ((ISidedInventory) inventoryToPull).getAccessibleSlotsFromSide(0)) {
				ItemStack stack = inventoryToPull.getStackInSlot(slot);
				if (stack == null)
					continue;
				else if (stack.stackSize <= 0) {
					inventoryToPull.setInventorySlotContents(slot, null);
					continue;
				} else if (shouldPull(stack) && ((ISidedInventory) inventoryToPull).canExtractItem(slot, stack, 0)) {
					ItemStack singleItemItemStack = stack.copy();
					singleItemItemStack.stackSize = 1;
					if (Utils.addStackToInventory(this, singleItemItemStack)) {
						stack.splitStack(1);
						return true;
					}
					return false;
				}
			}
		else
			for (int i = 0; i < inventoryToPull.getSizeInventory(); i++) {
				ItemStack stack = inventoryToPull.getStackInSlot(i);
				if (stack == null)
					continue;
				else if (stack.stackSize <= 0) {
					inventoryToPull.setInventorySlotContents(i, null);
					continue;
				} else if (shouldPull(stack)) {
					ItemStack singleItemItemStack = stack.copy();
					singleItemItemStack.stackSize = 1;
					if (Utils.addStackToInventory(this, singleItemItemStack)) {
						stack.splitStack(1);
						return true;
					}
					return false;
				}
			}
		return false;
	}

	private boolean suckEntitiesAbove() {
		List list = worldObj.selectEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getAABBPool().getAABB(xCoord, yCoord + 1.0D, zCoord, xCoord + 1.0D, yCoord + 2.0D, zCoord + 1.0D), IEntitySelector.selectAnything);
		if (!list.isEmpty()) {
			Iterator iterator = list.iterator();
			while (iterator.hasNext()) {
				EntityItem entity = (EntityItem) iterator.next();
				if (entity.worldObj == worldObj)
					if (shouldPull(entity.getEntityItem()))
						return Utils.addEntitytoInventory(this, entity);
			}
		}
		return false;
	}

	private IInventory getInventoryAbove() {
		TileEntity tile = worldObj.getBlockTileEntity(xCoord, yCoord + 1, zCoord);
		return tile instanceof TileEntityChest ? getInventoryFromChest((TileEntityChest) tile) : tile instanceof IInventory ? (IInventory) tile : null;
	}

	private IInventory getInventoryToInsert() {
		int x = xCoord, y = yCoord, z = zCoord;
		switch (getBlockMetadata()) {
			case 0:
				y--;
				break;
			case 2:
				z--;
				break;
			case 3:
				z++;
				break;
			case 4:
				x--;
				break;
			case 5:
				x++;
				break;
			default:
				return null;
		}

		TileEntity tile = worldObj.getBlockTileEntity(x, y, z);
		return tile instanceof TileEntityChest ? getInventoryFromChest((TileEntityChest) tile) : tile instanceof IInventory ? (IInventory) tile : null;
	}

	private IInventory getInventoryFromChest(TileEntityChest chest) {
		TileEntityChest adjacent = null;
		if (chest.adjacentChestXNeg != null)
			adjacent = chest.adjacentChestXNeg;
		if (chest.adjacentChestXNeg != null)
			adjacent = chest.adjacentChestXNeg;
		if (chest.adjacentChestXPos != null)
			adjacent = chest.adjacentChestXPos;
		if (chest.adjacentChestZNeg != null)
			adjacent = chest.adjacentChestZNeg;
		if (chest.adjacentChestZPosition != null)
			adjacent = chest.adjacentChestZPosition;
		if (adjacent != null)
			return new InventoryLargeChest("", chest, adjacent);

		return chest;
	}

	private int getSideOfInventory() {
		return getBlockMetadata() ^ 1;
	}

	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		if (slot == FILER_SLOT)
			return filter;
		return inventory[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int size) {
		if (slot == FILER_SLOT)
			if (filter != null) {
				ItemStack stack;
				if (filter.stackSize <= size) {
					stack = filter;
					filter = null;
					return stack;
				} else {
					stack = filter.splitStack(size);
					if (filter.stackSize == 0)
						filter = null;
					return stack;
				}
			} else
				return null;

		if (inventory[slot] != null) {
			ItemStack stack;

			if (inventory[slot].stackSize <= size) {
				stack = inventory[slot];
				inventory[slot] = null;
				return stack;
			} else {
				stack = inventory[slot].splitStack(size);

				if (inventory[slot].stackSize == 0)
					inventory[slot] = null;

				return stack;
			}
		} else
			return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if (slot == FILER_SLOT)
			return filter;
		if (inventory[slot] != null) {
			ItemStack itemstack = inventory[slot];
			inventory[slot] = null;
			return itemstack;
		} else
			return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		if (slot == FILER_SLOT) {
			filter = stack;
			if (stack != null && stack.stackSize > getInventoryStackLimit())
				stack.stackSize = getInventoryStackLimit();
			return;
		}

		inventory[slot] = stack;
		if (stack != null && stack.stackSize > getInventoryStackLimit())
			stack.stackSize = getInventoryStackLimit();
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return true;
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		super.readFromNBT(data);
		NBTTagList nbttaglist = data.getTagList("Items");
		inventory = new ItemStack[getSizeInventory()];
		transferCooldown = data.getInteger("TransferCooldown");
		MAX_COOL_DOWN = data.getInteger("MAX_COOL_DOWN");
		EXCLUSIVE = data.getBoolean("OPPOSITE");
		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound = (NBTTagCompound) nbttaglist.tagAt(i);
			byte b0 = nbttagcompound.getByte("Slot");

			if (b0 >= 0 && b0 < inventory.length)
				inventory[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound);
		}
		NBTTagCompound nbttagcompound = data.getCompoundTag("filter");
		filter = ItemStack.loadItemStackFromNBT(nbttagcompound);
	}

	@Override
	public void writeToNBT(NBTTagCompound data) {
		super.writeToNBT(data);
		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < inventory.length; ++i)
			if (inventory[i] != null) {
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte) i);
				inventory[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		if (filter != null) {
			NBTTagCompound nbttagcompound = new NBTTagCompound();
			filter.writeToNBT(nbttagcompound);
			data.setCompoundTag("filter", nbttagcompound);
		}
		data.setTag("Items", nbttaglist);
		data.setInteger("TransferCooldown", transferCooldown);
		data.setInteger("MAX_COOL_DOWN", MAX_COOL_DOWN);
		data.setBoolean("OPPOSITE", EXCLUSIVE);
	}
}
