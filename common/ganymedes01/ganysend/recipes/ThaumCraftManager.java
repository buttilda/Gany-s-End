package ganymedes01.ganysend.recipes;

import ganymedes01.ganysend.items.ModItems;
import net.minecraft.item.ItemStack;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class ThaumCraftManager {

	public static void init() {
		ThaumcraftApi.addSmeltingBonus(new ItemStack(ModItems.endiumIngot), new ItemStack(ModItems.endiumNugget));

		addAspect(ModItems.endiumIngot.itemID, new int[] { 1 }, new Aspect[] { Aspect.AIR });
	}

	private static void addAspect(int id, int[] amounts, Aspect[] aspects) {
		addAspect(id, -1, amounts, aspects);
	}

	private static void addAspect(int id, int meta, int[] amounts, Aspect[] aspects) {
		AspectList list = new AspectList();
		for (int i = 0; i < aspects.length; i++)
			list.add(aspects[i], amounts[i]);

		if (ThaumcraftApi.exists(id, meta))
			ThaumcraftApi.registerObjectTag(id, meta, list);
	}
}