package ganymedes01.ganysend.configuration;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.integration.Integration;
import ganymedes01.ganysend.integration.ModIntegrator;
import ganymedes01.ganysend.lib.ModIDs;
import ganymedes01.ganysend.lib.Reference;
import ganymedes01.ganysend.lib.Strings;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.FMLLog;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class ConfigurationHandler {

	public static Configuration configuration;

	private static boolean configBoolean(String name, boolean def) {
		return configuration.get("Others", name, def).getBoolean(def);
	}

	private static int configEnch(String ench, int def) {
		int config = configuration.get("Enchantments", ench, def).getInt(def);
		return config > 0 ? config : def;
	}

	private static boolean configIntegrationBoolean(String modID) {
		return configuration.get("Mod Integration", "Integrate " + modID, true).getBoolean(true);
	}

	public static void init(File configFile) {
		configuration = new Configuration(configFile);

		try {
			configuration.load();

			// Enchantments
			ModIDs.IMPERVIOUSNESS_ID = configEnch(Strings.IMPERVIOUSNESS_NAME, 100);

			// Mod Integration
			for (Integration integration : ModIntegrator.modIntegrations)
				integration.setShouldIntegrate(configIntegrationBoolean(integration.getModID()));

			// Others
			GanysEnd.togglerShouldMakeSound = configBoolean(Strings.TOGGLERS_SHOULD_MAKE_SOUND, GanysEnd.togglerShouldMakeSound);
			GanysEnd.shouldDoVersionCheck = configBoolean(Strings.SHOULD_DO_VERSION_CHECK, GanysEnd.shouldDoVersionCheck);
			GanysEnd.activateShifters = configBoolean(Strings.ACTIVATE_SHIFTERS, GanysEnd.activateShifters);
			GanysEnd.enableRandomHeadDrop = configBoolean(Strings.ENABLE_RANDOM_HEAD_DROP, GanysEnd.enableRandomHeadDrop);
			GanysEnd.enableTimeManipulator = configBoolean(Strings.ENABLE_TIME_MANIPULATOR, GanysEnd.enableTimeManipulator);
			GanysEnd.enableEnderBag = configBoolean(Strings.ENABLE_ENDER_BAG, GanysEnd.enableEnderBag);
			GanysEnd.enableRawEndiumRecipe = configBoolean(Strings.ENABLE_RAW_ENDIUM_RECIPE, GanysEnd.enableRawEndiumRecipe);

		} catch (Exception e) {
			FMLLog.severe(Reference.MOD_NAME + " has had a problem loading its configuration");
			throw new RuntimeException(e);
		} finally {
			configuration.save();
		}
	}
}