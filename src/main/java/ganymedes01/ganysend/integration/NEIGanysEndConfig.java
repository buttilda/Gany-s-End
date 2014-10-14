package ganymedes01.ganysend.integration;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.ModBlocks;
import ganymedes01.ganysend.ModItems;
import ganymedes01.ganysend.integration.nei.EnderFurnaceRecipeHandler;
import ganymedes01.ganysend.lib.Reference;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
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
		if (!GanysEnd.enableTimeManipulator) {
			API.hideItem(new ItemStack(ModBlocks.timeManipulator));
			API.hideItem(new ItemStack(ModItems.infusedGem, 1, OreDictionary.WILDCARD_VALUE));
		}
		if (!GanysEnd.enableShifters) {
			API.hideItem(new ItemStack(ModBlocks.blockShifter));
			API.hideItem(new ItemStack(ModBlocks.entityShifter));
			API.hideItem(new ItemStack(ModItems.enderTag));
		}
		if (!GanysEnd.enableAnchoredEnderChest)
			API.hideItem(new ItemStack(ModBlocks.anchoredEnderChest));
		if (GanysEnd.isHeadcrumbsLoaded)
			API.hideItem(new ItemStack(ModItems.skull, 1, OreDictionary.WILDCARD_VALUE));
		if (!GanysEnd.enableEnderFlower)
			API.hideItem(new ItemStack(ModBlocks.enderFlower));
		if (!GanysEnd.enableEndiumArmour) {
			API.hideItem(new ItemStack(ModItems.endiumHelmet));
			API.hideItem(new ItemStack(ModItems.endiumChestplate));
			API.hideItem(new ItemStack(ModItems.endiumLeggings));
			API.hideItem(new ItemStack(ModItems.endiumBoots));
		}
		if (!GanysEnd.enableEndiumTools) {
			API.hideItem(new ItemStack(ModItems.endiumPickaxe));
			API.hideItem(new ItemStack(ModItems.endiumAxe));
			API.hideItem(new ItemStack(ModItems.endiumShovel));
			API.hideItem(new ItemStack(ModItems.endiumSword));
			API.hideItem(new ItemStack(ModItems.endiumBow));

			API.hideItem(new ItemStack(ModItems.reinforcedEndiumPickaxe));
			API.hideItem(new ItemStack(ModItems.reinforcedEndiumAxe));
			API.hideItem(new ItemStack(ModItems.reinforcedEndiumShovel));
			API.hideItem(new ItemStack(ModItems.reinforcedEndiumSword));
			API.hideItem(new ItemStack(ModItems.reinforcedEndiumBow));
		}
		if (!GanysEnd.enableHoppers) {
			API.hideItem(new ItemStack(ModBlocks.basicFilteringHopper));
			API.hideItem(new ItemStack(ModBlocks.exclusiveFilteringHopper));
			API.hideItem(new ItemStack(ModBlocks.speedyBasicFilteringHopper));
			API.hideItem(new ItemStack(ModBlocks.speedyExclusiveFilteringHopper));
			API.hideItem(new ItemStack(ModBlocks.speedyHopper));
			API.hideItem(new ItemStack(ModBlocks.advancedFilteringHopper));
			API.hideItem(new ItemStack(ModBlocks.advancedExclusiveFilteringHopper));
			API.hideItem(new ItemStack(ModBlocks.creativeSpeedyHopper));
		}

		if (!GanysEnd.enableDecorativeBlocks) {
			API.hideItem(new ItemStack(ModBlocks.endstoneBrick));
			API.hideItem(new ItemStack(ModBlocks.enderpearlBlock, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(ModBlocks.endstoneStairs));
			API.hideItem(new ItemStack(ModBlocks.enderpearlStairs));
			API.hideItem(new ItemStack(ModBlocks.endWalls, 1, OreDictionary.WILDCARD_VALUE));
		}

		if (!GanysEnd.enableVoidCrate)
			API.hideItem(new ItemStack(ModBlocks.voidCrate));

		if (!GanysEnd.enableInfiniteWaterSource) {
			API.hideItem(new ItemStack(ModBlocks.infiniteWaterSource));
			API.hideItem(new ItemStack(ModBlocks.creativeInfiniteFluidSource));
		}

		if (!GanysEnd.enableInventoryBinder)
			API.hideItem(new ItemStack(ModBlocks.inventoryBinder));

		if (!GanysEnd.enableEmulator)
			API.hideItem(new ItemStack(ModBlocks.emulator));

		if (!GanysEnd.enableEnderFurnace)
			API.hideItem(new ItemStack(ModBlocks.enderFurnace));

		if (!GanysEnd.enableScythe)
			API.hideItem(new ItemStack(ModItems.enderScythe));

		if (!GanysEnd.enableInfiniteBucket)
			API.hideItem(new ItemStack(ModItems.infiniteBucket));

		if (!GanysEnd.enableSkulls)
			API.hideItem(new ItemStack(ModItems.skull, 1, OreDictionary.WILDCARD_VALUE));

		if (!GanysEnd.enableEnderToggler)
			API.hideItem(new ItemStack(ModBlocks.enderToggler));

		if (!GanysEnd.enableEndiumTools && !GanysEnd.enableScythe)
			API.hideItem(new ItemStack(ModItems.endstoneRod));

		if (!GanysEnd.enableEndium) {
			API.hideItem(new ItemStack(ModItems.endiumIngot, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(ModBlocks.endiumBlock));
			API.hideItem(new ItemStack(ModBlocks.rawEndium));
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