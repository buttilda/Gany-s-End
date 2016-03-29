package ganymedes01.ganysend.recipes;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.ModBlocks;
import ganymedes01.ganysend.ModItems;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class ModRecipes {

	public static void init() {
		registerBlockRecipes();
		registerItemRecipes();
		registerArmourRecipes();
	}

	public static void registerOreDictionary() {
		OreDictionary.registerOre("chestWood", Blocks.chest);

		OreDictionary.registerOre("itemSkull", new ItemStack(Items.skull, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("skullSkeleton", new ItemStack(Items.skull, 1, 0));
		OreDictionary.registerOre("skullWitherSkeleton", new ItemStack(Items.skull, 1, 1));
		OreDictionary.registerOre("skullZombie", new ItemStack(Items.skull, 1, 2));
		OreDictionary.registerOre("skullPlayer", new ItemStack(Items.skull, 1, 3));
		OreDictionary.registerOre("skullCreeper", new ItemStack(Items.skull, 1, 4));

		if (GanysEnd.enableEndium) {
			OreDictionary.registerOre("oreEndium", ModBlocks.raw_endium);
			OreDictionary.registerOre("ingotEndium", new ItemStack(ModItems.endiumIngot));
			OreDictionary.registerOre("nuggetEndium", new ItemStack(ModItems.endiumIngot, 1, 1));
			OreDictionary.registerOre("blockEndium", ModBlocks.endium_block);
		}

		if (GanysEnd.enableDecorativeBlocks) {
			OreDictionary.registerOre("blockEnderPearl", new ItemStack(ModBlocks.enderpearl_block, 1, OreDictionary.WILDCARD_VALUE));
			OreDictionary.registerOre("brickEndStone", new ItemStack(ModBlocks.endstone_bricks, 1, OreDictionary.WILDCARD_VALUE));
		}

		if (GanysEnd.enableEnderFlower)
			OreDictionary.registerOre("flowerEnder", ModBlocks.ender_flower);

		if (GanysEnd.enableTimeManipulator) {
			OreDictionary.registerOre("nightGemMaterial", new ItemStack(Blocks.brown_mushroom));
			OreDictionary.registerOre("nightGemMaterial", new ItemStack(Blocks.red_mushroom));
			OreDictionary.registerOre("nightGemMaterial", new ItemStack(Items.rotten_flesh));
			OreDictionary.registerOre("gemNight", new ItemStack(ModItems.infusedGem));
			OreDictionary.registerOre("gemDay", new ItemStack(ModItems.infusedGem, 1, 1));
		}

		if (GanysEnd.enableEnderBag || GanysEnd.enableAnchoredEnderChest)
			OreDictionary.registerOre("chestEnder", Blocks.ender_chest);

		if (GanysEnd.enableAnchoredEnderChest)
			OreDictionary.registerOre("transdimBlock", new ItemStack(ModBlocks.anchored_ender_chest));
	}

	private static void registerArmourRecipes() {
		if (GanysEnd.enableEndiumArmour) {
			addShapedRecipe(ModItems.endiumHelmet, "xxx", "xyx", 'x', "ingotEndium", 'y', "dyeGreen");
			addShapedRecipe(ModItems.endiumChestplate, "xyx", "xxx", "xxx", 'x', "ingotEndium", 'y', "dyeGreen");
			addShapedRecipe(ModItems.endiumLeggings, "xxx", "xyx", "x x", 'x', "ingotEndium", 'y', "dyeGreen");
			addShapedRecipe(ModItems.endiumBoots, "xyx", "x x", 'x', "ingotEndium", 'y', "dyeGreen");
		}
	}

	private static void registerItemRecipes() {
		if (GanysEnd.enableEndium) {
			GameRegistry.addSmelting(ModBlocks.raw_endium, new ItemStack(ModItems.endiumIngot), 1F);
			addShapelessRecipe(new ItemStack(ModItems.endiumIngot, 9), "blockEndium");
			addShapedRecipe(ModItems.endiumIngot, "xxx", "xxx", "xxx", 'x', "nuggetEndium");
			addShapelessRecipe(new ItemStack(ModItems.endiumIngot, 9, 1), "ingotEndium");
			addShapedRecipe(new ItemStack(Items.experience_bottle, 8), "yyy", "yxy", "yyy", 'x', "ingotEndium", 'y', new ItemStack(Items.potionitem));
		}

		if (GanysEnd.enableEndiumTools || GanysEnd.enableScythe)
			addShapedRecipe(new ItemStack(ModItems.endstoneRod, 4), "x", "x", 'x', Blocks.end_stone);

		if (GanysEnd.enableScythe) {
			addShapedRecipe(ModItems.enderScythe, "xxy", " y ", "y  ", 'x', "ingotEndium", 'y', ModItems.endstoneRod);
			addShapedRecipe(ModItems.reinforcedEnderScythe, " x ", "xyx", 'x', "gemDiamond", 'y', ModItems.enderScythe);
		}

		if (GanysEnd.enableInfiniteBucket)
			addShapedRecipe(ModItems.infiniteBucket, "xxx", "zxz", "yzy", 'x', Items.water_bucket, 'y', Items.ender_pearl, 'z', "ingotGold");

		if (GanysEnd.enableTimeManipulator) {
			addShapedRecipe(new ItemStack(ModItems.infusedGem, 4, 0), "yyy", "yxy", "yyy", 'x', Items.clock, 'y', "nightGemMaterial");
			addShapedRecipe(new ItemStack(ModItems.infusedGem, 4, 1), "yyy", "yxy", "yyy", 'x', Items.clock, 'y', "dayGemMaterial");
		}

		if (GanysEnd.enableEndiumTools) {
			addShapedRecipe(ModItems.endiumPickaxe, "xxx", " y ", " y ", 'x', "ingotEndium", 'y', ModItems.endstoneRod);
			addShapedRecipe(ModItems.endiumAxe, "xx", "xy", " y", 'x', "ingotEndium", 'y', ModItems.endstoneRod);
			addShapedRecipe(ModItems.endiumShovel, "x", "y", "y", 'x', "ingotEndium", 'y', ModItems.endstoneRod);
			addShapedRecipe(ModItems.endiumSword, "x", "x", "y", 'x', "ingotEndium", 'y', ModItems.endstoneRod);
			addShapedRecipe(ModItems.endiumBow, " xy", "x y", " xy", 'x', "ingotEndium", 'y', ModItems.endstoneRod);
			addShapedRecipe(ModItems.reinforcedEndiumAxe, " x ", "xyx", 'x', "gemDiamond", 'y', ModItems.endiumAxe);
			addShapedRecipe(ModItems.reinforcedEndiumPickaxe, " x ", "xyx", 'x', "gemDiamond", 'y', ModItems.endiumPickaxe);
			addShapedRecipe(ModItems.reinforcedEndiumShovel, " x ", "xyx", 'x', "gemDiamond", 'y', ModItems.endiumShovel);
			addShapedRecipe(ModItems.reinforcedEndiumSword, " x ", "xyx", 'x', "gemDiamond", 'y', ModItems.endiumSword);
			addShapedRecipe(ModItems.reinforcedEndiumBow, " x ", "xyx", 'x', "gemDiamond", 'y', ModItems.endiumBow);
		}

		if (GanysEnd.enableEnderBag)
			if (GanysEnd.enableAnchoredEnderChest)
				addShapedRecipe(ModItems.enderBag, "zwz", "wyw", "xxx", 'x', Items.ender_pearl, 'y', ModBlocks.anchored_ender_chest, 'z', Items.string, 'w', Items.leather);
			else
				addShapedRecipe(ModItems.enderBag, "zwz", "wyw", "xxx", 'x', Items.ender_pearl, 'y', "chestEnder", 'z', Items.string, 'w', Items.leather);

		if (GanysEnd.enableAnchoredEnderChest) {
			addShapedRecipe(ModBlocks.anchored_ender_chest, "xxx", "xyx", "xzx", 'x', "blockGold", 'y', "chestEnder", 'z', new ItemStack(Blocks.anvil));
			addShapedRecipe(new ItemStack(ModItems.anchoredEnderChestMinecart), "x", "y", 'x', ModBlocks.anchored_ender_chest, 'y', Items.minecart);
		}

		if (GanysEnd.enableEnderFlower)
			addShapedRecipe(Items.ender_pearl, "xxx", "xxx", "x x", 'x', "flowerEnder");

		if (GanysEnd.enableDecorativeBlocks)
			addShapelessRecipe(new ItemStack(Items.ender_pearl, 9), "blockEnderPearl");
	}

	private static void registerBlockRecipes() {
		if (GanysEnd.enableEndium)
			addShapedRecipe(ModBlocks.endium_block, "xxx", "xxx", "xxx", 'x', "ingotEndium");

		if (GanysEnd.enableDecorativeBlocks) {
			addShapedRecipe(new ItemStack(ModBlocks.endstone_bricks, 4), "xx", "xx", 'x', Blocks.end_stone);
			addShapedRecipe(new ItemStack(ModBlocks.enderpearl_block, 1, 0), "xxx", "xxx", "xxx", 'x', Items.ender_pearl);
			addShapedRecipe(new ItemStack(ModBlocks.enderpearl_block, 4, 1), "xx", "xx", 'x', new ItemStack(ModBlocks.enderpearl_block, 1, 0));
			addShapedRecipe(new ItemStack(ModBlocks.endstone_stairs, 4), "x  ", "xx ", "xxx", 'x', "brickEndStone");
			addShapedRecipe(new ItemStack(ModBlocks.enderpearl_stairs, 4), "x  ", "xx ", "xxx", 'x', new ItemStack(ModBlocks.enderpearl_block, 1, 1));
			addShapedRecipe(new ItemStack(ModBlocks.end_walls, 6, 0), "xxx", "xxx", 'x', "brickEndStone");
			addShapedRecipe(new ItemStack(ModBlocks.end_walls, 6, 1), "xxx", "xxx", 'x', new ItemStack(ModBlocks.enderpearl_block, 1, 1));
		}

		if (GanysEnd.enableEnderToggler)
			addShapedRecipe(ModBlocks.ender_toggler, "wwz", "zyz", "zww", 'y', Items.ender_pearl, 'z', "blockGlass", 'w', "dustRedstone");

		if (GanysEnd.enableShifters) {
			addShapedRecipe(ModBlocks.block_shifter, "yzy", "wxw", "ywy", 'x', new ItemStack(ModBlocks.enderpearl_block, 1, 0), 'y', "ingotGold", 'z', "dustRedstone", 'w', "blockGlass");
			addShapedRecipe(ModBlocks.entity_shifter, "yzy", "wxw", "ywy", 'x', new ItemStack(ModBlocks.enderpearl_block, 1, 1), 'y', "ingotGold", 'z', "dustRedstone", 'w', "blockGlass");
		}

		if (GanysEnd.enableEmulator) {
			addShapedRecipe(ModBlocks.emulator, "xyx", "yyy", "xyx", 'x', Items.ender_pearl, 'y', Blocks.hardened_clay);
			addShapedRecipe(ModBlocks.emulator, "xyx", "yyy", "xyx", 'x', Items.ender_pearl, 'y', Blocks.stained_hardened_clay);
			addShapedRecipe(new ItemStack(ModBlocks.emulator, 8), "xyx", "yyy", "xyx", 'x', "flowerEnder", 'y', Blocks.hardened_clay);
			addShapedRecipe(new ItemStack(ModBlocks.emulator, 8), "xyx", "yyy", "xyx", 'x', "flowerEnder", 'y', Blocks.stained_hardened_clay);
		}

		if (GanysEnd.enableHoppers) {
			addShapedRecipe(ModBlocks.basic_filtering_hopper, " z ", "yxy", 'x', Blocks.hopper, 'y', Items.ender_pearl, 'z', "ingotGold");
			addShapedRecipe(ModBlocks.exclusive_filtering_hopper, "yxy", 'x', ModBlocks.basic_filtering_hopper, 'y', "dustRedstone");
			addShapelessRecipe(ModBlocks.basic_filtering_hopper, new ItemStack(ModBlocks.exclusive_filtering_hopper), "ingotGold");
			addShapedRecipe(ModBlocks.speedy_basic_filtering_hopper, "yyy", "yxy", "yyy", 'x', ModBlocks.basic_filtering_hopper, 'y', "gemLapis");
			addShapedRecipe(ModBlocks.speedy_exclusive_filtering_hopper, "yyy", "yxy", "yyy", 'x', ModBlocks.exclusive_filtering_hopper, 'y', "gemLapis");
			addShapedRecipe(ModBlocks.speedy_hopper, "yyy", "yxy", "yyy", 'x', Blocks.hopper, 'y', "gemLapis");
			addShapedRecipe(ModBlocks.speedy_basic_filtering_hopper, " z ", "yxy", 'x', ModBlocks.speedy_hopper, 'y', Items.ender_pearl, 'z', "ingotGold");
			addShapedRecipe(ModBlocks.speedy_exclusive_filtering_hopper, "yxy", 'x', ModBlocks.speedy_basic_filtering_hopper, 'y', "dustRedstone");
			addShapelessRecipe(ModBlocks.speedy_basic_filtering_hopper, new ItemStack(ModBlocks.speedy_exclusive_filtering_hopper), "ingotGold");
			addShapedRecipe(ModBlocks.advanced_filtering_hopper, " z ", "yxy", " z ", 'x', ModBlocks.speedy_basic_filtering_hopper, 'y', "gemDiamond", 'z', "blockGlass");
			addShapedRecipe(ModBlocks.advanced_exclusive_filtering_hopper, " z ", "yxy", " z ", 'x', ModBlocks.speedy_exclusive_filtering_hopper, 'y', "gemDiamond", 'z', "blockGlass");
		}

		if (GanysEnd.enableTimeManipulator)
			addShapedRecipe(ModBlocks.time_manipulator, "zyz", "yxy", "zyz", 'x', Blocks.dragon_egg, 'y', new ItemStack(Blocks.planks, 1, 1), 'z', "blockGold");

		if (GanysEnd.enableInventoryBinder)
			addShapedRecipe(ModBlocks.inventory_binder, "xxx", "yzy", "xxx", 'x', Blocks.end_stone, 'y', Items.ender_pearl, 'z', "itemSkull");

		if (GanysEnd.enableInfiniteWaterSource)
			addShapedRecipe(ModBlocks.infinite_water_source, "yzy", "zxz", "yzy", 'x', ModItems.infiniteBucket, 'y', "ingotIron", 'z', Items.ender_pearl);

		if (GanysEnd.enableVoidCrate)
			addShapedRecipe(ModBlocks.void_crate, "xyx", "xzx", "xwx", 'x', "logWood", 'y', "chestWood", 'z', Items.ender_pearl, 'w', Blocks.obsidian);

		if (GanysEnd.enableEnderFurnace)
			addShapedRecipe(ModBlocks.ender_furnace, "xxx", "xyx", "xxx", 'x', new ItemStack(Blocks.end_stone), 'y', "ingotEndium");
	}

	public static void addShapedRecipe(Block block, Object... objects) {
		addShapedRecipe(new ItemStack(block), objects);
	}

	public static void addShapedRecipe(Item item, Object... objects) {
		addShapedRecipe(new ItemStack(item), objects);
	}

	public static void addShapedRecipe(ItemStack stack, Object... objects) {
		GameRegistry.addRecipe(new ShapedOreRecipe(stack, objects));
	}

	public static void addShapelessRecipe(Block block, Object... objects) {
		addShapelessRecipe(new ItemStack(block), objects);
	}

	public static void addShapelessRecipe(Item item, Object... objects) {
		addShapelessRecipe(new ItemStack(item), objects);
	}

	public static void addShapelessRecipe(ItemStack stack, Object... objects) {
		GameRegistry.addRecipe(new ShapelessOreRecipe(stack, objects));
	}
}