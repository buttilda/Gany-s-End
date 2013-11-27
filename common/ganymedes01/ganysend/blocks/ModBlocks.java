package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.items.blocks.ItemEndWalls;
import ganymedes01.ganysend.items.blocks.ItemEnderPearlBlock;
import ganymedes01.ganysend.lib.ModIDs;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.block.Block;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class ModBlocks {

	public static Block enderFlower;
	public static Block endstoneBrick;
	public static Block enderpearlBlock;
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
	public static Block inventoryBinder;
	public static Block infiniteWaterSource;
	public static Block endWalls;

	public static void init() {
		enderFlower = new EnderFlower();
		endstoneBrick = new EndstoneBrick();
		enderpearlBlock = new EnderPearlBlock();
		endstoneStairs = new EndStairs(ModIDs.ENDSTONE_STAIRS_ID, endstoneBrick, 0).setUnlocalizedName(Utils.getUnlocalizedName(Strings.ENDSTONE_STAIRS_NAME));
		enderpearlStairs = new EndStairs(ModIDs.ENDERPEARL_BRICK_STAIRS_ID, enderpearlBlock, 1).setUnlocalizedName(Utils.getUnlocalizedName(Strings.ENDERPEARL_BRICK_STAIRS_NAME));
		enderToggler = new EnderToggler();
		enderToggler_air = new EnderTogglerAir();
		blockShifter = new BlockShifter();
		rawEndium = new RawEndium();
		endiumBlock = new EndiumBlock();
		emulator = new Emulator();
		blockNewSkull = new BlockNewSkull();
		basicFilteringHopper = new BasicFilteringHopper();
		exclusiveFilteringHopper = new ExclusiveFilteringHopper();
		speedyBasicFilteringHopper = new SpeedyBasicFilteringHopper();
		speedyExclusiveFilteringHopper = new SpeedyExclusiveFilteringHopper();
		speedyHopper = new SpeedyHopper();
		advancedFilteringHopper = new AdvancedFilteringHopper();
		advancedExclusiveFilteringHopper = new AdvancedExclusiveFilteringHopper();
		timeManipulator = new TimeManipulator();
		entityShifter = new EntityShifter();
		inventoryBinder = new InventoryBinder();
		infiniteWaterSource = new InfiniteWaterSource();
		endWalls = new EndWalls();

		registerNames();
	}

	private static void registerNames() {
		GameRegistry.registerBlock(enderFlower, Strings.ENDER_FLOWER_NAME);
		GameRegistry.registerBlock(endstoneBrick, Strings.ENDSTONE_BRICK_NAME);
		GameRegistry.registerBlock(enderpearlBlock, ItemEnderPearlBlock.class, Strings.ENDERPEARL_BLOCK_NAME);
		GameRegistry.registerBlock(endstoneStairs, Strings.ENDSTONE_STAIRS_NAME);
		GameRegistry.registerBlock(enderpearlStairs, Strings.ENDERPEARL_BRICK_STAIRS_NAME);
		GameRegistry.registerBlock(enderToggler, Strings.ENDER_TOGGLER_NAME);
		GameRegistry.registerBlock(enderToggler_air, Strings.ENDER_TOGGLER_AIR_NAME);
		if (GanysEnd.activateShifters) {
			GameRegistry.registerBlock(blockShifter, Strings.BLOCK_SHIFTER_NAME);
			GameRegistry.registerBlock(entityShifter, Strings.ENTITY_SHIFTER_NAME);
		}
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
		GameRegistry.registerBlock(inventoryBinder, Strings.INVENTORY_BINDER_NAME);
		GameRegistry.registerBlock(infiniteWaterSource, Strings.INFINITE_WATER_SOURCE_NAME);
		GameRegistry.registerBlock(endWalls, ItemEndWalls.class, Strings.END_WALLS_NAME);
	}
}