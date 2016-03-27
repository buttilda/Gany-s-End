package ganymedes01.ganysend.tileentities;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EntitySelectors;
import net.minecraftforge.items.IItemHandler;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class TileEntityCreativeSpeedyHopper extends TileEntitySpeedyHopper {

	@Override
	protected boolean suckItemFromInventory() {
		IItemHandler inventoryToPull = getInventoryToExtract();

		if (inventoryToPull == null)
			return false;

		for (int i = 0; i < inventoryToPull.getSlots(); i++) {
			ItemStack stack = inventoryToPull.getStackInSlot(i);
			if (stack != null)
				if (inventoryToPull.extractItem(i, stack.stackSize, false) != null)
					return true;
		}

		return false;
	}

	@Override
	protected boolean suckEntitiesAbove() {
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		List<EntityItem> list = worldObj.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(x - 0.5, y - 0.5, z - 0.5, x + 0.5, y + 0.5, z + 0.5), EntitySelectors.selectAnything);
		if (!list.isEmpty()) {
			Iterator<EntityItem> iterator = list.iterator();
			while (iterator.hasNext()) {
				EntityItem entity = iterator.next();
				if (entity.worldObj == worldObj)
					if (shouldTransfer(entity.getEntityItem())) {
						entity.setDead();
						return true;
					}
			}
		}
		return false;
	}

	@Override
	protected boolean insertItemToInventory() {
		IItemHandler inventoryToInsert = getInventoryToInsert();
		if (inventoryToInsert == null)
			return false;

		for (int i = 0; i < itemHandler.getSlots(); i++) {
			ItemStack stack = itemHandler.getStackInSlot(i);
			if (stack != null && shouldTransfer(stack))
				for (int j = 0; j < inventoryToInsert.getSlots(); j++)
					if (inventoryToInsert.insertItem(j, stack.copy(), false) == null)
						return true;
		}

		return true;
	}
}