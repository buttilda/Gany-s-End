package ganymedes01.ganysend.integration;

import ganymedes01.ganysend.blocks.ModBlocks;
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
		ThaumcraftApi.addSmeltingBonus(new ItemStack(ModItems.endiumIngot), new ItemStack(ModItems.endiumIngot, 0, 1));
		ThaumcraftApi.portableHoleBlackList.add(ModBlocks.enderToggler_air.blockID);

		addAspectsToItem(ModItems.endiumIngot.itemID, new Aspect[] { Aspect.ELDRITCH, Aspect.TRAVEL, Aspect.MAGIC, Aspect.SENSES, Aspect.DARKNESS }, new int[] { 4, 4, 7, 2, 2 });
		addAspectsToItem(ModItems.endstoneRod.itemID, new Aspect[] { Aspect.DARKNESS, Aspect.EARTH }, new int[] { 1, 1 });
		addAspectsToItem(ModItems.itemNewSkull.itemID, new Aspect[] { Aspect.FLESH, Aspect.DEATH, Aspect.SOUL }, new int[] { 4, 4, 4 });
		addAspectsToItem(ModBlocks.enderFlower.blockID, new Aspect[] { Aspect.PLANT, Aspect.SENSES, Aspect.LIFE, Aspect.ELDRITCH, Aspect.MAGIC, Aspect.TRAVEL }, new int[] { 1, 1, 1, 1, 1, 1 });
	}

	private static void addAspectsToItem(int id, Aspect[] aspects, int[] amounts) {
		addAspectsToItem(id, -1, aspects, amounts);
	}

	private static void addAspectsToItem(int id, int meta, Aspect[] aspects, int[] amounts) {
		AspectList list = new AspectList();
		for (int i = 0; i < aspects.length; i++)
			list.add(aspects[i], amounts[i]);

		ThaumcraftApi.registerObjectTag(id, meta, list);
	}
}