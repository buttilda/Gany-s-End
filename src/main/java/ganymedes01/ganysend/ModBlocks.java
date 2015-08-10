package ganymedes01.ganysend;

import ganymedes01.ganysend.blocks.AdvancedExclusiveFilteringHopper;
import ganymedes01.ganysend.blocks.AdvancedFilteringHopper;
import ganymedes01.ganysend.blocks.AnchoredEnderChest;
import ganymedes01.ganysend.blocks.BasicFilteringHopper;
import ganymedes01.ganysend.blocks.BlockShifter;
import ganymedes01.ganysend.blocks.CreativeInfiniteFluidSource;
import ganymedes01.ganysend.blocks.CreativeSpeedyHopper;
import ganymedes01.ganysend.blocks.Emulator;
import ganymedes01.ganysend.blocks.EndStairs;
import ganymedes01.ganysend.blocks.EndWalls;
import ganymedes01.ganysend.blocks.EnderFlower;
import ganymedes01.ganysend.blocks.EnderFurnace;
import ganymedes01.ganysend.blocks.EnderPearlBlock;
import ganymedes01.ganysend.blocks.EnderToggler;
import ganymedes01.ganysend.blocks.EnderTogglerAir;
import ganymedes01.ganysend.blocks.EndiumBlock;
import ganymedes01.ganysend.blocks.EndstoneBrick;
import ganymedes01.ganysend.blocks.EntityShifter;
import ganymedes01.ganysend.blocks.ExclusiveFilteringHopper;
import ganymedes01.ganysend.blocks.InfiniteWaterSource;
import ganymedes01.ganysend.blocks.InventoryBinder;
import ganymedes01.ganysend.blocks.RawEndium;
import ganymedes01.ganysend.blocks.SpeedyBasicFilteringHopper;
import ganymedes01.ganysend.blocks.SpeedyExclusiveFilteringHopper;
import ganymedes01.ganysend.blocks.SpeedyHopper;
import ganymedes01.ganysend.blocks.TimeManipulator;
import ganymedes01.ganysend.blocks.VoidCrate;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;

import java.lang.reflect.Field;

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
	public static final Block endstoneStairs = new EndStairs(endstoneBrick, 0).setBlockName(Utils.getUnlocalisedName(Strings.ENDSTONE_STAIRS_NAME));
	public static final Block enderpearlStairs = new EndStairs(enderpearlBlock, 1).setBlockName(Utils.getUnlocalisedName(Strings.ENDERPEARL_BRICK_STAIRS_NAME));
	public static final Block enderToggler = new EnderToggler();
	public static final Block enderToggler_air = new EnderTogglerAir();
	public static final Block blockShifter = new BlockShifter();
	public static final Block rawEndium = new RawEndium();
	public static final Block endiumBlock = new EndiumBlock();
	public static final Block emulator = new Emulator();
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
	public static final Block creativeSpeedyHopper = new CreativeSpeedyHopper();
	public static final Block creativeInfiniteFluidSource = new CreativeInfiniteFluidSource();
	public static final Block anchoredEnderChest = new AnchoredEnderChest();

	public static void init() {
		try {
			for (Field f : ModBlocks.class.getDeclaredFields()) {
				Object obj = f.get(null);
				if (obj instanceof Block)
					registerBlock((Block) obj);
				else if (obj instanceof Block[])
					for (Block block : (Block[]) obj)
						if (block != null)
							registerBlock(block);
			}
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	private static void registerBlock(Block block) {
		if (!(block instanceof IConfigurable) || ((IConfigurable) block).isEnabled()) {
			String name = block.getUnlocalizedName();
			String[] strings = name.split("\\.");

			if (block instanceof ISubBlocksBlock)
				GameRegistry.registerBlock(block, ((ISubBlocksBlock) block).getItemBlockClass(), strings[strings.length - 1]);
			else
				GameRegistry.registerBlock(block, strings[strings.length - 1]);
		}
	}

	public static interface ISubBlocksBlock {

		Class<? extends ItemBlock> getItemBlockClass();
	}
}