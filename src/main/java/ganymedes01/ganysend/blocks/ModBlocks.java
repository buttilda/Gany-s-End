package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.items.blocks.ItemEndWalls;
import ganymedes01.ganysend.items.blocks.ItemEnderPearlBlock;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class ModBlocks {

	public static final Block enderFlower = new EnderFlower();
	public static final Block endstoneBrick = new EndstoneBrick();
	public static final Block enderpearlBlock = new EnderPearlBlock();
	public static final Block endstoneStairs = new EndStairs(endstoneBrick, 0).setBlockName(Utils.getUnlocalizedName(Strings.ENDSTONE_STAIRS_NAME));
	public static final Block enderpearlStairs = new EndStairs(enderpearlBlock, 1).setBlockName(Utils.getUnlocalizedName(Strings.ENDERPEARL_BRICK_STAIRS_NAME));
	public static final Block enderToggler = new EnderToggler();
	public static final Block enderToggler_air = new EnderTogglerAir();
	public static final Block blockShifter = new BlockShifter();
	public static final Block rawEndium = new RawEndium();
	public static final Block endiumBlock = new EndiumBlock();
	public static final Block emulator = new Emulator();
	public static final Block blockNewSkull = new BlockNewSkull();
	public static final Block basicFilteringHopper = new BasicFilteringHopper();
	public static final Block exclusiveFilteringHopper = new ExclusiveFilteringHopper();
	public static final Block speedyBasicFilteringHopper = new SpeedyBasicFilteringHopper();
	public static final Block speedyExclusiveFilteringHopper = new SpeedyExclusiveFilteringHopper();
	public static final Block speedyHopper = new SpeedyHopper();
	public static final Block advancedFilteringHopper = new AdvancedFilteringHopper();
	public static final Block advancedExclusiveFilteringHopper = new AdvancedExclusiveFilteringHopper();
	public static final Block timeManipulator = new TimeManipulator();
	public static final Block entityShifter = new EntityShifter();
	public static final Block inventoryBinder = new InventoryBinder();
	public static final Block infiniteWaterSource = new InfiniteWaterSource();
	public static final Block endWalls = new EndWalls();
	public static final Block voidCrate = new VoidCrate();
	public static final Block enderFurnace = new EnderFurnace();

	public static void init() {
		registerBlock(enderFlower);
		registerBlock(endstoneBrick);
		registerBlock(enderpearlBlock, ItemEnderPearlBlock.class);
		registerBlock(endstoneStairs);
		registerBlock(enderpearlStairs);
		registerBlock(enderToggler);
		registerBlock(enderToggler_air);
		if (GanysEnd.activateShifters) {
			registerBlock(blockShifter);
			registerBlock(entityShifter);
		}
		registerBlock(rawEndium);
		registerBlock(endiumBlock);
		registerBlock(emulator);
		registerBlock(blockNewSkull);
		registerBlock(basicFilteringHopper);
		registerBlock(exclusiveFilteringHopper);
		registerBlock(speedyBasicFilteringHopper);
		registerBlock(speedyExclusiveFilteringHopper);
		registerBlock(speedyHopper);
		registerBlock(advancedFilteringHopper);
		registerBlock(advancedExclusiveFilteringHopper);
		if (GanysEnd.enableTimeManipulator)
			registerBlock(timeManipulator);
		registerBlock(inventoryBinder);
		registerBlock(infiniteWaterSource);
		registerBlock(endWalls, ItemEndWalls.class);
		registerBlock(voidCrate);
		registerBlock(enderFurnace);
	}

	private static void registerBlock(Block block) {
		GameRegistry.registerBlock(block, block.getUnlocalizedName());
	}

	private static void registerBlock(Block block, Class<? extends ItemBlock> item) {
		GameRegistry.registerBlock(block, item, block.getUnlocalizedName());
	}
}