package ganymedes01.ganysend.recipes;

import ganymedes01.ganysend.blocks.ModBlocks;
import net.minecraft.item.ItemStack;
import buildcraft.api.transport.FacadeManager;

public class BuildCraftFacadeManager {

	public static void init() {
		FacadeManager.addFacade(new ItemStack(ModBlocks.enderpearlBlock));
		FacadeManager.addFacade(new ItemStack(ModBlocks.enderpearlBrick));
		FacadeManager.addFacade(new ItemStack(ModBlocks.endstoneBrick));
		FacadeManager.addFacade(new ItemStack(ModBlocks.endiumBlock));
		FacadeManager.addFacade(new ItemStack(ModBlocks.rawEndium));
	}
}
