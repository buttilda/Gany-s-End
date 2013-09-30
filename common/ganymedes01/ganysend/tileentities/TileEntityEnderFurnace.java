package ganymedes01.ganysend.tileentities;

import ganymedes01.ganysend.blocks.EnderFurnace;
import ganymedes01.ganysend.blocks.ModBlocks;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.inventory.ContainerEnderFurnace;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class TileEntityEnderFurnace extends TileEntity implements ISidedInventory {

	private static final int[] slots_top = new int[] { 0 };
	private static final int[] slots_bottom = new int[] { 2, 1 };
	private static final int[] slots_sides = new int[] { 1 };
	private ItemStack[] inventory = new ItemStack[3];
	private final int COOK_TIME = 200;
	private int furnaceBurnTime;
	private int currentItemBurnTime;
	private int furnaceCookTime;

	// Practically stolen from the vanilla furnace code
	public void updateEntitys() {
		boolean flag = furnaceBurnTime > 0;
		boolean flag1 = false;
		if (furnaceBurnTime > 0)
			--furnaceBurnTime;
		if (!worldObj.isRemote) {
			if (furnaceBurnTime == 0 && canSmelt()) {
				currentItemBurnTime = furnaceBurnTime = getItemBurnTime(inventory[1]);
				if (furnaceBurnTime > 0) {
					flag1 = true;
					if (inventory[1] != null) {
						--inventory[1].stackSize;
						if (inventory[1].stackSize == 0)
							inventory[1] = inventory[1].getItem().getContainerItemStack(inventory[1]);
					}
				}
			}
			if (isBurning() && canSmelt()) {
				++furnaceCookTime;
				if (furnaceCookTime == COOK_TIME) {
					furnaceCookTime = 0;
					smeltItem();
					flag1 = true;
				}
			} else
				furnaceCookTime = 0;
			if (flag != furnaceBurnTime > 0) {
				flag1 = true;
				EnderFurnace.updateFurnaceBlockState(furnaceBurnTime > 0, worldObj, xCoord, yCoord, zCoord);
			}
		}
		if (flag1)
			onInventoryChanged();
	}

	@Override
	public void updateEntity() {
		boolean flag = furnaceBurnTime > 0;
		boolean flag1 = false;
		if (furnaceBurnTime > 0)
			--furnaceBurnTime;

		if (!worldObj.isRemote) {
			if (furnaceBurnTime == 0 && canSmelt()) {
				currentItemBurnTime = furnaceBurnTime = getItemBurnTime(inventory[1]);

				if (furnaceBurnTime > 0) {
					flag1 = true;

					if (inventory[1] != null) {
						--inventory[1].stackSize;

						if (inventory[1].stackSize == 0)
							inventory[1] = inventory[1].getItem().getContainerItemStack(inventory[1]);
					}
				}
			}

			if (isBurning() && canSmelt()) {
				++furnaceCookTime;

				if (furnaceCookTime == 200) {
					furnaceCookTime = 0;
					smeltItem();
					flag1 = true;
				}
			} else
				furnaceCookTime = 0;

			if (flag != furnaceBurnTime > 0) {
				flag1 = true;
				EnderFurnace.updateFurnaceBlockState(furnaceBurnTime > 0, worldObj, xCoord, yCoord, zCoord);
			}
		}

		if (flag1)
			onInventoryChanged();
	}

	// TODO add more recipes
	public static ItemStack getSmeltingResult(ItemStack stack) {
		if (stack == null)
			return null;
		else {
			int itemID = stack.itemID;
			stack.getItemDamage();
			if (itemID == Block.stone.blockID)
				return new ItemStack(Block.whiteStone);
			if (itemID == ModBlocks.rawEndium.blockID)
				return new ItemStack(ModBlocks.endiumBlock);
		}
		return null;
	}

	public static int getItemBurnTime(ItemStack stack) {
		if (stack == null)
			return 0;
		else {
			int i = stack.getItem().itemID;
			stack.getItem();

			if (stack.getItem() instanceof ItemBlock && Block.blocksList[i] != null) {
				Block block = Block.blocksList[i];

				if (block == ModBlocks.enderpearlBlock)
					return 900;
			}
			if (i == Item.enderPearl.itemID)
				return 100;
		}
		return 0;
	}

	private boolean canSmelt() {
		if (inventory[0] == null)
			return false;
		else {
			ItemStack itemstack = getSmeltingResult(inventory[0]);
			if (itemstack == null)
				return false;
			if (inventory[2] == null)
				return true;
			if (!inventory[2].isItemEqual(itemstack))
				return false;
			int result = inventory[2].stackSize + itemstack.stackSize;
			return result <= getInventoryStackLimit() && result <= itemstack.getMaxStackSize();
		}
	}

	private void smeltItem() {
		if (canSmelt()) {
			ItemStack itemstack = getSmeltingResult(inventory[0]);

			if (inventory[2] == null)
				inventory[2] = itemstack.copy();
			else if (inventory[2].isItemEqual(itemstack))
				inventory[2].stackSize += itemstack.stackSize;

			--inventory[0].stackSize;

			if (inventory[0].stackSize <= 0)
				inventory[0] = null;
		}
	}

	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return inventory[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int size) {
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
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if (inventory[slot] != null) {
			ItemStack itemstack = inventory[slot];
			inventory[slot] = null;
			return itemstack;
		} else
			return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		inventory[slot] = stack;

		if (stack != null && stack.stackSize > getInventoryStackLimit())
			stack.stackSize = getInventoryStackLimit();
	}

	@Override
	public String getInvName() {
		return Utils.getConainerName(Strings.ENDER_FURNACE_NAME);
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
		return slot == 2 ? false : slot == 1 ? isItemFuel(stack) : true;
	}

	public static boolean isItemFuel(ItemStack stack) {
		if (stack == null)
			return false;
		else
			return stack.getItem() == Item.enderPearl || stack.getItem().itemID == ModBlocks.enderpearlBlock.blockID;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return side == 0 ? slots_bottom : side == 1 ? slots_top : slots_sides;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return isItemValidForSlot(slot, stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return true;
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		super.readFromNBT(data);
		NBTTagList nbttaglist = data.getTagList("Items");
		inventory = new ItemStack[3];

		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.tagAt(i);
			byte b0 = nbttagcompound1.getByte("Slot");

			if (b0 >= 0 && b0 < inventory.length)
				inventory[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}

		currentItemBurnTime = data.getInteger("currentItemBurnTime");
		furnaceBurnTime = data.getInteger("furnaceBurnTime");
		furnaceCookTime = data.getInteger("furnaceCookTime");
	}

	@Override
	public void writeToNBT(NBTTagCompound data) {
		super.writeToNBT(data);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < inventory.length; ++i)
			if (inventory[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				inventory[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}

		data.setTag("Items", nbttaglist);

		data.setInteger("currentItemBurnTime", currentItemBurnTime);
		data.setInteger("furnaceBurnTime", furnaceBurnTime);
		data.setInteger("furnaceCookTime", furnaceCookTime);
	}

	public void getGUIData(int id, int value) {
		switch (id) {
			case 1:
				currentItemBurnTime = value;
				break;
			case 2:
				furnaceBurnTime = value;
				break;
			case 3:
				furnaceCookTime = value;
				break;
		}
	}

	public void sendGUIData(ContainerEnderFurnace furnace, ICrafting craft) {
		craft.sendProgressBarUpdate(furnace, 1, currentItemBurnTime);
		craft.sendProgressBarUpdate(furnace, 2, furnaceBurnTime);
		craft.sendProgressBarUpdate(furnace, 3, furnaceCookTime);
	}

	public boolean isBurning() {
		return furnaceBurnTime > 0;
	}

	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainingScaled(int scale) {
		if (currentItemBurnTime == 0)
			currentItemBurnTime = COOK_TIME;
		return furnaceBurnTime * scale / currentItemBurnTime;
	}

	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int scale) {
		return furnaceCookTime * scale / COOK_TIME;
	}
}
