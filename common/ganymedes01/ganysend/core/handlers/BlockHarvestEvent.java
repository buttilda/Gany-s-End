package ganymedes01.ganysend.core.handlers;

import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.IEndiumTool;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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
			if (event.dropChance > 0.0F)
				if (!event.drops.isEmpty())
					if (event.harvester.getCurrentEquippedItem() != null)
						if (event.harvester.getCurrentEquippedItem().getItem() instanceof IEndiumTool)
							if (event.harvester.inventory.getCurrentItem().stackTagCompound != null)
								if (event.harvester.inventory.getCurrentItem().getTagCompound().getBoolean("Tagged")) {
									NBTTagCompound data = event.harvester.inventory.getCurrentItem().getTagCompound();
									int x = data.getIntArray("Position")[0];
									int y = data.getIntArray("Position")[1];
									int z = data.getIntArray("Position")[2];
									int dim = data.getInteger("Dimension");

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