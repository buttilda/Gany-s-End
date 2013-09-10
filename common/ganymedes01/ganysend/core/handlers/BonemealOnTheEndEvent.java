package ganymedes01.ganysend.core.handlers;

import ganymedes01.ganysend.blocks.ModBlocks;
import net.minecraft.block.Block;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.BonemealEvent;

public class BonemealOnTheEndEvent {

	@ForgeSubscribe
	public void endBonemealEvent(BonemealEvent event) {
		if (event.world.provider.dimensionId == 1 && event.world.getBlockId(event.X, event.Y, event.Z) == Block.whiteStone.blockID)
			if (event.world.isAirBlock(event.X, event.Y + 1, event.Z)) {
				event.world.setBlock(event.X, event.Y + 1, event.Z, ModBlocks.enderFlower.blockID);
				event.setResult(Result.ALLOW);
			}
	}
}
