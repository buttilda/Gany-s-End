package ganymedes01.ganysend.configuration;

import java.io.File;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.integration.Integration;
import ganymedes01.ganysend.integration.ModIntegrator;
import ganymedes01.ganysend.lib.ModIDs;
import ganymedes01.ganysend.lib.Reference;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.recipes.RecipeRegistry;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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

	private File fixFile(File file, String name, String extension) {
		File parent = file.getParentFile();
		return new File(parent, File.separator + Reference.MASTER + File.separator + name + extension);
	}

	public void init(FMLPreInitializationEvent event) {
		configFile = new Configuration(new File(event.getModConfigurationDirectory().getAbsolutePath() + File.separator + Reference.MASTER + File.separator + Reference.MOD_ID + ".cfg"));

		File recipes = fixFile(event.getSuggestedConfigurationFile(), "Recipes", "");
		recipes.mkdirs();
		RecipeRegistry.baseFile = recipes;

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
		GanysEnd.enableShifters = configBoolean("activateShifters", true, GanysEnd.enableShifters);
		GanysEnd.enableRandomHeadDrop = configBoolean("enableRandomHeadDrop", false, GanysEnd.enableRandomHeadDrop);
		GanysEnd.enableTimeManipulator = configBoolean("enableTimeManipulator", true, GanysEnd.enableTimeManipulator);
		GanysEnd.enableEnderBag = configBoolean("enableEnderBag", true, GanysEnd.enableEnderBag);
		GanysEnd.enableVanillaHeadsDrop = configBoolean("enableVanillaHeadsDrop", false, GanysEnd.enableVanillaHeadsDrop);
		GanysEnd.enableEndiumGen = configBoolean("enableEndiumWorldGen", true, GanysEnd.enableEndiumGen);
		GanysEnd.enableEnderFlower = configBoolean("enableEnderFlower", true, GanysEnd.enableEnderFlower);
		GanysEnd.enableEndiumArmour = configBoolean("enableEndiumArmour", true, GanysEnd.enableEndiumArmour);
		GanysEnd.enableEndiumTools = configBoolean("enableEndiumTools", true, GanysEnd.enableEndiumTools);
		GanysEnd.enableHoppers = configBoolean("enableHoppers", true, GanysEnd.enableHoppers);
		GanysEnd.enableAnchoredEnderChest = configBoolean("enableAnchoredEnderChest", true, GanysEnd.enableAnchoredEnderChest);
		GanysEnd.enableDecorativeBlocks = configBoolean("enableDecorativeBlocks", true, GanysEnd.enableDecorativeBlocks);
		GanysEnd.enableVoidCrate = configBoolean("enableVoidCrate", true, GanysEnd.enableVoidCrate);
		GanysEnd.enableInfiniteWaterSource = configBoolean("enableInfiniteWaterSource", true, GanysEnd.enableInfiniteWaterSource);
		GanysEnd.enableInventoryBinder = configBoolean("enableInventoryBinder", true, GanysEnd.enableInventoryBinder);
		GanysEnd.enableEnderFurnace = configBoolean("enableEnderFurnace", true, GanysEnd.enableEnderFurnace);
		GanysEnd.enableEmulator = configBoolean("enableEmulator", true, GanysEnd.enableEmulator);
		GanysEnd.enableScythe = configBoolean("enableScythe", true, GanysEnd.enableScythe);
		GanysEnd.enableInfiniteBucket = configBoolean("enableInfiniteBucket", true, GanysEnd.enableInfiniteBucket);
		GanysEnd.enableEnderToggler = configBoolean("enableEnderToggler", true, GanysEnd.enableEnderToggler);
		GanysEnd.enableEndium = configBoolean("enableEndium", true, GanysEnd.enableEndium);

		if (configFile.hasChanged())
			configFile.save();
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
		if (Reference.MOD_ID.equals(eventArgs.modID))
			syncConfigs();
	}
}