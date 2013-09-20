package ganymedes01.ganysend.inventory;

import ganymedes01.ganysend.tileentities.TileEntityEnderFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class ContainerEnderFurnace extends Container {

	TileEntityEnderFurnace furnace;

	public ContainerEnderFurnace(InventoryPlayer inventory, TileEntityEnderFurnace tile) {
		furnace = tile;
		addSlotToContainer(new Slot(tile, 0, 56, 17));
		addSlotToContainer(new Slot(tile, 1, 56, 53));
		addSlotToContainer(new SlotFurnace(inventory.player, tile, 2, 116, 35));

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 9; ++j)
				addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));

		for (int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 142));
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (int i = 0; i < crafters.size(); i++)
			furnace.sendGUIData(this, (ICrafting) crafters.get(i));
	}

	@Override
	public void updateProgressBar(int i, int j) {
		furnace.getGUIData(i, j);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		ItemStack itemStack = null;
		Slot slot = (Slot) inventorySlots.get(slotIndex);

		if (slot != null && slot.getHasStack()) {
			ItemStack slotItemStack = slot.getStack();
			itemStack = slotItemStack.copy();

			if (slotIndex < 3) {
				if (!mergeItemStack(slotItemStack, 3, inventorySlots.size(), true))
					return null;
			} else if (TileEntityEnderFurnace.isItemFuel(slotItemStack)) {
				if (!mergeItemStack(slotItemStack, 1, 2, false))
					return null;
			} else if (TileEntityEnderFurnace.getSmeltingResult(slotItemStack) != null) {
				if (!mergeItemStack(slotItemStack, 0, 1, false))
					return null;
			} else
				return null;

			if (slotItemStack.stackSize == 0)
				slot.putStack((ItemStack) null);
			else
				slot.onSlotChanged();
		}

		return itemStack;
	}
}
