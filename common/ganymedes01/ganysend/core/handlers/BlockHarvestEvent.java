package ganymedes01.ganysend.core.handlers;

import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.items.ModItems;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class BlockHarvestEvent {

	@ForgeSubscribe
	public void onBlockHarvested(HarvestDropsEvent event) {
		if (event.harvester != null)
			if (!event.drops.isEmpty()) {
				int itemID = event.harvester.getCurrentEquippedItem().itemID;
				if (itemID == ModItems.endiumPickaxe.itemID || itemID == ModItems.endiumAxe.itemID || itemID == ModItems.endiumShovel.itemID)
					if (event.harvester.inventory.getCurrentItem().getTagCompound().getBoolean("Tagged")) {
						int x = event.harvester.inventory.getCurrentItem().getTagCompound().getIntArray("Position")[0];
						int y = event.harvester.inventory.getCurrentItem().getTagCompound().getIntArray("Position")[1];
						int z = event.harvester.inventory.getCurrentItem().getTagCompound().getIntArray("Position")[2];
						int dim = event.harvester.inventory.getCurrentItem().getTagCompound().getInteger("Dimension");

						if (event.world.provider.dimensionId != dim)
							return;
						TileEntity tile = event.world.getBlockTileEntity(x, y, z);
						if (tile != null && tile instanceof IInventory) {
							event.dropChance = -1.0F;
							for (ItemStack stack : event.drops)
								if (!Utils.addStackToInventory((IInventory) tile, stack))
									Utils.dropStack(event.world, event.x, event.y, event.z, stack);
						}
					}
			}
	}
}