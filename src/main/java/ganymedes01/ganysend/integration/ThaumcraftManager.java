package ganymedes01.ganysend.integration;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.ModBlocks;
import ganymedes01.ganysend.ModItems;
import ganymedes01.ganysend.enchantment.ModEnchants;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
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

public class ThaumcraftManager extends Integration {

	@Override
	public void init() {
		ThaumcraftApi.addSmeltingBonus("oreEndium", new ItemStack(ModItems.endiumIngot, 0, 1));
		ThaumcraftApi.portableHoleBlackList.add(ModBlocks.enderToggler_air);

		addAspectsToItem(ModItems.endiumIngot, new Aspect[] { Aspect.ELDRITCH, Aspect.TRAVEL, Aspect.MAGIC, Aspect.SENSES, Aspect.DARKNESS }, new int[] { 4, 4, 7, 2, 2 });
		addAspectsToItem(ModItems.endstoneRod, new Aspect[] { Aspect.DARKNESS, Aspect.EARTH }, new int[] { 1, 1 });
		addAspectsToItem(ModItems.skull, new Aspect[] { Aspect.FLESH, Aspect.DEATH, Aspect.SOUL }, new int[] { 4, 4, 4 });
		addAspectsToItem(new ItemStack(ModItems.skull, 1, 3), new Aspect[] { Aspect.DEATH, Aspect.SOUL, Aspect.MAN }, new int[] { 4, 4, 4 });
		addAspectsToItem(ModBlocks.enderFlower, new Aspect[] { Aspect.PLANT, Aspect.SENSES, Aspect.LIFE, Aspect.ELDRITCH, Aspect.MAGIC, Aspect.TRAVEL }, new int[] { 1, 1, 1, 1, 1, 1 });
		addAspectsToItem(ModBlocks.rawEndium, new Aspect[] { Aspect.ELDRITCH, Aspect.TRAVEL, Aspect.MAGIC, Aspect.SENSES, Aspect.DARKNESS }, new int[] { 4, 4, 7, 2, 2 });
	}

	@Override
	public void postInit() {
	}

	public void postPostInit() {
		if (GanysEnd.enableEndiumArmour)
			addInfusionEnchantmentRecipe(Enchantment.enchantmentsList[ModEnchants.imperviousness.effectId], 4, new AspectList().add(Aspect.SLIME, 6).add(Aspect.WATER, 4).add(Aspect.MAGIC, 10), ItemApi.getItem("itemResource", 14), new ItemStack(Items.dye, 1, 2), new ItemStack(Items.slime_ball));
	}

	@Override
	public String getModID() {
		return "Thaumcraft";
	}

	private void addInfusionEnchantmentRecipe(Enchantment ench, int instability, AspectList aspects, ItemStack... ingredients) {
		InfusionEnchantmentRecipe recipe = ThaumcraftApi.addInfusionEnchantmentRecipe("INFUSIONENCHANTMENT", ench, instability, aspects, ingredients);
		ResearchItem research = ResearchCategories.getResearch("INFUSIONENCHANTMENT");

		ResearchPage[] pages = new ResearchPage[research.getPages().length + 1];
		for (int i = 0; i < research.getPages().length; i++)
			pages[i] = research.getPages()[i];
		pages[pages.length - 1] = new ResearchPage(recipe);

		research.setPages(pages);
	}

	private void addAspectsToItem(Block id, Aspect[] aspects, int[] amounts) {
		addAspectsToItem(new ItemStack(id), aspects, amounts);
	}

	private void addAspectsToItem(Item id, Aspect[] aspects, int[] amounts) {
		addAspectsToItem(new ItemStack(id), aspects, amounts);
	}

	private void addAspectsToItem(ItemStack stack, Aspect[] aspects, int[] amounts) {
		AspectList list = new AspectList();
		for (int i = 0; i < aspects.length; i++)
			list.add(aspects[i], amounts[i]);

		ThaumcraftApi.registerObjectTag(stack, list);
	}
}