package ganymedes01.ganysend.recipes;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.ModBlocks;
import ganymedes01.ganysend.ModItems;
import ganymedes01.ganysend.lib.SkullTypes;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
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
		registerBlockRecipes();
		registerItemRecipes();
		registerArmourRecipes();
	}

	public static void registerOreDictionary() {
		OreDictionary.registerOre("chestWood", Blocks.chest);

		if (GanysEnd.enableEndium) {
			OreDictionary.registerOre("oreEndium", ModBlocks.rawEndium);
			OreDictionary.registerOre("ingotEndium", new ItemStack(ModItems.endiumIngot));
			OreDictionary.registerOre("nuggetEndium", new ItemStack(ModItems.endiumIngot, 1, 1));
			OreDictionary.registerOre("blockEndium", ModBlocks.endiumBlock);
		}

		if (GanysEnd.enableDecorativeBlocks)
			OreDictionary.registerOre("blockEnderPearl", new ItemStack(ModBlocks.enderpearlBlock, 1, OreDictionary.WILDCARD_VALUE));

		if (GanysEnd.enableEnderFlower)
			OreDictionary.registerOre("flowerEnder", ModBlocks.enderFlower);

		if (GanysEnd.enableTimeManipulator) {
			OreDictionary.registerOre("nightGemMaterial", new ItemStack(Blocks.brown_mushroom));
			OreDictionary.registerOre("nightGemMaterial", new ItemStack(Blocks.red_mushroom));
			OreDictionary.registerOre("nightGemMaterial", new ItemStack(Items.rotten_flesh));
			OreDictionary.registerOre("gemNight", new ItemStack(ModItems.infusedGem));
			OreDictionary.registerOre("gemDay", new ItemStack(ModItems.infusedGem, 1, 1));
		}

		if (GanysEnd.enableEnderBag || GanysEnd.enableAnchoredEnderChest)
			OreDictionary.registerOre("chestEnder", Blocks.ender_chest);

		if (GanysEnd.enableSkulls) {
			OreDictionary.registerOre("itemSkull", new ItemStack(ModItems.skull, 1, OreDictionary.WILDCARD_VALUE));
			OreDictionary.registerOre("itemSkull", new ItemStack(Items.skull, 1, OreDictionary.WILDCARD_VALUE));
			for (SkullTypes type : SkullTypes.values())
				OreDictionary.registerOre("skull" + type.name().substring(0, 1).toUpperCase() + type.name().substring(1), new ItemStack(ModItems.skull, 1, type.ordinal()));
			OreDictionary.registerOre("skullSkeleton", new ItemStack(Items.skull, 1, 0));
			OreDictionary.registerOre("skullWitherSkeleton", new ItemStack(Items.skull, 1, 1));
			OreDictionary.registerOre("skullZombie", new ItemStack(Items.skull, 1, 2));
			OreDictionary.registerOre("skullPlayer", new ItemStack(Items.skull, 1, 3));
			OreDictionary.registerOre("skullCreeper", new ItemStack(Items.skull, 1, 4));
		}

		if (GanysEnd.enableAnchoredEnderChest)
			OreDictionary.registerOre("transdimBlock", new ItemStack(ModBlocks.anchoredEnderChest));
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
			GameRegistry.addSmelting(ModBlocks.rawEndium, new ItemStack(ModItems.endiumIngot), 1F);
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
			// addShapedRecipe(ModItems.endiumBucket, "x x", " x ", 'x', "ingotEndium");
		}

		if (GanysEnd.enableEnderBag)
			addShapedRecipe(ModItems.enderBag, "zwz", "wyw", "xxx", 'x', Items.ender_pearl, 'y', "chestEnder", 'z', Items.string, 'w', Items.leather);

		if (GanysEnd.enableAnchoredEnderChest)
			addShapedRecipe(ModBlocks.anchoredEnderChest, "xxx", "xyx", "xzx", 'x', "ingotGold", 'y', "chestEnder", 'z', new ItemStack(Blocks.anvil));

		if (GanysEnd.enableEnderFlower)
			addShapedRecipe(Items.ender_pearl, "xxx", "xxx", "x x", 'x', "flowerEnder");

		if (GanysEnd.enableDecorativeBlocks)
			addShapelessRecipe(new ItemStack(Items.ender_pearl, 9), "blockEnderPearl");
	}

	private static void registerBlockRecipes() {
		if (GanysEnd.enableEndium)
			addShapedRecipe(ModBlocks.endiumBlock, "xxx", "xxx", "xxx", 'x', "ingotEndium");

		if (GanysEnd.enableDecorativeBlocks) {
			addShapedRecipe(new ItemStack(ModBlocks.endstoneBrick, 4), "xx", "xx", 'x', Blocks.end_stone);
			addShapedRecipe(new ItemStack(ModBlocks.enderpearlBlock, 1, 0), "xxx", "xxx", "xxx", 'x', Items.ender_pearl);
			addShapedRecipe(new ItemStack(ModBlocks.enderpearlBlock, 4, 1), "xx", "xx", 'x', new ItemStack(ModBlocks.enderpearlBlock, 1, 0));
			addShapedRecipe(new ItemStack(ModBlocks.endstoneStairs, 4), "x  ", "xx ", "xxx", 'x', ModBlocks.endstoneBrick);
			addShapedRecipe(new ItemStack(ModBlocks.enderpearlStairs, 4), "x  ", "xx ", "xxx", 'x', new ItemStack(ModBlocks.enderpearlBlock, 1, 1));
			addShapedRecipe(new ItemStack(ModBlocks.endWalls, 6, 0), "xxx", "xxx", 'x', ModBlocks.endstoneBrick);
			addShapedRecipe(new ItemStack(ModBlocks.endWalls, 6, 1), "xxx", "xxx", 'x', new ItemStack(ModBlocks.enderpearlBlock, 1, 1));
		}

		if (GanysEnd.enableEnderToggler)
			addShapedRecipe(ModBlocks.enderToggler, "wwz", "zyz", "zww", 'y', Items.ender_pearl, 'z', "blockGlass", 'w', "dustRedstone");

		if (GanysEnd.enableShifters) {
			addShapedRecipe(ModBlocks.blockShifter, "yzy", "wxw", "ywy", 'x', new ItemStack(ModBlocks.enderpearlBlock, 1, 0), 'y', "ingotGold", 'z', "dustRedstone", 'w', "blockGlass");
			addShapedRecipe(ModBlocks.entityShifter, "yzy", "wxw", "ywy", 'x', new ItemStack(ModBlocks.enderpearlBlock, 1, 1), 'y', "ingotGold", 'z', "dustRedstone", 'w', "blockGlass");
		}

		if (GanysEnd.enableEmulator) {
			addShapedRecipe(ModBlocks.emulator, "xyx", "yyy", "xyx", 'x', Items.ender_pearl, 'y', Blocks.hardened_clay);
			addShapedRecipe(ModBlocks.emulator, "xyx", "yyy", "xyx", 'x', Items.ender_pearl, 'y', Blocks.stained_hardened_clay);
			addShapedRecipe(new ItemStack(ModBlocks.emulator, 8), "xyx", "yyy", "xyx", 'x', "flowerEnder", 'y', Blocks.hardened_clay);
			addShapedRecipe(new ItemStack(ModBlocks.emulator, 8), "xyx", "yyy", "xyx", 'x', "flowerEnder", 'y', Blocks.stained_hardened_clay);
		}

		if (GanysEnd.enableHoppers) {
			addShapedRecipe(ModBlocks.basicFilteringHopper, " z ", "yxy", 'x', Blocks.hopper, 'y', Items.ender_pearl, 'z', "ingotGold");
			addShapedRecipe(ModBlocks.exclusiveFilteringHopper, "yxy", 'x', ModBlocks.basicFilteringHopper, 'y', "dustRedstone");
			addShapelessRecipe(ModBlocks.basicFilteringHopper, new ItemStack(ModBlocks.exclusiveFilteringHopper), "ingotGold");
			addShapedRecipe(ModBlocks.speedyBasicFilteringHopper, "yyy", "yxy", "yyy", 'x', ModBlocks.basicFilteringHopper, 'y', "gemLapis");
			addShapedRecipe(ModBlocks.speedyExclusiveFilteringHopper, "yyy", "yxy", "yyy", 'x', ModBlocks.exclusiveFilteringHopper, 'y', "gemLapis");
			addShapedRecipe(ModBlocks.speedyHopper, "yyy", "yxy", "yyy", 'x', Blocks.hopper, 'y', "gemLapis");
			addShapedRecipe(ModBlocks.speedyBasicFilteringHopper, " z ", "yxy", 'x', ModBlocks.speedyHopper, 'y', Items.ender_pearl, 'z', "ingotGold");
			addShapedRecipe(ModBlocks.speedyExclusiveFilteringHopper, "yxy", 'x', ModBlocks.speedyBasicFilteringHopper, 'y', "dustRedstone");
			addShapelessRecipe(ModBlocks.speedyBasicFilteringHopper, new ItemStack(ModBlocks.speedyExclusiveFilteringHopper), "ingotGold");
			addShapedRecipe(ModBlocks.advancedFilteringHopper, " z ", "yxy", " z ", 'x', ModBlocks.speedyBasicFilteringHopper, 'y', "gemDiamond", 'z', "blockGlass");
			addShapedRecipe(ModBlocks.advancedExclusiveFilteringHopper, " z ", "yxy", " z ", 'x', ModBlocks.speedyExclusiveFilteringHopper, 'y', "gemDiamond", 'z', "blockGlass");
		}

		if (GanysEnd.enableTimeManipulator)
			addShapedRecipe(ModBlocks.timeManipulator, "zyz", "yxy", "zyz", 'x', Blocks.dragon_egg, 'y', new ItemStack(Blocks.planks, 1, 1), 'z', "blockGold");

		if (GanysEnd.enableInventoryBinder)
			addShapedRecipe(ModBlocks.inventoryBinder, "xxx", "yzy", "xxx", 'x', Blocks.end_stone, 'y', Items.ender_pearl, 'z', "itemSkull");

		if (GanysEnd.enableInfiniteWaterSource)
			addShapedRecipe(ModBlocks.infiniteWaterSource, "yzy", "zxz", "yzy", 'x', ModItems.infiniteBucket, 'y', "ingotIron", 'z', Items.ender_pearl);

		if (GanysEnd.enableVoidCrate)
			addShapedRecipe(ModBlocks.voidCrate, "xyx", "xzx", "xwx", 'x', "logWood", 'y', "chestWood", 'z', Items.ender_pearl, 'w', Blocks.obsidian);

		if (GanysEnd.enableEnderFurnace)
			addShapedRecipe(ModBlocks.enderFurnace, "xxx", "xyx", "xxx", 'x', new ItemStack(Blocks.end_stone), 'y', Items.ender_eye);
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