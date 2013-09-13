package ganymedes01.ganysend.configuration;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.lib.BlocksID;
import ganymedes01.ganysend.lib.ItemsID;
import ganymedes01.ganysend.lib.Reference;
import ganymedes01.ganysend.lib.Strings;

import java.io.File;
import java.util.logging.Level;

import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.FMLLog;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class ConfigurationHandler {

	public static Configuration configuration;

	public static void init(File configFile) {
		configuration = new Configuration(configFile);

		try {
			configuration.load();

			// Blocks
			BlocksID.ENDER_FLOWER_ID = configuration.getBlock(Strings.ENDER_FLOWER_NAME, BlocksID.ENDER_FLOWER_ID_DEFAULT).getInt(BlocksID.ENDER_FLOWER_ID_DEFAULT);
			BlocksID.ENDSTONE_BRICK_ID = configuration.getBlock(Strings.ENDSTONE_BRICK_NAME, BlocksID.ENDSTONE_BRICK_ID_DEFAULT).getInt(BlocksID.ENDSTONE_BRICK_ID_DEFAULT);
			BlocksID.ENDERPEARL_BLOCK_ID = configuration.getBlock(Strings.ENDERPEARL_BLOCK_NAME, BlocksID.ENDERPEARL_BLOCK_ID_DEFAULT).getInt(BlocksID.ENDERPEARL_BLOCK_ID_DEFAULT);
			BlocksID.ENDERPEARL_BRICK_ID = configuration.getBlock(Strings.ENDERPEARL_BRICK_NAME, BlocksID.ENDERPEARL_BRICK_ID_DEFAULT).getInt(BlocksID.ENDERPEARL_BRICK_ID_DEFAULT);
			BlocksID.ENDSTONE_STAIRS_ID = configuration.getBlock(Strings.ENDSTONE_STAIRS_NAME, BlocksID.ENDSTONE_STAIRS_ID_DEFAULT).getInt(BlocksID.ENDSTONE_STAIRS_ID_DEFAULT);
			BlocksID.ENDERPEARL_BRICK_STAIRS_ID = configuration.getBlock(Strings.ENDERPEARL_BRICK_STAIRS_NAME, BlocksID.ENDERPEARL_BRICK_STAIRS_ID_DEFAULT).getInt(BlocksID.ENDERPEARL_BRICK_STAIRS_ID_DEFAULT);
			BlocksID.ENDER_TOGGLER_ID = configuration.getBlock(Strings.ENDER_TOGGLER_NAME, BlocksID.ENDER_TOGGLER_ID_DEFAULT).getInt(BlocksID.ENDER_TOGGLER_ID_DEFAULT);
			BlocksID.ENDER_TOGGLER_AIR_ID = configuration.getBlock(Strings.ENDER_TOGGLER_AIR_NAME, BlocksID.ENDER_TOGGLER_AIR_ID_DEFAULT).getInt(BlocksID.ENDER_TOGGLER_AIR_ID_DEFAULT);
			BlocksID.BLOCK_SHIFTER_ID = configuration.getBlock(Strings.BLOCK_SHIFTER_NAME, BlocksID.BLOCK_SHIFTER_ID_DEFAULT).getInt(BlocksID.BLOCK_SHIFTER_ID_DEFAULT);
			BlocksID.RAW_ENDIUM_ID = configuration.getBlock(Strings.RAW_ENDIUM_NAME, BlocksID.RAW_ENDIUM_ID_DEFAULT).getInt(BlocksID.RAW_ENDIUM_ID_DEFAULT);
			BlocksID.ENDIUM_BLOCK_ID = configuration.getBlock(Strings.ENDIUM_BLOCK_NAME, BlocksID.ENDIUM_BLOCK_ID_DEFAULT).getInt(BlocksID.ENDIUM_BLOCK_ID_DEFAULT);
			BlocksID.EMULATOR = configuration.getBlock(Strings.EMULATOR_NAME, BlocksID.EMULATOR_ID_DEFAULT).getInt(BlocksID.EMULATOR_ID_DEFAULT);
			BlocksID.BLOCK_NEW_SKULL_ID = configuration.getBlock(Strings.BLOCK_NEW_SKULL_NAME, BlocksID.BLOCK_NEW_SKULL_ID_DEFAULT).getInt(BlocksID.BLOCK_NEW_SKULL_ID_DEFAULT);
			BlocksID.BASIC_FILTERING_HOPPER_ID = configuration.getBlock(Strings.BASIC_FILTERING_HOPPER_NAME, BlocksID.BASIC_FILTERING_HOPPER_ID_DEFAULT).getInt(BlocksID.BASIC_FILTERING_HOPPER_ID_DEFAULT);
			BlocksID.EXCLUSIVE_FILTERING_HOPPER_ID = configuration.getBlock(Strings.EXCLUSIVE_FILTERING_HOPPER_NAME, BlocksID.EXCLUSIVE_FILTERING_HOPPER_ID_DEFAULT).getInt(BlocksID.EXCLUSIVE_FILTERING_HOPPER_ID_DEFAULT);
			BlocksID.SPEEDY_BASIC_FILTERING_HOPPER_ID = configuration.getBlock(Strings.SPEEDY_BASIC_FILTERING_HOPPER_NAME, BlocksID.SPEEDY_BASIC_FILTERING_HOPPER_ID_DEFAULT).getInt(BlocksID.SPEEDY_BASIC_FILTERING_HOPPER_ID_DEFAULT);
			BlocksID.SPEEDY_EXCLUSIVE_FILTERING_HOPPER_ID = configuration.getBlock(Strings.SPEEDY_EXCLUSIVE_FILTERING_HOPPER_NAME, BlocksID.SPEEDY_EXCLUSIVE_FILTERING_HOPPER_ID_DEFAULT).getInt(BlocksID.SPEEDY_EXCLUSIVE_FILTERING_HOPPER_ID_DEFAULT);
			BlocksID.SPEEDY_HOPPER_ID = configuration.getBlock(Strings.SPEEDY_HOPPER_NAME, BlocksID.SPEEDY_HOPPER_ID_DEFAULT).getInt(BlocksID.SPEEDY_HOPPER_ID_DEFAULT);
			BlocksID.ADVANCED_FILTERING_HOPPER_ID = configuration.getBlock(Strings.ADVANCED_FILTERING_HOPPER_NAME, BlocksID.ADVANCED_FILTERING_HOPPER_ID_DEFAULT).getInt(BlocksID.ADVANCED_FILTERING_HOPPER_ID_DEFAULT);
			BlocksID.ADVANCED_EXCLUSIVE_FILTERING_HOPPER_ID = configuration.getBlock(Strings.ADVANCED_EXCLUSIVE_FILTERING_HOPPER_NAME, BlocksID.ADVANCED_EXCLUSIVE_FILTERING_HOPPER_ID_DEFAULT).getInt(BlocksID.ADVANCED_EXCLUSIVE_FILTERING_HOPPER_ID_DEFAULT);
			BlocksID.TIME_MANIPULATOR_ID = configuration.getBlock(Strings.TIME_MANIPULATOR_NAME, BlocksID.TIME_MANIPULATOR_ID_DEFAULT).getInt(BlocksID.TIME_MANIPULATOR_ID_DEFAULT);
			BlocksID.ENTITY_SHIFTER_ID = configuration.getBlock(Strings.ENTITY_SHIFTER_NAME, BlocksID.ENTITY_SHIFTER_ID_DEFAULT).getInt(BlocksID.ENTITY_SHIFTER_ID_DEFAULT);
			BlocksID.PLAYER_INVENTORY_ID = configuration.getBlock(Strings.PLAYER_INVENTORY_NAME, BlocksID.PLAYER_INVENTORY_ID_DEFAULT).getInt(BlocksID.PLAYER_INVENTORY_ID_DEFAULT);

			// Armour
			ItemsID.ENDIUM_HELMET_ID = configuration.getItem(Strings.ENDIUM_HELMET_NAME, ItemsID.ENDIUM_HELMET_ID_DEFAULT).getInt(ItemsID.ENDIUM_HELMET_ID_DEFAULT);
			ItemsID.ENDIUM_CHESTPLATE_ID = configuration.getItem(Strings.ENDIUM_CHESTPLATE_NAME, ItemsID.ENDIUM_CHESTPLATE_ID_DEFAULT).getInt(ItemsID.ENDIUM_CHESTPLATE_ID_DEFAULT);
			ItemsID.ENDIUM_LEGGINGS_ID = configuration.getItem(Strings.ENDIUM_LEGGINGS_NAME, ItemsID.ENDIUM_LEGGINGS_ID_DEFAULT).getInt(ItemsID.ENDIUM_LEGGINGS_ID_DEFAULT);
			ItemsID.ENDIUM_BOOTS_ID = configuration.getItem(Strings.ENDIUM_BOOTS_NAME, ItemsID.ENDIUM_BOOTS_ID_DEFAULT).getInt(ItemsID.ENDIUM_BOOTS_ID_DEFAULT);

			// Items
			ItemsID.ENDER_TAG_ID = configuration.getItem(Strings.ENDER_TAG_NAME, ItemsID.ENDER_TAG_ID_DEFAULT).getInt(ItemsID.ENDER_TAG_ID_DEFAULT);
			ItemsID.ENDIUM_INGOT_ID = configuration.getItem(Strings.ENDIUM_INGOT_NAME, ItemsID.ENDIUM_INGOTS_ID_DEFAULT).getInt(ItemsID.ENDIUM_INGOTS_ID_DEFAULT);
			ItemsID.ENDSTONE_ROD_ID = configuration.getItem(Strings.ENDSTONE_ROD_NAME, ItemsID.ENDSTONE_ROD_ID_DEFAULT).getInt(ItemsID.ENDSTONE_ROD_ID_DEFAULT);
			ItemsID.ENDER_SCYTHE_ID = configuration.getItem(Strings.ENDER_SCYTHE_NAME, ItemsID.ENDER_SCYTHE_ID_DEFAULT).getInt(ItemsID.ENDER_SCYTHE_ID_DEFAULT);
			ItemsID.INFINITE_BUCKET_ID = configuration.getItem(Strings.INFINITE_BUCKET_NAME, ItemsID.INFINITE_BUCKET_ID_DEFAULT).getInt(ItemsID.INFINITE_BUCKET_ID_DEFAULT);
			ItemsID.ITEM_NEW_SKULL_ID = configuration.getItem(Strings.ITEM_NEW_SKULL_NAME, ItemsID.ITEM_NEW_SKULL_ID_DEFAULT).getInt(ItemsID.ITEM_NEW_SKULL_ID_DEFAULT);
			ItemsID.INFUSED_GEM_ID = configuration.getItem(Strings.INFUSED_GEM_NAME, ItemsID.INFUSED_GEM_ID_DEFAULT).getInt(ItemsID.INFUSED_GEM_ID_DEFAULT);

			// Others
			GanysEnd.togglerShouldMakeSound = configuration.get("Others", Strings.TOGGLERS_SHOULD_MAKE_SOUND, true).getBoolean(true);

		} catch (Exception e) {
			FMLLog.log(Level.SEVERE, e, Reference.MOD_NAME + " has had a problem loading its configuration");
			throw new RuntimeException(e);
		} finally {
			configuration.save();
		}

	}
}
