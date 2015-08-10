package ganymedes01.ganysend.integration;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.ModBlocks;
import ganymedes01.ganysend.integration.nei.EnderFurnaceRecipeHandler;
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
		if (GanysEnd.enableEnderFurnace) {
			API.registerRecipeHandler(new EnderFurnaceRecipeHandler());
			API.registerUsageHandler(new EnderFurnaceRecipeHandler());
		}

		if (GanysEnd.enableEnderToggler)
			API.hideItem(new ItemStack(ModBlocks.enderToggler_air));
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