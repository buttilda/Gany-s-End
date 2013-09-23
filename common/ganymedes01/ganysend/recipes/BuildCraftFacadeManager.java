package ganymedes01.ganysend.recipes;

import ganymedes01.ganysend.blocks.ModBlocks;
import net.minecraft.item.ItemStack;
import buildcraft.api.transport.FacadeManager;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class BuildCraftFacadeManager {

	public static void init() {
		FacadeManager.addFacade(new ItemStack(ModBlocks.enderpearlBlock, 1, 0));
		FacadeManager.addFacade(new ItemStack(ModBlocks.enderpearlBlock, 1, 1));
		FacadeManager.addFacade(new ItemStack(ModBlocks.endstoneBrick));
		FacadeManager.addFacade(new ItemStack(ModBlocks.endiumBlock));
		FacadeManager.addFacade(new ItemStack(ModBlocks.rawEndium));
	}
}
