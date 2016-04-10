package ganymedes01.ganysend;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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
import ganymedes01.ganysend.blocks.EndstoneBricks;
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
import ganymedes01.ganysend.lib.Reference;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class ModBlocks {

	public static final Block ender_flower = new EnderFlower();
	public static final Block endstone_bricks = new EndstoneBricks();
	public static final Block enderpearl_block = new EnderPearlBlock();
	public static final Block endstone_stairs = new EndStairs(endstone_bricks.getStateFromMeta(0)).setUnlocalizedName(Utils.getUnlocalisedName(Strings.ENDSTONE_STAIRS_NAME));
	public static final Block enderpearl_stairs = new EndStairs(enderpearl_block.getStateFromMeta(1)).setUnlocalizedName(Utils.getUnlocalisedName(Strings.ENDERPEARL_BRICKS_STAIRS_NAME));
	public static final Block ender_toggler = new EnderToggler();
	public static final Block ender_toggler_air = new EnderTogglerAir();
	public static final Block block_shifter = new BlockShifter();
	public static final Block raw_endium = new RawEndium();
	public static final Block endium_block = new EndiumBlock();
	public static final Block emulator = new Emulator();
	public static final Block basic_filtering_hopper = new BasicFilteringHopper();
	public static final Block exclusive_filtering_hopper = new ExclusiveFilteringHopper();
	public static final Block speedy_basic_filtering_hopper = new SpeedyBasicFilteringHopper();
	public static final Block speedy_exclusive_filtering_hopper = new SpeedyExclusiveFilteringHopper();
	public static final Block speedy_hopper = new SpeedyHopper();
	public static final Block advanced_filtering_hopper = new AdvancedFilteringHopper();
	public static final Block advanced_exclusive_filtering_hopper = new AdvancedExclusiveFilteringHopper();
	public static final Block time_manipulator = new TimeManipulator();
	public static final Block entity_shifter = new EntityShifter();
	public static final Block inventory_binder = new InventoryBinder();
	public static final Block infinite_water_source = new InfiniteWaterSource();
	public static final Block endstone_bricks_wall = new EndWalls(endstone_bricks, "endstone_bricks_wall").setHardness(3.0F).setResistance(5.0F);
	public static final Block enderpearl_bricks_wall = new EndWalls(enderpearl_block, "enderpearl_bricks_wall").setHardness(1.5F).setResistance(3.0F);
	public static final Block void_crate = new VoidCrate();
	public static final Block ender_furnace = new EnderFurnace();
	public static final Block creative_speedy_hopper = new CreativeSpeedyHopper();
	public static final Block creative_infinite_fluid_source = new CreativeInfiniteFluidSource();
	public static final Block anchored_ender_chest = new AnchoredEnderChest();

	public static void init() {
		for (Block block : getBlocks())
			registerBlock(block);
	}

	public static void registerRenderers() {
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		for (Block block : getBlocks())
			if (!(block instanceof IConfigurable) || ((IConfigurable) block).isEnabled())
				if (block instanceof ISubBlocksBlock) {
					List<String> models = ((ISubBlocksBlock) block).getModels();
					for (int i = 0; i < models.size(); i++)
						mesher.register(Item.getItemFromBlock(block), i, new ModelResourceLocation(Reference.MOD_ID + ":" + models.get(i), "inventory"));
				} else {
					String name = block.getUnlocalizedName();
					String[] strings = name.split("\\.");
					mesher.register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(Reference.MOD_ID + ":" + strings[strings.length - 1], "inventory"));
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

	private static List<Block> getBlocks() {
		List<Block> blocks = new ArrayList<Block>();
		try {
			for (Field f : ModBlocks.class.getDeclaredFields()) {
				Object obj = f.get(null);
				if (obj instanceof Block)
					blocks.add((Block) obj);
				else if (obj instanceof Block[])
					for (Block block : (Block[]) obj)
						if (block != null)
							blocks.add(block);
			}
			return blocks;
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public static interface ISubBlocksBlock {

		Class<? extends ItemBlock> getItemBlockClass();

		List<String> getModels();
	}
}