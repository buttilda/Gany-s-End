package ganymedes01.ganysend.integration;

import ganymedes01.ganysend.ModBlocks;
import ganymedes01.ganysend.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
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

public class ThaumcraftManager extends Integration {

	@Override
	public void init() {
		ThaumcraftApi.addSmeltingBonus("oreEndium", new ItemStack(ModItems.endiumIngot, 0, 1));
		ThaumcraftApi.portableHoleBlackList.add(ModBlocks.enderToggler_air);

		addAspectsToItem(ModItems.endiumIngot, new Aspect[] { Aspect.ELDRITCH, Aspect.TRAVEL, Aspect.MAGIC, Aspect.SENSES, Aspect.DARKNESS }, new int[] { 4, 4, 7, 2, 2 });
		addAspectsToItem(ModItems.endstoneRod, new Aspect[] { Aspect.DARKNESS, Aspect.EARTH }, new int[] { 1, 1 });
		addAspectsToItem(ModBlocks.enderFlower, new Aspect[] { Aspect.PLANT, Aspect.SENSES, Aspect.LIFE, Aspect.ELDRITCH, Aspect.MAGIC, Aspect.TRAVEL }, new int[] { 1, 1, 1, 1, 1, 1 });
		addAspectsToItem(ModBlocks.rawEndium, new Aspect[] { Aspect.ELDRITCH, Aspect.TRAVEL, Aspect.MAGIC, Aspect.SENSES, Aspect.DARKNESS }, new int[] { 4, 4, 7, 2, 2 });
	}

	@Override
	public void postInit() {
	}

	@Override
	public String getModID() {
		return "Thaumcraft";
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