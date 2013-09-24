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
		registerBlock(ModBlocks.enderpearlBlock.blockID);
		registerBlock(ModBlocks.enderpearlBlock.blockID, 1);
		registerBlock(ModBlocks.endstoneBrick.blockID);
		registerBlock(ModBlocks.endiumBlock.blockID);
		registerBlock(ModBlocks.rawEndium.blockID);
	}

	private static void registerBlock(int blockID) {
		registerBlock(blockID, 0);
	}

	private static void registerBlock(int blockID, int meta) {
		FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", String.format("%d@%d", blockID, meta));
	}
}
