package ganymedes01.ganysend.inventory.slots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class FilterSlot extends SlotItemHandler {

	public FilterSlot(IItemHandler itemHandler, int slot, int posX, int posY) {
		super(itemHandler, slot, posX, posY);
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}

	@Override
	public boolean canTakeStack(EntityPlayer player) {
		return false;
	}
}