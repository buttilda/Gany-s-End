package ganymedes01.ganysend.tileentities;

import ganymedes01.ganysend.core.utils.InventoryUtils;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;

import java.util.Iterator;
import java.util.List;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class TileEntityCreativeSpeedyHopper extends TileEntitySpeedyHopper {

	@Override
	public String getInventoryName() {
		return Utils.getConainerName(Strings.CREATIVE_SPEEDY_HOPPER_NAME);
	}

	@Override
	protected boolean suckItemFromInventory() {
		IInventory inventoryToPull = getInventoryAbove();

		if (inventoryToPull == null)
			return false;

		for (int slot : InventoryUtils.getSlotsFromSide(inventoryToPull, 0)) {
			ItemStack stack = inventoryToPull.getStackInSlot(slot);
			if (stack != null && shouldPull(stack))
				return InventoryUtils.extractFromInventory(inventoryToPull, -1, 0) != null;
		}

		return false;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected boolean suckEntitiesAbove() {
		List<EntityItem> list = worldObj.selectEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getAABBPool().getAABB(xCoord, yCoord + 1.0D, zCoord, xCoord + 1.0D, yCoord + 2.0D, zCoord + 1.0D), IEntitySelector.selectAnything);
		if (!list.isEmpty()) {
			Iterator<EntityItem> iterator = list.iterator();
			while (iterator.hasNext()) {
				EntityItem entity = iterator.next();
				if (entity.worldObj == worldObj)
					if (shouldPull(entity.getEntityItem())) {
						entity.setDead();
						return true;
					}
			}
		}
		return false;
	}

	@Override
	protected boolean insertItemToInventory() {
		IInventory inventoryToInsert = getInventoryToInsert();
		if (inventoryToInsert == null)
			return false;

		for (int i = 0; i < inventory.length; i++)
			if (inventory[i] == null)
				continue;
			else if (shouldPull(inventory[i]))
				InventoryUtils.addStackToInventory(inventoryToInsert, inventory[i].copy(), getSideToInsert());

		return true;
	}
}