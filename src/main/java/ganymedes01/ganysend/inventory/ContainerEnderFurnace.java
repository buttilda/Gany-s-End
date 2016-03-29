package ganymedes01.ganysend.inventory;

import ganymedes01.ganysend.recipes.EnderFurnaceFuelsRegistry;
import ganymedes01.ganysend.tileentities.TileEntityEnderFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class ContainerEnderFurnace extends Container {

	private final TileEntityEnderFurnace furnace;

	public ContainerEnderFurnace(InventoryPlayer inventory, TileEntityEnderFurnace tile) {
		furnace = tile;

		IItemHandler outputSlots = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
		IItemHandler inputSlots = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
		IItemHandler fuelSlots = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.WEST);
		addSlotToContainer(new SlotItemHandler(fuelSlots, 0, 13, 44));

		addSlotToContainer(new SlotItemHandler(inputSlots, 0, 51, 26));
		addSlotToContainer(new SlotItemHandler(inputSlots, 1, 69, 26));
		addSlotToContainer(new SlotItemHandler(inputSlots, 2, 51, 44));
		addSlotToContainer(new SlotItemHandler(inputSlots, 3, 69, 44));

		addSlotToContainer(new SlotItemHandler(outputSlots, 0, 128, 34));

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 9; j++)
				addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));

		for (int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 142));
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (int i = 0; i < crafters.size(); i++)
			furnace.sendGUIData(this, crafters.get(i));
	}

	@Override
	public void updateProgressBar(int id, int value) {
		furnace.getGUIData(id, value);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		ItemStack itemstack = null;
		Slot slot = inventorySlots.get(slotIndex);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (slotIndex <= 5) {
				if (!mergeItemStack(itemstack1, 6, 42, true))
					return null;
			} else if (EnderFurnaceFuelsRegistry.INSTANCE.getBurnTime(itemstack1) > 0) {
				if (!mergeItemStack(itemstack1, 0, 1, false))
					return null;
			} else if (!mergeItemStack(itemstack1, 1, 5, false))
				return null;

			if (itemstack1.stackSize == 0)
				slot.putStack((ItemStack) null);
			else
				slot.onSlotChanged();

			if (itemstack1.stackSize == itemstack.stackSize)
				return null;

			slot.onPickupFromSlot(player, itemstack1);
		}

		return itemstack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		World worldObj = furnace.getWorld();
		BlockPos pos = furnace.getPos();
		return worldObj.getTileEntity(pos) != furnace ? false : player.getDistanceSq(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 64;
	}
}