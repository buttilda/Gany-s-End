package ganymedes01.ganysend.configuration;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.IdGenerator;
import ganymedes01.ganysend.lib.ModIDs;
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
	private static IdGenerator idGen = new IdGenerator(Reference.ITEM_ID_BASE, Reference.BLOCK_ID_BASE);

	private static int configBlock(String name) {
		return configuration.getBlock(name, idGen.getNextBlockID()).getInt(idGen.getLastBlockID());
	}

	private static int configItem(String name) {
		return configuration.getItem(name, idGen.getNextItemID()).getInt(idGen.getLastItemID());
	}

	public static void init(File configFile) {
		configuration = new Configuration(configFile);

		try {
			configuration.load();

			// Blocks
			ModIDs.ENDER_FLOWER_ID = configBlock(Strings.ENDER_FLOWER_NAME);
			ModIDs.ENDSTONE_BRICK_ID = configBlock(Strings.ENDSTONE_BRICK_NAME);
			ModIDs.ENDERPEARL_BLOCK_ID = configBlock(Strings.ENDERPEARL_BLOCK_NAME);
			ModIDs.ENDERPEARL_BRICK_ID = configBlock(Strings.ENDERPEARL_BRICK_NAME);
			ModIDs.ENDSTONE_STAIRS_ID = configBlock(Strings.ENDSTONE_STAIRS_NAME);
			ModIDs.ENDERPEARL_BRICK_STAIRS_ID = configBlock(Strings.ENDERPEARL_BRICK_STAIRS_NAME);
			ModIDs.ENDER_TOGGLER_ID = configBlock(Strings.ENDER_TOGGLER_NAME);
			ModIDs.ENDER_TOGGLER_AIR_ID = configBlock(Strings.ENDER_TOGGLER_AIR_NAME);
			ModIDs.BLOCK_SHIFTER_ID = configBlock(Strings.BLOCK_SHIFTER_NAME);
			ModIDs.RAW_ENDIUM_ID = configBlock(Strings.RAW_ENDIUM_NAME);
			ModIDs.ENDIUM_BLOCK_ID = configBlock(Strings.ENDIUM_BLOCK_NAME);
			ModIDs.EMULATOR = configBlock(Strings.EMULATOR_NAME);
			ModIDs.BLOCK_NEW_SKULL_ID = configBlock(Strings.BLOCK_NEW_SKULL_NAME);
			ModIDs.BASIC_FILTERING_HOPPER_ID = configBlock(Strings.BASIC_FILTERING_HOPPER_NAME);
			ModIDs.EXCLUSIVE_FILTERING_HOPPER_ID = configBlock(Strings.EXCLUSIVE_FILTERING_HOPPER_NAME);
			ModIDs.SPEEDY_BASIC_FILTERING_HOPPER_ID = configBlock(Strings.SPEEDY_BASIC_FILTERING_HOPPER_NAME);
			ModIDs.SPEEDY_EXCLUSIVE_FILTERING_HOPPER_ID = configBlock(Strings.SPEEDY_EXCLUSIVE_FILTERING_HOPPER_NAME);
			ModIDs.SPEEDY_HOPPER_ID = configBlock(Strings.SPEEDY_HOPPER_NAME);
			ModIDs.ADVANCED_FILTERING_HOPPER_ID = configBlock(Strings.ADVANCED_FILTERING_HOPPER_NAME);
			ModIDs.ADVANCED_EXCLUSIVE_FILTERING_HOPPER_ID = configBlock(Strings.ADVANCED_EXCLUSIVE_FILTERING_HOPPER_NAME);
			ModIDs.TIME_MANIPULATOR_ID = configBlock(Strings.TIME_MANIPULATOR_NAME);
			ModIDs.ENTITY_SHIFTER_ID = configBlock(Strings.ENTITY_SHIFTER_NAME);
			ModIDs.INVENTORY_BINDER_ID = configBlock(Strings.INVENTORY_BINDER_NAME);
			ModIDs.INFINITE_WATER_SOURCE_ID = configBlock(Strings.INFINITE_WATER_SOURCE_NAME);
			ModIDs.END_WALLS_ID = configBlock(Strings.END_WALLS_NAME);
			ModIDs.ENERGY_PORTAL_ID = configBlock(Strings.ENERGY_PORTAL_NAME);

			// Armour
			ModIDs.ENDIUM_HELMET_ID = configItem(Strings.ENDIUM_HELMET_NAME);
			ModIDs.ENDIUM_CHESTPLATE_ID = configItem(Strings.ENDIUM_CHESTPLATE_NAME);
			ModIDs.ENDIUM_LEGGINGS_ID = configItem(Strings.ENDIUM_LEGGINGS_NAME);
			ModIDs.ENDIUM_BOOTS_ID = configItem(Strings.ENDIUM_BOOTS_NAME);

			// Items
			ModIDs.ENDER_TAG_ID = configItem(Strings.ENDER_TAG_NAME);
			ModIDs.ENDIUM_INGOT_ID = configItem(Strings.ENDIUM_INGOT_NAME);
			ModIDs.ENDSTONE_ROD_ID = configItem(Strings.ENDSTONE_ROD_NAME);
			ModIDs.ENDER_SCYTHE_ID = configItem(Strings.ENDER_SCYTHE_NAME);
			ModIDs.INFINITE_BUCKET_ID = configItem(Strings.INFINITE_BUCKET_NAME);
			ModIDs.ITEM_NEW_SKULL_ID = configItem(Strings.ITEM_NEW_SKULL_NAME);
			ModIDs.INFUSED_GEM_ID = configItem(Strings.INFUSED_GEM_NAME);
			ModIDs.ENDIUM_PICKAXE_ID = configItem(Strings.ENDIUM_PICKAXE_NAME);
			ModIDs.ENDIUM_AXE_ID = configItem(Strings.ENDIUM_AXE_NAME);
			ModIDs.ENDIUM_SHOVEL_ID = configItem(Strings.ENDIUM_SHOVEL_NAME);

			// Others
			GanysEnd.togglerShouldMakeSound = configuration.get("Others", Strings.TOGGLERS_SHOULD_MAKE_SOUND, true).getBoolean(true);
			GanysEnd.shouldDoVersionCheck = configuration.get("Others", Strings.SHOULD_DO_VERSION_CHECK, true).getBoolean(true);
			GanysEnd.activateShifters = configuration.get("Others", Strings.ACTIVATE_SHIFTERS, true).getBoolean(true);
			GanysEnd.activateEnergyPortal = configuration.get("Others", Strings.ACTIVATE_ENERGY_PORTAL, true).getBoolean(true);

		} catch (Exception e) {
			FMLLog.log(Level.SEVERE, e, Reference.MOD_NAME + " has had a problem loading its configuration");
			throw new RuntimeException(e);
		} finally {
			configuration.save();
		}
	}
}