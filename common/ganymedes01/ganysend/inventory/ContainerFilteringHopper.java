package ganymedes01.ganysend.inventory;

import ganymedes01.ganysend.tileentities.TileEntityFilteringHopper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerFilteringHopper extends Container {

	TileEntityFilteringHopper hopper;

	public ContainerFilteringHopper(InventoryPlayer inventory, TileEntityFilteringHopper tile) {
		hopper = tile;

		byte b0 = 44;
		if (tile.isFilter())
			b0 = 26;

		for (int i = 0; i < tile.getSizeInventory(); i++)
			addSlotToContainer(new Slot(tile, i, b0 + i * 18, 20));

		if (tile.isFilter())
			addSlotToContainer(new Slot(tile, tile.FILER_SLOT, 152, 20));

		b0 = 51;
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 9; j++)
				addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, i * 18 + b0));

		for (int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 58 + b0));
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(slotIndex);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (slotIndex < hopper.getSizeInventory()) {
				if (!mergeItemStack(itemstack1, hopper.getSizeInventory(), inventorySlots.size(), true))
					return null;
			} else if (slotIndex < hopper.getSizeInventory() + 1) {
				if (!mergeItemStack(itemstack1, hopper.getSizeInventory() + 1, inventorySlots.size(), true))
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
}
