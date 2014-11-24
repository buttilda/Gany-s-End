package ganymedes01.ganysend.inventory;

import ganymedes01.ganysend.inventory.slots.FilterSlot;
import ganymedes01.ganysend.tileentities.TileEntityAdvancedFilteringHopper;
import ganymedes01.ganysend.tileentities.TileEntityFilteringHopper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class ContainerAdvancedFilteringHopper extends Container {

	private final TileEntityAdvancedFilteringHopper hopper;

	public ContainerAdvancedFilteringHopper(InventoryPlayer inventory, TileEntityAdvancedFilteringHopper tile) {
		hopper = tile;

		for (int i = 0; i < tile.getSizeInventory(); i++)
			addSlotToContainer(new Slot(tile, i, 44 + i * 18, 26));
		for (int i = TileEntityFilteringHopper.FILER_SLOT; i < TileEntityFilteringHopper.FILER_SLOT + tile.getFilterInventorySize(); i++)
			addSlotToContainer(new FilterSlot(tile, i, -28 + i * 18, 53));

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 9; j++)
				addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, i * 18 + 84));
		for (int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 142));
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(slotIndex);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (slotIndex < hopper.getSizeInventory()) {
				if (!mergeItemStack(itemstack1, hopper.getSizeInventory() + hopper.getFilterInventorySize(), inventorySlots.size(), true))
					return null;
			} else if (slotIndex < hopper.getSizeInventory() + hopper.getFilterInventorySize()) {
				if (!mergeItemStack(itemstack1, hopper.getSizeInventory() + hopper.getFilterInventorySize(), inventorySlots.size(), true))
					return null;
			} else if (!mergeItemStack(itemstack1, 0, hopper.getSizeInventory(), false))
				return null;

			if (itemstack1.stackSize == 0)
				slot.putStack((ItemStack) null);
			else
				slot.onSlotChanged();
		}

		return itemstack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

	@Override
	public ItemStack slotClick(int slotIndex, int button, int shift, EntityPlayer player) {
		if (slotIndex > 0) {
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
}