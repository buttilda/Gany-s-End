package ganymedes01.ganysend.inventory;

import ganymedes01.ganysend.tileentities.TileEntityVoidCrate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class ContainerVoidCrate extends GanysContainer {

	public ContainerVoidCrate(InventoryPlayer inventory, TileEntityVoidCrate tile) {
		super(tile);
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				addSlotToContainer(new Slot(tile, j + i * 8, 8 + j * 18, i * 18 + 11));

		addSlotToContainer(new Slot(tile, 64, 152, 137));

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 9; j++)
				addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, i * 18 + 163));
		for (int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 221));
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(slotIndex);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (slotIndex < 65) {
				if (!mergeItemStack(itemstack1, 65, 100, false))
					return null;
			} else if (!mergeItemStack(itemstack1, 0, 65, false))
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
}