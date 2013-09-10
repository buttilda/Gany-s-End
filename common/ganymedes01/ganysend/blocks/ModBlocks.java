package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.BlocksID;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.block.Block;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks {

	public static Block enderFlower;
	public static Block endstoneBrick;
	public static Block enderpearlBlock;
	public static Block enderpearlBrick;
	public static Block endstoneStairs;
	public static Block enderpearlStairs;
	public static Block enderToggler;
	public static Block enderToggler_air;
	public static Block blockShifter;
	public static Block rawEndium;
	public static Block endiumBlock;
	public static Block emulator;
	public static Block blockNewSkull;
	public static Block basicFilteringHopper;
	public static Block exclusiveFilteringHopper;
	public static Block speedyBasicFilteringHopper;
	public static Block speedyExclusiveFilteringHopper;
	public static Block speedyHopper;
	public static Block advancedFilteringHopper;
	public static Block advancedExclusiveFilteringHopper;
	public static Block timeManipulator;
	public static Block entityShifter;

	public static void init() {
		enderFlower = new EnderFlower(BlocksID.ENDER_FLOWER_ID);
		endstoneBrick = new EndstoneBrick(BlocksID.ENDSTONE_BRICK_ID);
		enderpearlBlock = new EnderPearlBlock(BlocksID.ENDERPEARL_BLOCK_ID).setUnlocalizedName(Utils.getUnlocalizedName(Strings.ENDERPEARL_BLOCK_NAME)).setTextureName(Utils.getBlockTexture(Strings.ENDERPEARL_BLOCK_NAME, false));
		enderpearlBrick = new EnderPearlBlock(BlocksID.ENDERPEARL_BRICK_ID).setUnlocalizedName(Utils.getUnlocalizedName(Strings.ENDERPEARL_BRICK_NAME)).setTextureName(Utils.getBlockTexture(Strings.ENDERPEARL_BRICK_NAME, false));
		endstoneStairs = new EndStairs(BlocksID.ENDSTONE_STAIRS_ID, endstoneBrick).setUnlocalizedName(Utils.getUnlocalizedName(Strings.ENDSTONE_STAIRS_NAME));
		enderpearlStairs = new EndStairs(BlocksID.ENDERPEARL_BRICK_STAIRS_ID, enderpearlBrick).setUnlocalizedName(Utils.getUnlocalizedName(Strings.ENDERPEARL_BRICK_STAIRS_NAME));
		enderToggler = new EnderToggler(BlocksID.ENDER_TOGGLER_ID);
		enderToggler_air = new EnderTogglerAir(BlocksID.ENDER_TOGGLER_AIR_ID, GanysEnd.togglerShouldMakeSound);
		blockShifter = new BlockShifter(BlocksID.BLOCK_SHIFTER_ID);
		rawEndium = new RawEndium(BlocksID.RAW_ENDIUM_ID);
		endiumBlock = new EndiumBlock(BlocksID.ENDIUM_BLOCK_ID);
		emulator = new Emulator(BlocksID.EMULATOR);
		blockNewSkull = new BlockNewSkull(BlocksID.BLOCK_NEW_SKULL_ID);
		basicFilteringHopper = new BasicFilteringHopper(BlocksID.BASIC_FILTERING_HOPPER_ID);
		exclusiveFilteringHopper = new ExclusiveFilteringHopper(BlocksID.EXCLUSIVE_FILTERING_HOPPER_ID);
		speedyBasicFilteringHopper = new SpeedyBasicFilteringHopper(BlocksID.SPEEDY_BASIC_FILTERING_HOPPER_ID);
		speedyExclusiveFilteringHopper = new SpeedyExclusiveFilteringHopper(BlocksID.SPEEDY_EXCLUSIVE_FILTERING_HOPPER_ID);
		speedyHopper = new SpeedyHopper(BlocksID.SPEEDY_HOPPER_ID);
		advancedFilteringHopper = new AdvancedFilteringHopper(BlocksID.ADVANCED_FILTERING_HOPPER_ID);
		advancedExclusiveFilteringHopper = new AdvancedExclusiveFilteringHopper(BlocksID.ADVANCED_EXCLUSIVE_FILTERING_HOPPER_ID);
		timeManipulator = new TimeManipulator(BlocksID.TIME_MANIPULATOR_ID);
		entityShifter = new EntityShifter(BlocksID.ENTITY_SHIFTER_ID);

		registerNames();
	}

	private static void registerNames() {
		GameRegistry.registerBlock(enderFlower, Strings.ENDER_FLOWER_NAME);
		GameRegistry.registerBlock(endstoneBrick, Strings.ENDSTONE_BRICK_NAME);
		GameRegistry.registerBlock(enderpearlBlock, Strings.ENDERPEARL_BLOCK_NAME);
		GameRegistry.registerBlock(enderpearlBrick, Strings.ENDERPEARL_BRICK_NAME);
		GameRegistry.registerBlock(endstoneStairs, Strings.ENDSTONE_STAIRS_NAME);
		GameRegistry.registerBlock(enderpearlStairs, Strings.ENDERPEARL_BRICK_STAIRS_NAME);
		GameRegistry.registerBlock(enderToggler, Strings.ENDER_TOGGLER_NAME);
		GameRegistry.registerBlock(enderToggler_air, Strings.ENDER_TOGGLER_AIR_NAME);
		GameRegistry.registerBlock(blockShifter, Strings.BLOCK_SHIFTER_NAME);
		GameRegistry.registerBlock(rawEndium, Strings.RAW_ENDIUM_NAME);
		GameRegistry.registerBlock(endiumBlock, Strings.ENDIUM_BLOCK_NAME);
		GameRegistry.registerBlock(emulator, Strings.EMULATOR_NAME);
		GameRegistry.registerBlock(blockNewSkull, Strings.BLOCK_NEW_SKULL_NAME);
		GameRegistry.registerBlock(basicFilteringHopper, Strings.BASIC_FILTERING_HOPPER_NAME);
		GameRegistry.registerBlock(exclusiveFilteringHopper, Strings.EXCLUSIVE_FILTERING_HOPPER_NAME);
		GameRegistry.registerBlock(speedyBasicFilteringHopper, Strings.SPEEDY_BASIC_FILTERING_HOPPER_NAME);
		GameRegistry.registerBlock(speedyExclusiveFilteringHopper, Strings.SPEEDY_EXCLUSIVE_FILTERING_HOPPER_NAME);
		GameRegistry.registerBlock(speedyHopper, Strings.SPEEDY_HOPPER_NAME);
		GameRegistry.registerBlock(advancedFilteringHopper, Strings.ADVANCED_FILTERING_HOPPER_NAME);
		GameRegistry.registerBlock(advancedExclusiveFilteringHopper, Strings.ADVANCED_EXCLUSIVE_FILTERING_HOPPER_NAME);
		GameRegistry.registerBlock(timeManipulator, Strings.TIME_MANIPULATOR_NAME);
		GameRegistry.registerBlock(entityShifter, Strings.ENTITY_SHIFTER_NAME);
	}
}
