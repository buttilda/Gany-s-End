package ganymedes01.ganysend.configuration;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.integration.Integration;
import ganymedes01.ganysend.integration.ModIntegrator;
import ganymedes01.ganysend.lib.ModIDs;
import ganymedes01.ganysend.lib.Reference;
import ganymedes01.ganysend.lib.Strings;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class ConfigurationHandler {

	public static ConfigurationHandler INSTANCE = new ConfigurationHandler();
	public Configuration configFile;
	public String[] usedCategories = { Configuration.CATEGORY_GENERAL, "enchantments", "mod integration", "heads" };

	private boolean configBoolean(String name, boolean requiresRestart, boolean def) {
		return configFile.get(Configuration.CATEGORY_GENERAL, name, def).setRequiresMcRestart(requiresRestart).getBoolean(def);
	}

	private int configEnch(String ench, int def) {
		int config = configFile.get("Enchantments", ench, def).setRequiresMcRestart(true).getInt(def);
		return config > 0 ? config : def;
	}

	private boolean configIntegrationBoolean(String modID) {
		return configFile.get("Mod Integration", "Integrate " + modID, true).setRequiresMcRestart(true).getBoolean(true);
	}

	public void init(File file) {
		configFile = new Configuration(file);

		syncConfigs();
	}

	private void syncConfigs() {
		// Enchantments
		ModIDs.IMPERVIOUSNESS_ID = configEnch(Strings.IMPERVIOUSNESS_NAME, 100);

		// Mod Integration
		for (Integration integration : ModIntegrator.modIntegrations)
			integration.setShouldIntegrate(configIntegrationBoolean(integration.getModID()));

		// Others
		GanysEnd.togglerShouldMakeSound = configBoolean("togglersShouldMakeSound", false, GanysEnd.togglerShouldMakeSound);
		GanysEnd.shouldDoVersionCheck = configBoolean("shouldDoVersionCheck", true, GanysEnd.shouldDoVersionCheck);
		GanysEnd.activateShifters = configBoolean("activateShifters", true, GanysEnd.activateShifters);
		GanysEnd.enableRandomHeadDrop = configBoolean("enableRandomHeadDrop", false, GanysEnd.enableRandomHeadDrop);
		GanysEnd.enableTimeManipulator = configBoolean("enableTimeManipulator", true, GanysEnd.enableTimeManipulator);
		GanysEnd.enableEnderBag = configBoolean("enableEnderBag", true, GanysEnd.enableEnderBag);
		GanysEnd.enableRawEndiumRecipe = configBoolean("enableRawEndiumRecipe", true, GanysEnd.enableRawEndiumRecipe);
		GanysEnd.enableVanillaHeadsDrop = configBoolean("enableVanillaHeadsDrop", false, GanysEnd.enableVanillaHeadsDrop);
		GanysEnd.others = configFile.get("heads", "others", GanysEnd.others).setRequiresMcRestart(true).getStringList();
		GanysEnd.modders = configFile.get("heads", "modders", GanysEnd.modders).setRequiresMcRestart(true).getStringList();
		GanysEnd.youtubers = configFile.get("heads", "youtubers", GanysEnd.youtubers).setRequiresMcRestart(true).getStringList();
		GanysEnd.mojang = configFile.get("heads", "mojang", GanysEnd.mojang).setRequiresMcRestart(true).getStringList();
		GanysEnd.mindCrack = configFile.get("heads", "mindCrack", GanysEnd.mindCrack).setRequiresMcRestart(true).getStringList();
		GanysEnd.forgeCraft = configFile.get("heads", "forgeCraft", GanysEnd.forgeCraft).setRequiresMcRestart(true).getStringList();

		if (configFile.hasChanged())
			configFile.save();
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
		if (Reference.MOD_ID.equals(eventArgs.modID))
			syncConfigs();
	}
}