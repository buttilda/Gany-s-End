package ganymedes01.ganysend.integration;

import ganymedes01.ganysend.blocks.ModBlocks;
import ganymedes01.ganysend.enchantment.ModEnchants;
import ganymedes01.ganysend.items.ModItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import thaumcraft.api.ItemApi;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.InfusionEnchantmentRecipe;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;

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

	public static void postInit() {
		addInfusionEnchantmentRecipe(Enchantment.enchantmentsList[ModEnchants.imperviousness.effectId], 4, new AspectList().add(Aspect.SLIME, 6).add(Aspect.WATER, 4).add(Aspect.MAGIC, 10), ItemApi.getItem("itemResource", 14), new ItemStack(Item.dyePowder, 1, 2), new ItemStack(Item.slimeBall));
	}

	private static void addInfusionEnchantmentRecipe(Enchantment ench, int instability, AspectList aspects, ItemStack... ingredients) {
		InfusionEnchantmentRecipe recipe = ThaumcraftApi.addInfusionEnchantmentRecipe("INFUSIONENCHANTMENT", ench, instability, aspects, ingredients);
		ResearchItem research = ResearchCategories.getResearch("INFUSIONENCHANTMENT");

		ResearchPage[] pages = new ResearchPage[research.getPages().length + 1];
		for (int i = 0; i < research.getPages().length; i++)
			pages[i] = research.getPages()[i];
		pages[pages.length - 1] = new ResearchPage(recipe);

		research.setPages(pages);
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