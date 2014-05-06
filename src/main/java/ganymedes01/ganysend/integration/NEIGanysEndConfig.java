package ganymedes01.ganysend.integration;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.blocks.ModBlocks;
import ganymedes01.ganysend.integration.nei.EnderFurnaceRecipeHandler;
import ganymedes01.ganysend.items.ModItems;
import ganymedes01.ganysend.lib.Reference;
import net.minecraft.item.ItemStack;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class NEIGanysEndConfig implements IConfigureNEI {

	@Override
	public void loadConfig() {
		API.registerRecipeHandler(new EnderFurnaceRecipeHandler());
		API.registerUsageHandler(new EnderFurnaceRecipeHandler());

		API.hideItem(new ItemStack(ModBlocks.enderToggler_air));
		API.hideItem(new ItemStack(ModBlocks.blockNewSkull));
		if (!GanysEnd.enableEnderBag)
			API.hideItem(new ItemStack(ModItems.enderBag));
		if (!GanysEnd.enableTimeManipulator)
			API.hideItem(new ItemStack(ModBlocks.timeManipulator));
		if (!GanysEnd.activateShifters) {
			API.hideItem(new ItemStack(ModBlocks.blockShifter));
			API.hideItem(new ItemStack(ModBlocks.entityShifter));
		}
	}

	@Override
	public String getName() {
		return Reference.MOD_NAME;
	}

	@Override
	public String getVersion() {
		return Reference.VERSION_NUMBER;
	}
}