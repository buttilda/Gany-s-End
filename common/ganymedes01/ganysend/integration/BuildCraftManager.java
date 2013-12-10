package ganymedes01.ganysend.integration;

import ganymedes01.ganysend.blocks.ModBlocks;
import cpw.mods.fml.common.event.FMLInterModComms;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class BuildCraftManager extends Integration {

	@Override
	public void init() {
		addFacade(ModBlocks.enderpearlBlock.blockID);
		addFacade(ModBlocks.enderpearlBlock.blockID, 1);
		addFacade(ModBlocks.endstoneBrick.blockID);
		addFacade(ModBlocks.endiumBlock.blockID);
		addFacade(ModBlocks.rawEndium.blockID);
	}

	@Override
	public void postInit() {
	}

	@Override
	public String getModID() {
		return "BuildCraft|Transport";
	}

	private void addFacade(int blockID) {
		addFacade(blockID, 0);
	}

	private void addFacade(int blockID, int meta) {
		FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", blockID + "@" + meta);
	}
}