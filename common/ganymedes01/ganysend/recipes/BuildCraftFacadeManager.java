package ganymedes01.ganysend.recipes;

import ganymedes01.ganysend.blocks.ModBlocks;
import cpw.mods.fml.common.event.FMLInterModComms;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class BuildCraftFacadeManager {

	public static void registerFacades() {
		addFacade(ModBlocks.enderpearlBlock.blockID);
		addFacade(ModBlocks.enderpearlBlock.blockID, 1);
		addFacade(ModBlocks.endstoneBrick.blockID);
		addFacade(ModBlocks.endiumBlock.blockID);
		addFacade(ModBlocks.rawEndium.blockID);
	}

	private static void addFacade(int blockID) {
		addFacade(blockID, 0);
	}

	private static void addFacade(int blockID, int meta) {
		FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", String.format("%d@%d", blockID, meta));
	}
}
