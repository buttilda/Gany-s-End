package ganymedes01.ganysend.core.handlers;

import ganymedes01.ganysend.blocks.ModBlocks;
import net.minecraft.init.Blocks;
import net.minecraftforge.event.entity.player.BonemealEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class BonemealOnTheEndEvent {

	@SubscribeEvent
	public void endBonemealEvent(BonemealEvent event) {
		if (event.world.provider.dimensionId == 1 && event.block == Blocks.end_stone)
			if (event.world.isAirBlock(event.x, event.y + 1, event.z)) {
				event.world.setBlock(event.x, event.y + 1, event.z, ModBlocks.enderFlower);
				event.setResult(Result.ALLOW);
			}
	}
}