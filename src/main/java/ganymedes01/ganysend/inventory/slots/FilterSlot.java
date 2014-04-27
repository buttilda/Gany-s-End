package ganymedes01.ganysend.inventory.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class FilterSlot extends Slot {

	public FilterSlot(IInventory inventory, int slot, int posX, int posY) {
		super(inventory, slot, posX, posY);
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}
}
