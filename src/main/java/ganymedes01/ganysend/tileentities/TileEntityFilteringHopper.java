package ganymedes01.ganysend.tileentities;

import java.util.Iterator;
import java.util.List;

import ganymedes01.ganysend.core.utils.InventoryUtils;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Reference;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.IHopper;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ITickable;
import net.minecraft.util.StatCollector;
import net.minecraftforge.items.VanillaInventoryCodeHooks;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class TileEntityFilteringHopper extends TileEntityLockable implements IInventory, IHopper, ITickable {

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
		line = StatCollector.translateToLocal("string." + Reference.MOD_ID + ".pullonly");
		name = Utils.getConainerName(Strings.BASIC_FILTERING_HOPPER_NAME);
	}

	public void setExclusive() {
		MAX_COOL_DOWN = 8;
		EXCLUSIVE = true;
		line = StatCollector.translateToLocal("string." + Reference.MOD_ID + ".pullallbut");
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

	protected boolean shouldPull(ItemStack stack) {
		if (getStackInSlot(FILER_SLOT) == null)
			return false;
		return isExclusive() ^ InventoryUtils.areStacksTheSame(stack, getStackInSlot(FILER_SLOT), false);
	}

	@Override
	public void update() {
		if (worldObj.isRemote)
			return;

		transferCooldown--;
		if (transferCooldown <= 0) {
			boolean suckedItems = suckItemsIntoHopper();
			boolean indertedItems = insertItemToInventory();
			if (suckedItems || indertedItems)
				markDirty();
			transferCooldown = MAX_COOL_DOWN;
		}
	}

	protected boolean insertItemToInventory() {
		if (VanillaInventoryCodeHooks.insertHook(this, getSideToInsert()))
			return true;

		IInventory inventoryToInsert = getInventoryToInsert();
		if (inventoryToInsert == null)
			return false;

		for (int i = 0; i < inventory.length; i++)
			if (inventory[i] == null)
				continue;
			else if (shouldPull(inventory[i])) {
				ItemStack copy = inventory[i].copy();
				copy.stackSize = 1;
				if (InventoryUtils.addStackToInventory(inventoryToInsert, copy, getSideToInsert())) {
					if (--inventory[i].stackSize <= 0)
						inventory[i] = null;
					return true;
				}
			}
		return false;
	}

	private boolean suckItemsIntoHopper() {
		return suckEntitiesAbove() || suckItemFromInventory();
	}

	protected boolean suckItemFromInventory() {
		if (VanillaInventoryCodeHooks.extractHook(this))
			return true;

		IInventory inventoryToPull = getInventoryAbove();

		if (inventoryToPull == null)
			return false;

		for (int slot : InventoryUtils.getSlotsFromSide(inventoryToPull, EnumFacing.DOWN)) {
			ItemStack stack = inventoryToPull.getStackInSlot(slot);
			if (stack == null)
				continue;
			if (inventoryToPull instanceof ISidedInventory && !((ISidedInventory) inventoryToPull).canExtractItem(slot, stack, EnumFacing.DOWN))
				continue;
			if (shouldPull(stack)) {
				ItemStack copy = stack.copy();
				copy.stackSize = 1;
				if (InventoryUtils.addStackToInventory(this, copy)) {
					stack.stackSize--;
					if (stack.stackSize <= 0)
						inventoryToPull.setInventorySlotContents(slot, null);
					return true;
				}
			}
		}

		return false;
	}

	protected boolean suckEntitiesAbove() {
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		List<EntityItem> list = worldObj.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(x - 0.5, y - 0.5, z - 0.5, x + 0.5, y + 0.5, z + 0.5), EntitySelectors.selectAnything);
		if (!list.isEmpty()) {
			Iterator<EntityItem> iterator = list.iterator();
			while (iterator.hasNext()) {
				EntityItem entity = iterator.next();
				if (entity.worldObj == worldObj)
					if (shouldPull(entity.getEntityItem()))
						return InventoryUtils.addEntitytoInventory(this, entity);
			}
		}
		return false;
	}

	protected final IInventory getInventoryAbove() {
		IInventory iinventory = Utils.getTileEntity(worldObj, pos.up(), IInventory.class);
		if (iinventory == null) {
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			List<Entity> list = worldObj.getEntitiesInAABBexcluding(null, new AxisAlignedBB(x - 0.5, y - 0.5, z - 0.5, x + 0.5, y + 0.5, z + 0.5), EntitySelectors.selectInventories);
			if (list != null && list.size() > 0)
				iinventory = (IInventory) list.get(worldObj.rand.nextInt(list.size()));
		}

		return iinventory;
	}

	protected IInventory getInventoryToInsert() {
		BlockPos sidePos = pos.offset(getSideToInsert());
		return Utils.getTileEntity(worldObj, sidePos, IInventory.class);
	}

	public EnumFacing getSideToInsert() {
		return EnumFacing.VALUES[getBlockMetadata()];
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
	public ItemStack removeStackFromSlot(int slot) {
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
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return true;
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		super.readFromNBT(data);
		NBTTagList tags = data.getTagList("Items", 10);
		inventory = new ItemStack[getSizeInventory()];
		for (int i = 0; i < tags.tagCount(); i++) {
			NBTTagCompound nbt = tags.getCompoundTagAt(i);
			int j = nbt.getByte("Slot") & 255;
			if (j >= 0 && j < inventory.length)
				inventory[j] = ItemStack.loadItemStackFromNBT(nbt);
		}

		NBTTagCompound nbttagcompound = data.getCompoundTag("filter");
		filter = ItemStack.loadItemStackFromNBT(nbttagcompound);

		transferCooldown = data.getInteger("TransferCooldown");
		MAX_COOL_DOWN = data.getInteger("MAX_COOL_DOWN");
		EXCLUSIVE = data.getBoolean("OPPOSITE");
	}

	@Override
	public void writeToNBT(NBTTagCompound data) {
		super.writeToNBT(data);
		NBTTagList tags = new NBTTagList();
		for (int i = 0; i < inventory.length; i++)
			if (inventory[i] != null) {
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setByte("Slot", (byte) i);
				inventory[i].writeToNBT(nbt);
				tags.appendTag(nbt);
			}

		data.setTag("Items", tags);

		if (filter != null) {
			NBTTagCompound nbttagcompound = new NBTTagCompound();
			filter.writeToNBT(nbttagcompound);
			data.setTag("filter", nbttagcompound);
		}

		data.setInteger("TransferCooldown", transferCooldown);
		data.setInteger("MAX_COOL_DOWN", MAX_COOL_DOWN);
		data.setBoolean("OPPOSITE", EXCLUSIVE);
	}

	@Override
	public String getName() {
		return name;
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
	public void openInventory(EntityPlayer player) {
	}

	@Override
	public void closeInventory(EntityPlayer player) {
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
		filter = null;
	}

	@Override
	public double getXPos() {
		return pos.getX();
	}

	@Override
	public double getYPos() {
		return pos.getY();
	}

	@Override
	public double getZPos() {
		return pos.getZ();
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return null;
	}

	@Override
	public String getGuiID() {
		return null;
	}
}