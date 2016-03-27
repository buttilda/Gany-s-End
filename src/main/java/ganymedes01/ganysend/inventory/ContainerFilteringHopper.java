package ganymedes01.ganysend.inventory;

import ganymedes01.ganysend.inventory.slots.FilterSlot;
import ganymedes01.ganysend.tileentities.TileEntityFilteringHopper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
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

public class ContainerFilteringHopper extends Container {

	private final TileEntityFilteringHopper hopper;

	public ContainerFilteringHopper(InventoryPlayer inventory, TileEntityFilteringHopper tile) {
		hopper = tile;

		byte b0 = 44;
		byte b1 = 26;
		if (tile.isFilter())
			b0 = 26 + 18;
		else
			b1 = 20;

		IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		IItemHandler filters = tile.getFilters();

		for (int i = 0; i < itemHandler.getSlots(); i++)
			addSlotToContainer(new SlotItemHandler(itemHandler, i, b0 + i * 18, b1));
		if (tile.isFilter())
			addSlotToContainer(new FilterSlot(filters, 0, 80, 53));

		byte b2 = 84;
		if (!tile.isFilter())
			b2 = 51;
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 9; j++)
				addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, i * 18 + b2));
		for (int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 58 + b2));
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		ItemStack itemstack = null;
		Slot slot = inventorySlots.get(slotIndex);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			IItemHandler itemHandler = hopper.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
			IItemHandler filters = hopper.getFilters();

			if (slotIndex < itemHandler.getSlots()) {
				if (!mergeItemStack(itemstack1, itemHandler.getSlots() + filters.getSlots(), inventorySlots.size(), true))
					return null;
			} else if (slotIndex < itemHandler.getSlots() + filters.getSlots()) {
				if (!mergeItemStack(itemstack1, itemHandler.getSlots() + filters.getSlots(), inventorySlots.size(), true))
					return null;
			} else if (!mergeItemStack(itemstack1, 0, itemHandler.getSlots(), false))
				return null;

			if (itemstack1.stackSize == 0)
				slot.putStack((ItemStack) null);
			else
				slot.onSlotChanged();
		}

		return itemstack;
	}

	@Override
	public ItemStack slotClick(int slotIndex, int button, int shift, EntityPlayer player) {
		if (slotIndex >= 0) {
			Slot slot = getSlot(slotIndex);
			if (slot instanceof FilterSlot) {
				if (slot.getHasStack())
					slot.putStack(null);
				else if (player.inventory.getItemStack() != null) {
					ItemStack copy = player.inventory.getItemStack().copy();
					copy.stackSize = 1;
					slot.putStack(copy);
				}
				return null;
			}
		}
		return super.slotClick(slotIndex, button, shift, player);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		World worldObj = hopper.getWorld();
		BlockPos pos = hopper.getPos();
		return worldObj.getTileEntity(pos) != hopper ? false : player.getDistanceSq(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 64;
	}
}