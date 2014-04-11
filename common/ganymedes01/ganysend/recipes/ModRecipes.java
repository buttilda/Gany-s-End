package ganymedes01.ganysend.recipes;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.blocks.ModBlocks;
import ganymedes01.ganysend.items.ModItems;
import ganymedes01.ganysend.lib.SkullTypes;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
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
		OreDictionary.registerOre("blockEnderPearl", new ItemStack(ModBlocks.enderpearlBlock, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("enderFlower", ModBlocks.enderFlower);
		OreDictionary.registerOre("mobHead", new ItemStack(Item.skull, 1, OreDictionary.WILDCARD_VALUE));
		for (SkullTypes skull : SkullTypes.values())
			if (skull.canShow())
				OreDictionary.registerOre("mobHead", new ItemStack(ModItems.itemNewSkull, 1, skull.ordinal()));
		OreDictionary.registerOre("nightGemMaterial", new ItemStack(Block.mushroomBrown));
		OreDictionary.registerOre("nightGemMaterial", new ItemStack(Block.mushroomRed));
		OreDictionary.registerOre("nightGemMaterial", new ItemStack(Item.rottenFlesh));
		if (GanysEnd.enableEnderBag)
			OreDictionary.registerOre("enderChest", Block.enderChest);
	}

	private static void registerArmourRecipes() {
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.endiumHelmet), "xxx", "xyx", 'x', "ingotEndium", 'y', "dyeGreen"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.endiumChestplate), "xyx", "xxx", "xxx", 'x', "ingotEndium", 'y', "dyeGreen"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.endiumLeggings), "xxx", "xyx", "x x", 'x', "ingotEndium", 'y', "dyeGreen"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.endiumBoots), "xyx", "x x", 'x', "ingotEndium", 'y', "dyeGreen"));
	}

	private static void registerItemRecipes() {
		// Items
		GameRegistry.addSmelting(ModBlocks.rawEndium.blockID, new ItemStack(ModItems.endiumIngot), 1F);
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.enderTag), new ItemStack(Item.paper), new ItemStack(Item.enderPearl));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.endiumIngot, 9), "blockEndium"));
		GameRegistry.addRecipe(new ItemStack(ModItems.endstoneRod, 4), "x", "x", 'x', Block.whiteStone);
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.enderScythe), "xxy", " y ", "y  ", 'x', "ingotEndium", 'y', ModItems.endstoneRod));
		GameRegistry.addRecipe(new ItemStack(ModItems.infiniteBucket), "xxx", "zxz", "yzy", 'x', Item.bucketWater, 'y', Item.enderPearl, 'z', Item.ingotGold);
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.infusedGem, 4, 0), "yyy", "yxy", "yyy", 'x', Item.pocketSundial, 'y', "nightGemMaterial"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.infusedGem, 4, 1), "yyy", "yxy", "yyy", 'x', Item.pocketSundial, 'y', "dayGemMaterial"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.endiumIngot), "xxx", "xxx", "xxx", 'x', "nuggetEndium"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.endiumIngot, 9, 1), "ingotEndium"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.endiumPickaxe), "xxx", " y ", " y ", 'x', "ingotEndium", 'y', ModItems.endstoneRod));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.endiumAxe), "xx", "xy", " y", 'x', "ingotEndium", 'y', ModItems.endstoneRod));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.endiumShovel), "x", "y", "y", 'x', "ingotEndium", 'y', ModItems.endstoneRod));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.reinforcedEndiumAxe), " x ", "xyx", 'x', Item.diamond, 'y', ModItems.endiumAxe));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.reinforcedEndiumPickaxe), " x ", "xyx", 'x', Item.diamond, 'y', ModItems.endiumPickaxe));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.reinforcedEndiumShovel), " x ", "xyx", 'x', Item.diamond, 'y', ModItems.endiumShovel));
		if (GanysEnd.enableEnderBag)
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.enderBag), "zwz", "wyw", "xxx", 'x', Item.enderPearl, 'y', "enderChest", 'z', new ItemStack(Block.cloth, 1, 15), 'w', new ItemStack(Block.cloth, 1, 13)));

		// Vanilla
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.expBottle, 8), "yyy", "yxy", "yyy", 'x', "ingotEndium", 'y', new ItemStack(Item.potion, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.enderPearl), "xxx", "xxx", "x x", 'x', "enderFlower"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Item.enderPearl, 9), "blockEnderPearl"));
		GameRegistry.addShapelessRecipe(new ItemStack(Item.netherStar, 2), new ItemStack(ModItems.itemNewSkull, 1, 17));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Item.enderPearl, 4), "mobHead", "mobHead"));
	}

	private static void registerBlockRecipes() {
		GameRegistry.addRecipe(new ItemStack(ModBlocks.endstoneBrick, 4), "xx", "xx", 'x', Block.whiteStone);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.enderpearlBlock, 1, 0), "xxx", "xxx", "xxx", 'x', Item.enderPearl);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.enderpearlBlock, 4, 1), "xx", "xx", 'x', new ItemStack(ModBlocks.enderpearlBlock, 1, 0));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.endstoneStairs, 4), "x  ", "xx ", "xxx", 'x', ModBlocks.endstoneBrick);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.enderpearlStairs, 4), "x  ", "xx ", "xxx", 'x', new ItemStack(ModBlocks.enderpearlBlock, 1, 1));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.enderToggler), "wwz", "zyz", "zww", 'y', Item.enderPearl, 'z', Block.glass, 'w', Item.redstone);
		if (GanysEnd.activateShifters) {
			GameRegistry.addRecipe(new ItemStack(ModBlocks.blockShifter), "yzy", "wxw", "ywy", 'x', new ItemStack(ModBlocks.enderpearlBlock, 1, 0), 'y', Item.ingotGold, 'z', Item.redstone, 'w', Block.glass);
			GameRegistry.addRecipe(new ItemStack(ModBlocks.entityShifter), "yzy", "wxw", "ywy", 'x', new ItemStack(ModBlocks.enderpearlBlock, 1, 1), 'y', Item.ingotGold, 'z', Item.redstone, 'w', Block.glass);
		}
		GameRegistry.addRecipe(new ItemStack(ModBlocks.emulator), "xyx", "yyy", "xyx", 'x', Item.enderPearl, 'y', Block.hardenedClay);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.emulator), "xyx", "yyy", "xyx", 'x', Item.enderPearl, 'y', Block.stainedClay);
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.emulator, 8), "xyx", "yyy", "xyx", 'x', "enderFlower", 'y', Block.hardenedClay));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.emulator, 8), "xyx", "yyy", "xyx", 'x', "enderFlower", 'y', Block.stainedClay));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.endiumBlock), "xxx", "xxx", "xxx", 'x', "ingotEndium"));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.basicFilteringHopper), " z ", "yxy", 'x', Block.hopperBlock, 'y', Item.enderPearl, 'z', Item.ingotGold);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.exclusiveFilteringHopper), "yxy", 'x', ModBlocks.basicFilteringHopper, 'y', Item.redstone);
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.basicFilteringHopper), new ItemStack(ModBlocks.exclusiveFilteringHopper), new ItemStack(Item.ingotGold));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.speedyBasicFilteringHopper), "yyy", "yxy", "yyy", 'x', ModBlocks.basicFilteringHopper, 'y', new ItemStack(Item.dyePowder, 1, 4));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.speedyExclusiveFilteringHopper), "yyy", "yxy", "yyy", 'x', ModBlocks.exclusiveFilteringHopper, 'y', new ItemStack(Item.dyePowder, 1, 4));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.speedyHopper), "yyy", "yxy", "yyy", 'x', Block.hopperBlock, 'y', new ItemStack(Item.dyePowder, 1, 4));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.speedyBasicFilteringHopper), " z ", "yxy", 'x', ModBlocks.speedyHopper, 'y', Item.enderPearl, 'z', Item.ingotGold);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.speedyExclusiveFilteringHopper), "yxy", 'x', ModBlocks.speedyBasicFilteringHopper, 'y', Item.redstone);
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.speedyBasicFilteringHopper), new ItemStack(ModBlocks.speedyExclusiveFilteringHopper), new ItemStack(Item.ingotGold));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.advancedFilteringHopper), " z ", "yxy", " z ", 'x', ModBlocks.speedyBasicFilteringHopper, 'y', Item.diamond, 'z', Block.glass);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.advancedExclusiveFilteringHopper), " z ", "yxy", " z ", 'x', ModBlocks.speedyExclusiveFilteringHopper, 'y', Item.diamond, 'z', Block.glass);
		if (GanysEnd.enableTimeManipulator) {
			GameRegistry.addShapelessRecipe(new ItemStack(Block.dragonEgg), new ItemStack(ModBlocks.timeManipulator));
			GameRegistry.addRecipe(new ItemStack(ModBlocks.timeManipulator), "zyz", "yxy", "zyz", 'x', Block.dragonEgg, 'y', new ItemStack(Block.planks, 1, 1), 'z', Block.blockGold);
		}
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.inventoryBinder), "xxx", "yzy", "xxx", 'x', Block.whiteStone, 'y', Item.enderPearl, 'z', "mobHead"));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.infiniteWaterSource), "yzy", "zxz", "yzy", 'x', ModItems.infiniteBucket, 'y', Item.ingotIron, 'z', Item.enderPearl);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.endWalls, 6, 0), "xxx", "xxx", 'x', ModBlocks.endstoneBrick);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.endWalls, 6, 1), "xxx", "xxx", 'x', new ItemStack(ModBlocks.enderpearlBlock, 1, 1));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.voidCrate), "xyx", "xzx", "xwx", 'x', "logWood", 'y', Block.chest, 'z', Item.enderPearl, 'w', Block.obsidian));
		if (GanysEnd.enableRawEndiumRecipe)
			GameRegistry.addRecipe(new ItemStack(ModBlocks.rawEndium), "zyz", "yxy", "zyz", 'x', Item.eyeOfEnder, 'y', Block.whiteStone, 'z', Block.obsidian);

		// Ender Flower Recipes
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.dyePowder, 2, 0), "xxx", " x ", "   ", 'x', "enderFlower")); // Black
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.dyePowder, 2, 1), "x x", "   ", "   ", 'x', "enderFlower")); // Red
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.dyePowder, 2, 2), "   ", "xx ", "   ", 'x', "enderFlower")); // Green
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.dyePowder, 1, 3), "   ", "x x", " x ", 'x', "enderFlower")); // Brown
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.dyePowder, 1, 4), "xxx", "x x", "xxx", 'x', "enderFlower")); // Blue
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.dyePowder, 2, 5), "   ", "   ", "x x", 'x', "enderFlower")); // Purple
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.dyePowder, 2, 6), "x  ", "x  ", "   ", 'x', "enderFlower")); // Cyan
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.dyePowder, 2, 7), " x ", " x ", "   ", 'x', "enderFlower")); // Silver
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.dyePowder, 2, 8), "  x", "   ", "  x", 'x', "enderFlower")); // Gray
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.dyePowder, 2, 9), "   ", "x  ", "x  ", 'x', "enderFlower")); // Pink
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.dyePowder, 2, 10), "   ", " x ", " x ", 'x', "enderFlower")); // Lime
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.dyePowder, 2, 11), " x ", "   ", " x ", 'x', "enderFlower")); // Yellow
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.dyePowder, 2, 12), "x  ", " x ", "   ", 'x', "enderFlower")); // L.Blue
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.dyePowder, 2, 13), "   ", " x ", "  x", 'x', "enderFlower")); // Magenta
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.dyePowder, 2, 14), " x ", "x  ", "   ", 'x', "enderFlower")); // Orange
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.dyePowder, 2, 15), "   ", "  x", " x ", 'x', "enderFlower")); // White
	}
}