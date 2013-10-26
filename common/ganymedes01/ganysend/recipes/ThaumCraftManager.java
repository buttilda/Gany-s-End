package ganymedes01.ganysend.recipes;

import ganymedes01.ganysend.items.ModItems;
import net.minecraft.item.ItemStack;
import thaumcraft.api.ThaumcraftApi;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class ThaumCraftManager {

	public static void init() {
		ThaumcraftApi.addSmeltingBonus(new ItemStack(ModItems.endiumIngot), new ItemStack(ModItems.endiumNugget));
	}
}