package ganymedes01.ganysend.integration;

import cpw.mods.fml.common.Loader;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class ModIntegrator {

	public static void integrateMods() {
		// BuildCraft
		if (Loader.isModLoaded("BuildCraft|Transport"))
			BuildCraftFacadeManager.registerFacades();

		// ThaumCraft
		if (Loader.isModLoaded("Thaumcraft"))
			ThaumCraftManager.init();

		// Equivalent Exchange 3
		if (Loader.isModLoaded("EE3"))
			EE3Manager.init();

		// Gany's Nether
		if (Loader.isModLoaded("ganysnether"))
			GanysNetherManager.init();
	}

	public static void postIntegrateMods() {
		// ThaumCraft
		if (Loader.isModLoaded("Thaumcraft"))
			ThaumCraftManager.postInit();
	}
}