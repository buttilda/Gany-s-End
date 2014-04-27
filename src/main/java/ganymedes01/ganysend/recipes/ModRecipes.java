package ganymedes01.ganysend.recipes;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.blocks.ModBlocks;
import ganymedes01.ganysend.items.ModItems;
import ganymedes01.ganysend.lib.SkullTypes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class ModRecipes {

	public static void init() {
		registerOreDictionary();

		registerBlockRecipes();
		registerItemRecipes();
		registerArmourRecipes();
	}

	private static void registerOreDictionary() {
		OreDictionary.registerOre("oreEndium", ModBlocks.rawEndium);
		OreDictionary.registerOre("ingotEndium", new ItemStack(ModItems.endiumIngot));
		OreDictionary.registerOre("nuggetEndium", new ItemStack(ModItems.endiumIngot, 1, 1));
		OreDictionary.registerOre("blockEndium", ModBlocks.endiumBlock);
		OreDictionary.registerOre("blockender_pearl", new ItemStack(ModBlocks.enderpearlBlock, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("enderFlower", ModBlocks.enderFlower);
		OreDictionary.registerOre("mobHead", new ItemStack(Items.skull, 1, OreDictionary.WILDCARD_VALUE));
		for (SkullTypes skull : SkullTypes.values())
			if (skull.canShow())
				OreDictionary.registerOre("mobHead", new ItemStack(ModItems.itemNewSkull, 1, skull.ordinal()));
		OreDictionary.registerOre("nightGemMaterial", new ItemStack(Blocks.brown_mushroom));
		OreDictionary.registerOre("nightGemMaterial", new ItemStack(Blocks.red_mushroom));
		OreDictionary.registerOre("nightGemMaterial", new ItemStack(Items.rotten_flesh));
		if (GanysEnd.enableEnderBag)
			OreDictionary.registerOre("enderChest", Blocks.ender_chest);
	}

	private static void registerArmourRecipes() {
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.endiumHelmet), "xxx", "xyx", 'x', "ingotEndium", 'y', "dyeGreen"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.endiumChestplate), "xyx", "xxx", "xxx", 'x', "ingotEndium", 'y', "dyeGreen"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.endiumLeggings), "xxx", "xyx", "x x", 'x', "ingotEndium", 'y', "dyeGreen"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.endiumBoots), "xyx", "x x", 'x', "ingotEndium", 'y', "dyeGreen"));
	}

	private static void registerItemRecipes() {
		// Items
		GameRegistry.addSmelting(ModBlocks.rawEndium, new ItemStack(ModItems.endiumIngot), 1F);
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.enderTag), new ItemStack(Items.paper), new ItemStack(Items.ender_pearl));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.endiumIngot, 9), "blockEndium"));
		GameRegistry.addRecipe(new ItemStack(ModItems.endstoneRod, 4), "x", "x", 'x', Blocks.end_stone);
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.enderScythe), "xxy", " y ", "y  ", 'x', "ingotEndium", 'y', ModItems.endstoneRod));
		GameRegistry.addRecipe(new ItemStack(ModItems.infiniteBucket), "xxx", "zxz", "yzy", 'x', Items.water_bucket, 'y', Items.ender_pearl, 'z', Items.gold_ingot);
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.infusedGem, 4, 0), "yyy", "yxy", "yyy", 'x', Items.clock, 'y', "nightGemMaterial"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.infusedGem, 4, 1), "yyy", "yxy", "yyy", 'x', Items.clock, 'y', "dayGemMaterial"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.endiumIngot), "xxx", "xxx", "xxx", 'x', "nuggetEndium"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.endiumIngot, 9, 1), "ingotEndium"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.endiumPickaxe), "xxx", " y ", " y ", 'x', "ingotEndium", 'y', ModItems.endstoneRod));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.endiumAxe), "xx", "xy", " y", 'x', "ingotEndium", 'y', ModItems.endstoneRod));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.endiumShovel), "x", "y", "y", 'x', "ingotEndium", 'y', ModItems.endstoneRod));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.reinforcedEndiumAxe), " x ", "xyx", 'x', Items.diamond, 'y', ModItems.endiumAxe));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.reinforcedEndiumPickaxe), " x ", "xyx", 'x', Items.diamond, 'y', ModItems.endiumPickaxe));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.reinforcedEndiumShovel), " x ", "xyx", 'x', Items.diamond, 'y', ModItems.endiumShovel));
		if (GanysEnd.enableEnderBag)
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.enderBag), "zwz", "wyw", "xxx", 'x', Items.ender_pearl, 'y', "enderChest", 'z', new ItemStack(Blocks.wool, 1, 15), 'w', new ItemStack(Blocks.wool, 1, 13)));

		// Vanilla
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.experience_bottle, 8), "yyy", "yxy", "yyy", 'x', "ingotEndium", 'y', new ItemStack(Items.potionitem, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.ender_pearl), "xxx", "xxx", "x x", 'x', "enderFlower"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.ender_pearl, 9), "blockender_pearl"));
	}

	private static void registerBlockRecipes() {
		GameRegistry.addRecipe(new ItemStack(ModBlocks.endstoneBrick, 4), "xx", "xx", 'x', Blocks.end_stone);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.enderpearlBlock, 1, 0), "xxx", "xxx", "xxx", 'x', Items.ender_pearl);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.enderpearlBlock, 4, 1), "xx", "xx", 'x', new ItemStack(ModBlocks.enderpearlBlock, 1, 0));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.endstoneStairs, 4), "x  ", "xx ", "xxx", 'x', ModBlocks.endstoneBrick);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.enderpearlStairs, 4), "x  ", "xx ", "xxx", 'x', new ItemStack(ModBlocks.enderpearlBlock, 1, 1));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.enderToggler), "wwz", "zyz", "zww", 'y', Items.ender_pearl, 'z', Blocks.glass, 'w', Items.redstone);
		if (GanysEnd.activateShifters) {
			GameRegistry.addRecipe(new ItemStack(ModBlocks.blockShifter), "yzy", "wxw", "ywy", 'x', new ItemStack(ModBlocks.enderpearlBlock, 1, 0), 'y', Items.gold_ingot, 'z', Items.redstone, 'w', Blocks.glass);
			GameRegistry.addRecipe(new ItemStack(ModBlocks.entityShifter), "yzy", "wxw", "ywy", 'x', new ItemStack(ModBlocks.enderpearlBlock, 1, 1), 'y', Items.gold_ingot, 'z', Items.redstone, 'w', Blocks.glass);
		}
		GameRegistry.addRecipe(new ItemStack(ModBlocks.emulator), "xyx", "yyy", "xyx", 'x', Items.ender_pearl, 'y', Blocks.hardened_clay);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.emulator), "xyx", "yyy", "xyx", 'x', Items.ender_pearl, 'y', Blocks.stained_hardened_clay);
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.emulator, 8), "xyx", "yyy", "xyx", 'x', "enderFlower", 'y', Blocks.hardened_clay));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.emulator, 8), "xyx", "yyy", "xyx", 'x', "enderFlower", 'y', Blocks.stained_hardened_clay));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.endiumBlock), "xxx", "xxx", "xxx", 'x', "ingotEndium"));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.basicFilteringHopper), " z ", "yxy", 'x', Blocks.hopper, 'y', Items.ender_pearl, 'z', Items.gold_ingot);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.exclusiveFilteringHopper), "yxy", 'x', ModBlocks.basicFilteringHopper, 'y', Items.redstone);
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.basicFilteringHopper), new ItemStack(ModBlocks.exclusiveFilteringHopper), new ItemStack(Items.gold_ingot));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.speedyBasicFilteringHopper), "yyy", "yxy", "yyy", 'x', ModBlocks.basicFilteringHopper, 'y', new ItemStack(Items.dye, 1, 4));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.speedyExclusiveFilteringHopper), "yyy", "yxy", "yyy", 'x', ModBlocks.exclusiveFilteringHopper, 'y', new ItemStack(Items.dye, 1, 4));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.speedyHopper), "yyy", "yxy", "yyy", 'x', Blocks.hopper, 'y', new ItemStack(Items.dye, 1, 4));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.speedyBasicFilteringHopper), " z ", "yxy", 'x', ModBlocks.speedyHopper, 'y', Items.ender_pearl, 'z', Items.gold_ingot);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.speedyExclusiveFilteringHopper), "yxy", 'x', ModBlocks.speedyBasicFilteringHopper, 'y', Items.redstone);
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.speedyBasicFilteringHopper), new ItemStack(ModBlocks.speedyExclusiveFilteringHopper), new ItemStack(Items.gold_ingot));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.advancedFilteringHopper), " z ", "yxy", " z ", 'x', ModBlocks.speedyBasicFilteringHopper, 'y', Items.diamond, 'z', Blocks.glass);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.advancedExclusiveFilteringHopper), " z ", "yxy", " z ", 'x', ModBlocks.speedyExclusiveFilteringHopper, 'y', Items.diamond, 'z', Blocks.glass);
		if (GanysEnd.enableTimeManipulator)
			GameRegistry.addRecipe(new ItemStack(ModBlocks.timeManipulator), "zyz", "yxy", "zyz", 'x', Blocks.dragon_egg, 'y', new ItemStack(Blocks.planks, 1, 1), 'z', Blocks.gold_block);
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.inventoryBinder), "xxx", "yzy", "xxx", 'x', Blocks.end_stone, 'y', Items.ender_pearl, 'z', "mobHead"));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.infiniteWaterSource), "yzy", "zxz", "yzy", 'x', ModItems.infiniteBucket, 'y', Items.iron_ingot, 'z', Items.ender_pearl);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.endWalls, 6, 0), "xxx", "xxx", 'x', ModBlocks.endstoneBrick);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.endWalls, 6, 1), "xxx", "xxx", 'x', new ItemStack(ModBlocks.enderpearlBlock, 1, 1));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.voidCrate), "xyx", "xzx", "xwx", 'x', "logWood", 'y', Blocks.chest, 'z', Items.ender_pearl, 'w', Blocks.obsidian));
		if (GanysEnd.enableRawEndiumRecipe)
			GameRegistry.addRecipe(new ItemStack(ModBlocks.rawEndium), "zyz", "yxy", "zyz", 'x', Items.ender_eye, 'y', Blocks.end_stone, 'z', Blocks.obsidian);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.enderFurnace), "xxx", "xyx", "xxx", 'x', new ItemStack(Blocks.end_stone), 'y', Items.ender_eye);
	}
}