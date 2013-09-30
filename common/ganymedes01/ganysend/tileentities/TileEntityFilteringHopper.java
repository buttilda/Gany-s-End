package ganymedes01.ganysend.tileentities;

import ganymedes01.ganysend.blocks.BasicFilteringHopper;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;

import java.util.List;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

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

	@Override
	public void updateEntity() {
		if (worldObj.isRemote)
			return;

		transferCooldown--;
		if (transferCooldown <= 0)
			if (BasicFilteringHopper.getIsBlockNotPoweredFromMetadata(getBlockMetadata())) {
				boolean suckedItems = suckItemsIntoHopper();
				boolean indertedItems = insertItemToInventory();
				if (suckedItems || indertedItems)
					onInventoryChanged();
				transferCooldown = MAX_COOL_DOWN;
			}
	}

	protected boolean shouldPull(ItemStack stack) {
		if (getStackInSlot(FILER_SLOT) == null)
			return false;
		return isExclusive() ^ areItemStacksEqualItem(stack, getStackInSlot(FILER_SLOT));
	}

	private boolean insertItemToInventory() {
		IInventory iinventory = getOutputInventory();

		if (iinventory == null)
			return false;
		else {
			for (int i = 0; i < getSizeInventory(); i++) {
				ItemStack stack = getStackInSlot(i);
				if (stack != null && shouldPull(stack)) {
					ItemStack itemstack1 = insertStack(iinventory, decrStackSize(i, 1), Facing.oppositeSide[BasicFilteringHopper.getDirectionFromMetadata(getBlockMetadata())]);

					if (itemstack1 == null || itemstack1.stackSize == 0) {
						iinventory.onInventoryChanged();
						return true;
					}
					setInventorySlotContents(i, stack.copy());
				}
			}
			return false;
		}
	}

	private boolean suckItemsIntoHopper() {
		IInventory iinventory = getInventoryAtLocation(worldObj, xCoord, yCoord + 1, zCoord);

		if (iinventory != null) {
			if (iinventory instanceof ISidedInventory) {
				ISidedInventory isidedinventory = (ISidedInventory) iinventory;
				int[] slots = isidedinventory.getAccessibleSlotsFromSide(0);

				for (int element : slots)
					if (func_102012_a(iinventory, element, 0))
						return true;
			} else
				for (int i = 0; i < iinventory.getSizeInventory(); i++)
					if (func_102012_a(iinventory, i, 0))
						return true;
		} else {
			EntityItem entityitem = getEntityAt(worldObj, xCoord, yCoord + 1, zCoord);

			if (entityitem != null)
				if (shouldPull(entityitem.getEntityItem()))
					return suckEntityItem(entityitem);
		}

		return false;
	}

	private boolean suckEntityItem(EntityItem entityItem) {
		boolean flag = false;

		if (entityItem == null)
			return false;
		else {
			ItemStack itemstack = entityItem.getEntityItem().copy();
			ItemStack itemstack1 = insertStack(this, itemstack, 0);

			if (itemstack1 != null && itemstack1.stackSize != 0)
				entityItem.setEntityItemStack(itemstack1);
			else {
				flag = true;
				entityItem.setDead();
			}
			return flag;
		}
	}

	private boolean func_102012_a(IInventory inventory, int slot, int side) {
		ItemStack itemstack = inventory.getStackInSlot(slot);

		if (itemstack != null && canExtractItemFromInventory(inventory, itemstack, slot, side)) {
			if (!shouldPull(itemstack))
				return false;

			ItemStack itemstack1 = itemstack.copy();
			ItemStack itemstack2 = insertStack(this, inventory.decrStackSize(slot, 1), 0);

			if (itemstack2 == null || itemstack2.stackSize == 0) {
				inventory.onInventoryChanged();
				return true;
			}
			inventory.setInventorySlotContents(slot, itemstack1);
		}

		return false;
	}

	private ItemStack insertStack(IInventory inventory, ItemStack stack, int side) {
		if (inventory instanceof ISidedInventory) {
			int[] slots = ((ISidedInventory) inventory).getAccessibleSlotsFromSide(side);
			for (int j = 0; j < slots.length && stack != null && stack.stackSize > 0; j++)
				stack = func_102014_c(inventory, stack, slots[j], side);
		} else {
			int k = inventory.getSizeInventory();

			for (int l = 0; l < k && stack != null && stack.stackSize > 0; l++)
				stack = func_102014_c(inventory, stack, l, side);
		}

		if (stack != null && stack.stackSize == 0)
			stack = null;

		return stack;
	}

	private boolean canInsertItemToInventory(IInventory inventory, ItemStack stack, int slot, int side) {
		return !inventory.isItemValidForSlot(slot, stack) ? false : !(inventory instanceof ISidedInventory) || ((ISidedInventory) inventory).canInsertItem(slot, stack, side);
	}

	private boolean canExtractItemFromInventory(IInventory inventory, ItemStack stack, int slot, int side) {
		return !(inventory instanceof ISidedInventory) || ((ISidedInventory) inventory).canExtractItem(slot, stack, side);
	}

	private ItemStack func_102014_c(IInventory inventory, ItemStack stack, int slot, int side) {
		ItemStack itemstack1 = inventory.getStackInSlot(slot);

		if (canInsertItemToInventory(inventory, stack, slot, side)) {
			boolean flag = false;

			if (itemstack1 == null) {
				inventory.setInventorySlotContents(slot, stack);
				stack = null;
				flag = true;
			} else if (areItemStacksEqualItem(itemstack1, stack)) {
				int k = stack.getMaxStackSize() - itemstack1.stackSize;
				int l = Math.min(stack.stackSize, k);
				stack.stackSize -= l;
				itemstack1.stackSize += l;
				flag = l > 0;
			}

			if (flag) {
				if (inventory instanceof TileEntityFilteringHopper) {
					((TileEntityFilteringHopper) inventory).transferCooldown = ((TileEntityFilteringHopper) inventory).getMaxCoolDown();
					inventory.onInventoryChanged();
				}

				inventory.onInventoryChanged();
			}
		}

		return stack;
	}

	private IInventory getOutputInventory() {
		int dir = BasicFilteringHopper.getDirectionFromMetadata(getBlockMetadata());
		return getInventoryAtLocation(worldObj, xCoord + Facing.offsetsXForSide[dir], yCoord + Facing.offsetsYForSide[dir], zCoord + Facing.offsetsZForSide[dir]);
	}

	private EntityItem getEntityAt(World world, int x, int y, int z) {
		List list = world.selectEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getAABBPool().getAABB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D), IEntitySelector.selectAnything);
		return list.size() > 0 ? (EntityItem) list.get(0) : null;
	}

	private IInventory getInventoryAtLocation(World world, int x, int y, int z) {
		IInventory iinventory = null;
		TileEntity tileentity = world.getBlockTileEntity(x, y, z);

		if (tileentity != null && tileentity instanceof IInventory)
			iinventory = (IInventory) tileentity;

		return iinventory;
	}

	protected boolean areItemStacksEqualItem(ItemStack stack1, ItemStack stack2) {
		return stack1.itemID != stack2.itemID ? false : stack1.getItemDamage() != stack2.getItemDamage() ? false : stack1.stackSize > stack1.getMaxStackSize() ? false : ItemStack.areItemStackTagsEqual(stack1, stack2);
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
