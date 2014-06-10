package ganymedes01.ganysend.core.handlers;

import ganymedes01.ganysend.blocks.ModBlocks;
import ganymedes01.ganysend.core.utils.InventoryUtils;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.IEndiumTool;
import ganymedes01.ganysend.lib.Reference;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class HandlerEvents {

	@SubscribeEvent
	public void tooltip(ItemTooltipEvent event) {
		Block block = Block.getBlockFromItem(event.itemStack.getItem());
		if (block == null)
			return;
		if (block == ModBlocks.creativeInfiniteFluidSource) {
			event.toolTip.add(StatCollector.translateToLocal("string." + Reference.MOD_ID + ".creativeOnly"));
			event.toolTip.add(StatCollector.translateToLocal("string." + Reference.MOD_ID + ".rightClickContainer"));
		}
	}

	@SubscribeEvent
	public void endBonemealEvent(BonemealEvent event) {
		if (event.world.provider.dimensionId == 1 && event.block == Blocks.end_stone)
			if (event.world.isAirBlock(event.x, event.y + 1, event.z)) {
				event.world.setBlock(event.x, event.y + 1, event.z, ModBlocks.enderFlower);
				event.setResult(Result.ALLOW);
			}
	}

	@SubscribeEvent
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
									IInventory tile = Utils.getTileEntity(event.world, x, y, z, IInventory.class);
									if (tile != null) {
										event.dropChance = -1.0F;
										for (ItemStack stack : event.drops)
											if (!InventoryUtils.addStackToInventory(tile, stack))
												InventoryUtils.dropStack(event.world, event.x, event.y, event.z, stack);
									}
								}
	}
}