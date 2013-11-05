package ganymedes01.ganysend.api;

import java.lang.reflect.Field;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import cpw.mods.fml.common.FMLLog;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class GanysEndAPI {

	// BLOCKS
	/*
	 * Here's a list of the blocks that can/should be retrieved by this method
	 * 
	 * enderFlower
	 * endstoneBrick
	 * enderpearlBlock
	 * endstoneStairs
	 * enderpearlStairs
	 * enderToggler
	 * enderToggler_air
	 * blockShifter
	 * rawEndium
	 * endiumBlock
	 * emulator
	 * blockNewSkull
	 * basicFilteringHopper
	 * exclusiveFilteringHopper
	 * speedyBasicFilteringHopper
	 * speedyExclusiveFilteringHopper
	 * speedyHopper
	 * advancedFilteringHopper
	 * advancedExclusiveFilteringHopper
	 * timeManipulator
	 * entityShifter
	 * inventoryBinder
	 * infiniteWaterSource
	 * endWalls
	 * 
	 */
	public static final Block getBlock(String blockName) {
		try {
			Class<?> modBlocks = Class.forName("ganymedes01.ganysend.blocks.ModBlocks");
			Field block = modBlocks.getField(blockName);
			return (Block) block.get(null);
		} catch (Exception e) {
			FMLLog.warning("[ganysend] Problems retrieving block: " + blockName);
			return null;
		}
	}

	// ITEMS
	/*
	 * Here's a list of the items that can/should be retrieved by this method
	 * 
	 * enderTag
	 * endiumIngot
	 * endstoneRod
	 * enderScythe
	 * infiniteBucket
	 * itemNewSkull
	 * infusedGem
	 * endiumPickaxe
	 * endiumAxe
	 * endiumShovel
	 * 
	 * endiumHelmet
	 * endiumChestplate
	 * endiumLeggings
	 * endiumBoots
	 * 
	 */
	public static final Item getItem(String itemName) {
		try {
			Class<?> modBlocks = Class.forName("ganymedes01.ganysend.items.ModItems");
			Field item = modBlocks.getField(itemName);
			return (Item) item.get(null);
		} catch (Exception e) {
			FMLLog.warning("[ganysend] Problems retrieving item: " + itemName);
			return null;
		}
	}
}