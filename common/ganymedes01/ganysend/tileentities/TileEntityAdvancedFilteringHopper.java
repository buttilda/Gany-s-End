package ganymedes01.ganysend.tileentities;

import ganymedes01.ganysend.blocks.BasicFilteringHopper;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class TileEntityAdvancedFilteringHopper extends TileEntity implements IInventory {

	/**
	 * Code copied from vanilla hopper and tweaked
	 * 
	 */

	private ItemStack[] inventory = new ItemStack[5];
	private ItemStack[] filter = new ItemStack[5];
	private int transferCooldown = -1;
	public final static int FILER_SLOT = 5;
	private int MAX_COOL_DOWN;
	private boolean EXCLUSIVE;
	private String line;
	private String name;

	public void setBasic() {
		MAX_COOL_DOWN = 2;
		EXCLUSIVE = false;
		line = StatCollector.translateToLocal("pullonly");
		name = Utils.getConainerName(Strings.ADVANCED_FILTERING_HOPPER_NAME);
	}

	public void setExclusive() {
		MAX_COOL_DOWN = 2;
		EXCLUSIVE = true;
		line = StatCollector.translateToLocal("pullallbut");
		name = Utils.getConainerName(Strings.ADVANCED_EXCLUSIVE_FILTERING_HOPPER_NAME);
	}

	public String getLine() {
		return line;
	}

	public boolean isExclusive() {
		return EXCLUSIVE;
	}

	public int getMaxCoolDown() {
		return MAX_COOL_DOWN;
	}

	public int getFilterInventorySize() {
		return filter.length;
	}

	@Override
	public String getInvName() {
		return name;
	}

	@Override
	public void updateEntity() {
		if (worldObj != null && !worldObj.isRemote) {
			--transferCooldown;

			if (!(transferCooldown > 0)) {
				transferCooldown = 0;
				hop();
			}
		}
	}

	protected boolean shouldPull(ItemStack stack) {
		for (int i = FILER_SLOT; i < FILER_SLOT + filter.length; i++) {
			if (getStackInSlot(i) == null)
				continue;
			if (areItemStacksEqualItem(stack, getStackInSlot(i)))
				return !isExclusive();
		}
		return isExclusive();
	}

	public boolean hop() {
		if (worldObj != null && !worldObj.isRemote) {
			if (!(transferCooldown > 0) && BasicFilteringHopper.getIsBlockNotPoweredFromMetadata(getBlockMetadata())) {
				boolean flag = insertItemToInventory();
				flag = TileEntityAdvancedFilteringHopper.suckItemsIntoHopper(this) || flag;
				if (flag) {
					transferCooldown = MAX_COOL_DOWN;
					onInventoryChanged();
					return true;
				}
			}
			return false;
		} else
			return false;
	}

	private boolean insertItemToInventory() {
		IInventory iinventory = getOutputInventory();

		if (iinventory == null)
			return false;
		else {
			for (int i = 0; i < getSizeInventory(); ++i)
				if (getStackInSlot(i) != null) {
					ItemStack itemstack = getStackInSlot(i).copy();
					ItemStack itemstack1 = insertStack(iinventory, decrStackSize(i, 1), Facing.oppositeSide[BasicFilteringHopper.getDirectionFromMetadata(getBlockMetadata())]);

					if (itemstack1 == null || itemstack1.stackSize == 0) {
						iinventory.onInventoryChanged();
						return true;
					}

					setInventorySlotContents(i, itemstack);
				}

			return false;
		}
	}

	public static boolean suckItemsIntoHopper(TileEntityAdvancedFilteringHopper hopper) {
		IInventory iinventory = getInventoryAboveHopper(hopper);

		if (iinventory != null) {
			byte b0 = 0;

			if (iinventory instanceof ISidedInventory && b0 > -1) {
				ISidedInventory isidedinventory = (ISidedInventory) iinventory;
				int[] aint = isidedinventory.getAccessibleSlotsFromSide(b0);

				for (int element : aint)
					if (func_102012_a(hopper, iinventory, element, b0))
						return true;
			} else {
				int j = iinventory.getSizeInventory();

				for (int k = 0; k < j; ++k)
					if (func_102012_a(hopper, iinventory, k, b0))
						return true;
			}
		} else {
			EntityItem entityitem = func_96119_a(hopper.getWorldObj(), hopper.xCoord, hopper.yCoord + 1.0D, hopper.zCoord);

			if (entityitem != null)
				if (hopper.shouldPull(entityitem.getEntityItem()))
					return func_96114_a(hopper, entityitem);
		}

		return false;
	}

	private static boolean func_102012_a(TileEntityAdvancedFilteringHopper hopper, IInventory inventory, int slot, int side) {
		ItemStack itemstack = inventory.getStackInSlot(slot);

		if (itemstack != null && canExtractItemFromInventory(inventory, itemstack, slot, side)) {

			if (!hopper.shouldPull(itemstack))
				return false;

			ItemStack itemstack1 = itemstack.copy();
			ItemStack itemstack2 = insertStack(hopper, inventory.decrStackSize(slot, 1), -1);

			if (itemstack2 == null || itemstack2.stackSize == 0) {
				inventory.onInventoryChanged();
				return true;
			}

			inventory.setInventorySlotContents(slot, itemstack1);
		}

		return false;
	}

	public static boolean func_96114_a(IInventory inventory, EntityItem entityItem) {
		boolean flag = false;

		if (entityItem == null)
			return false;
		else {
			ItemStack itemstack = entityItem.getEntityItem().copy();
			ItemStack itemstack1 = insertStack(inventory, itemstack, -1);

			if (itemstack1 != null && itemstack1.stackSize != 0)
				entityItem.setEntityItemStack(itemstack1);
			else {
				flag = true;
				entityItem.setDead();
			}

			return flag;
		}
	}

	public static ItemStack insertStack(IInventory inventory, ItemStack stack, int side) {
		if (inventory instanceof ISidedInventory && side > -1) {
			ISidedInventory isidedinventory = (ISidedInventory) inventory;
			int[] aint = isidedinventory.getAccessibleSlotsFromSide(side);

			for (int j = 0; j < aint.length && stack != null && stack.stackSize > 0; ++j)
				stack = func_102014_c(inventory, stack, aint[j], side);
		} else {
			int k = inventory.getSizeInventory();

			for (int l = 0; l < k && stack != null && stack.stackSize > 0; ++l)
				stack = func_102014_c(inventory, stack, l, side);
		}

		if (stack != null && stack.stackSize == 0)
			stack = null;

		return stack;
	}

	private static boolean canInsertItemToInventory(IInventory inventory, ItemStack stack, int slot, int side) {
		return !inventory.isItemValidForSlot(slot, stack) ? false : !(inventory instanceof ISidedInventory) || ((ISidedInventory) inventory).canInsertItem(slot, stack, side);
	}

	private static boolean canExtractItemFromInventory(IInventory inventory, ItemStack stack, int slot, int side) {
		return !(inventory instanceof ISidedInventory) || ((ISidedInventory) inventory).canExtractItem(slot, stack, side);
	}

	private static ItemStack func_102014_c(IInventory inventory, ItemStack stack, int par2, int par3) {
		ItemStack itemstack1 = inventory.getStackInSlot(par2);

		if (canInsertItemToInventory(inventory, stack, par2, par3)) {
			boolean flag = false;

			if (itemstack1 == null) {
				inventory.setInventorySlotContents(par2, stack);
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
				if (inventory instanceof TileEntityAdvancedFilteringHopper) {
					((TileEntityAdvancedFilteringHopper) inventory).transferCooldown = ((TileEntityAdvancedFilteringHopper) inventory).getMaxCoolDown();
					inventory.onInventoryChanged();
				}

				inventory.onInventoryChanged();
			}
		}

		return stack;
	}

	private IInventory getOutputInventory() {
		int i = BasicFilteringHopper.getDirectionFromMetadata(getBlockMetadata());
		return getInventoryAtLocation(getWorldObj(), xCoord + Facing.offsetsXForSide[i], yCoord + Facing.offsetsYForSide[i], zCoord + Facing.offsetsZForSide[i]);
	}

	public static IInventory getInventoryAboveHopper(TileEntityAdvancedFilteringHopper hopper) {
		return getInventoryAtLocation(hopper.getWorldObj(), hopper.xCoord, hopper.yCoord + 1.0D, hopper.zCoord);
	}

	public static EntityItem func_96119_a(World world, double x, double y, double z) {
		List list = world.selectEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getAABBPool().getAABB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D), IEntitySelector.selectAnything);
		return list.size() > 0 ? (EntityItem) list.get(0) : null;
	}

	public static IInventory getInventoryAtLocation(World world, double x, double y, double z) {
		IInventory iinventory = null;
		int i = MathHelper.floor_double(x);
		int j = MathHelper.floor_double(y);
		int k = MathHelper.floor_double(z);
		TileEntity tileentity = world.getBlockTileEntity(i, j, k);

		if (tileentity != null && tileentity instanceof IInventory) {
			iinventory = (IInventory) tileentity;

			if (iinventory instanceof TileEntityChest) {
				int l = world.getBlockId(i, j, k);
				Block block = Block.blocksList[l];

				if (block instanceof BlockChest)
					iinventory = ((BlockChest) block).getInventory(world, i, j, k);
			}
		}

		if (iinventory == null) {
			List list = world.getEntitiesWithinAABBExcludingEntity((Entity) null, AxisAlignedBB.getAABBPool().getAABB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D), IEntitySelector.selectInventories);

			if (list != null && list.size() > 0)
				iinventory = (IInventory) list.get(world.rand.nextInt(list.size()));
		}

		return iinventory;
	}

	protected static boolean areItemStacksEqualItem(ItemStack stack1, ItemStack stack2) {
		return stack1.itemID != stack2.itemID ? false : stack1.getItemDamage() != stack2.getItemDamage() ? false : stack1.stackSize > stack1.getMaxStackSize() ? false : ItemStack.areItemStackTagsEqual(stack1, stack2);
	}

	@Override
	public int getSizeInventory() {
		return inventory.length;
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
		inventory = new ItemStack[5];
		filter = new ItemStack[5];
		transferCooldown = data.getInteger("TransferCooldown");
		MAX_COOL_DOWN = data.getInteger("MAX_COOL_DOWN");
		EXCLUSIVE = data.getBoolean("OPPOSITE");
		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound = (NBTTagCompound) nbttaglist.tagAt(i);
			byte b0 = nbttagcompound.getByte("Slot");

			if (b0 >= 0 && b0 < inventory.length + filter.length)
				setInventorySlotContents(i, ItemStack.loadItemStackFromNBT(nbttagcompound));
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound data) {
		super.writeToNBT(data);
		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < inventory.length + filter.length; ++i) {
			NBTTagCompound nbttagcompound = new NBTTagCompound();
			if (getStackInSlot(i) != null) {
				nbttagcompound.setByte("Slot", (byte) i);
				getStackInSlot(i).writeToNBT(nbttagcompound);
			}
			nbttaglist.appendTag(nbttagcompound);
		}
		data.setTag("Items", nbttaglist);
		data.setInteger("TransferCooldown", transferCooldown);
		data.setInteger("MAX_COOL_DOWN", MAX_COOL_DOWN);
		data.setBoolean("OPPOSITE", EXCLUSIVE);
	}
}
