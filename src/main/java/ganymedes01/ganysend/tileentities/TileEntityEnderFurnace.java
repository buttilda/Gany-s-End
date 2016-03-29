package ganymedes01.ganysend.tileentities;

import ganymedes01.ganysend.blocks.EnderFurnace;
import ganymedes01.ganysend.core.utils.InventoryUtils;
import ganymedes01.ganysend.recipes.EnderFurnaceFuelsRegistry;
import ganymedes01.ganysend.recipes.EnderFurnaceRegistry;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class TileEntityEnderFurnace extends TileEntity implements ITickable {

	private final IItemHandler fuelSlots = new ItemStackHandler(1) {
		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
			return EnderFurnaceFuelsRegistry.INSTANCE.getBurnTime(stack) > 0 ? super.insertItem(slot, stack, simulate) : stack;
		}

		@Override
		public ItemStack extractItem(int slot, int amount, boolean simulate) {
			return null;
		}
	};
	private final IItemHandler inputSlots = new ItemStackHandler(4) {
		@Override
		public ItemStack extractItem(int slot, int amount, boolean simulate) {
			return null;
		}
	};
	private final IItemHandler outputSlots = new ItemStackHandler(1) {
		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
			return stack;
		}
	};

	private int burnTime, currentBurnTime, cookTime;
	private boolean update = true, canSmelt;

	@Override
	public void update() {
		if (worldObj.isRemote)
			return;

		boolean inventoryChanged = false;
		if (burnTime > 0)
			burnTime--;
		if (canSmelt() && burnTime <= 0) {
			currentBurnTime = burnTime = EnderFurnaceFuelsRegistry.INSTANCE.getBurnTime(fuelSlots.getStackInSlot(0));

			if (burnTime > 0) {
				fuelSlots.extractItem(0, 1, false);
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

		if (inventoryChanged)
			markDirty();
	}

	private void smelt() {
		outputSlots.insertItem(0, EnderFurnaceRegistry.INSTANCE.getOuput(getRecipeInput()), false);

		for (int i = 0; i < 4; i++)
			outputSlots.extractItem(i, 1, false);

		markDirty();
	}

	private boolean canSmelt() {
		if (update) {
			ItemStack result = EnderFurnaceRegistry.INSTANCE.getOuput(getRecipeInput());

			if (result == null)
				canSmelt = false;
			else
				canSmelt = outputSlots.insertItem(0, result, true) == null;

			update = false;
		}
		return canSmelt;
	}

	private ItemStack[] getRecipeInput() {
		return new ItemStack[] { inputSlots.getStackInSlot(0), inputSlots.getStackInSlot(1), inputSlots.getStackInSlot(2), inputSlots.getStackInSlot(3) };
	}

	@Override
	public void markDirty() {
		super.markDirty();
		update = true;

		IBlockState state = worldObj.getBlockState(pos);
		worldObj.setBlockState(pos, state.withProperty(EnderFurnace.IS_ON, burnTime > 0));
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

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		burnTime = nbt.getInteger("burnTime");
		currentBurnTime = nbt.getInteger("currentBurnTime");
		cookTime = nbt.getInteger("cookTime");
		update = true;

		InventoryUtils.readItemHandlerFromNBT(nbt, fuelSlots, "HandlerFuel");
		InventoryUtils.readItemHandlerFromNBT(nbt, outputSlots, "HandlerOutput");
		InventoryUtils.readItemHandlerFromNBT(nbt, inputSlots, "HandlerInput");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("burnTime", burnTime);
		nbt.setInteger("currentBurnTime", currentBurnTime);
		nbt.setInteger("cookTime", cookTime);

		InventoryUtils.writeItemHandlerToNBT(nbt, fuelSlots, "HandlerFuel");
		InventoryUtils.writeItemHandlerToNBT(nbt, outputSlots, "HandlerOutput");
		InventoryUtils.writeItemHandlerToNBT(nbt, inputSlots, "HandlerInput");
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			if (facing == EnumFacing.DOWN)
				return (T) outputSlots;
			else if (facing == EnumFacing.UP)
				return (T) inputSlots;
			else
				return (T) fuelSlots;
		return super.getCapability(capability, facing);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
	}
}