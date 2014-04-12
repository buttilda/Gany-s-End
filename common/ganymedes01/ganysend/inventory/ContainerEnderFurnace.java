package ganymedes01.ganysend.inventory;

import ganymedes01.ganysend.inventory.slots.BetterSlot;
import ganymedes01.ganysend.tileentities.TileEntityEnderFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

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

		addSlotToContainer(new BetterSlot(tile, 0, 13, 44));

		addSlotToContainer(new BetterSlot(tile, 1, 51, 26));
		addSlotToContainer(new BetterSlot(tile, 2, 69, 26));
		addSlotToContainer(new BetterSlot(tile, 3, 51, 44));
		addSlotToContainer(new BetterSlot(tile, 4, 69, 44));

		addSlotToContainer(new BetterSlot(tile, 5, 128, 34));

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
			furnace.sendGUIData(this, (ICrafting) crafters.get(i));
	}

	@Override
	public void updateProgressBar(int id, int value) {
		furnace.getGUIData(id, value);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		return null;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
}