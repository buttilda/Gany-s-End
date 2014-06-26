package ganymedes01.ganysend;

import ganymedes01.ganysend.blocks.ModBlocks;
import ganymedes01.ganysend.configuration.ConfigurationHandler;
import ganymedes01.ganysend.core.handlers.InterModComms;
import ganymedes01.ganysend.core.handlers.RenderCapeHandler;
import ganymedes01.ganysend.core.handlers.VersionCheckTickHandler;
import ganymedes01.ganysend.core.proxy.CommonProxy;
import ganymedes01.ganysend.core.utils.VersionHelper;
import ganymedes01.ganysend.creativetab.CreativeTabEnd;
import ganymedes01.ganysend.enchantment.ModEnchants;
import ganymedes01.ganysend.integration.Integration;
import ganymedes01.ganysend.integration.ModIntegrator;
import ganymedes01.ganysend.integration.ThaumcraftManager;
import ganymedes01.ganysend.items.ModItems;
import ganymedes01.ganysend.lib.Reference;
import ganymedes01.ganysend.network.PacketHandler;
import ganymedes01.ganysend.recipes.ModRecipes;
import ganymedes01.ganysend.world.EndWorldGenerator;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.FlowerEntry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION_NUMBER, dependencies = Reference.DEPENDENCIES, guiFactory = Reference.GUI_FACTORY_CLASS)
public class GanysEnd {

	@Instance(Reference.MOD_ID)
	public static GanysEnd instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;

	public static CreativeTabs endTab = new CreativeTabEnd();
	public static boolean togglerShouldMakeSound = true;
	public static boolean shouldDoVersionCheck = true;
	public static boolean activateShifters = true;
	public static boolean enableTimeManipulator = true;
	public static boolean enableRandomHeadDrop = true;
	public static boolean enableEnderBag = true;
	public static boolean enableRawEndiumRecipe = false;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ModIntegrator.preInit();

		ConfigurationHandler.INSTANCE.init(new File(event.getModConfigurationDirectory().getAbsolutePath() + File.separator + Reference.MASTER + File.separator + Reference.MOD_ID + ".cfg"));
		FMLCommonHandler.instance().bus().register(ConfigurationHandler.INSTANCE);

		if (shouldDoVersionCheck) {
			VersionHelper.execute();
			FMLCommonHandler.instance().bus().register(new VersionCheckTickHandler());
		}

		proxy.registerEventHandlers();

		ModBlocks.init();
		ModItems.init();
		ModRecipes.init();
		ModEnchants.init();
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
		PacketHandler.init();
		proxy.registerTileEntities();
		proxy.registerRenderers();
		GameRegistry.registerWorldGenerator(new EndWorldGenerator(), 0);
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);

		if (event.getSide() == Side.CLIENT)
			if (!Loader.isModLoaded("ganysnether"))
				MinecraftForge.EVENT_BUS.register(new RenderCapeHandler());

		if (GanysEnd.enableEnderBag)
			try {
				Block blockEnderChest = (Block) Class.forName("codechicken.enderstorage.EnderStorage").getDeclaredField("blockEnderChest").get(null);
				for (int i = 0; i < 0x1000; i++)
					OreDictionary.registerOre("enderChest", new ItemStack(blockEnderChest, 1, i));
			} catch (Exception e) {
			}

		ModIntegrator.init();
	}

	@SuppressWarnings("unchecked")
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		ModIntegrator.postInit();

		try {
			for (BiomeGenBase biome : BiomeGenBase.getBiomeGenArray()) {
				Field f = BiomeGenBase.class.getDeclaredField("flowers");
				f.setAccessible(true);
				for (FlowerEntry entry : (List<FlowerEntry>) f.get(biome))
					if (entry.block != null)
						OreDictionary.registerOre("dayGemMaterial", new ItemStack(entry.block, 1, entry.metadata));
			}
		} catch (Exception e) {
		}
	}

	@EventHandler
	public void postPostInit(FMLServerAboutToStartEvent event) {
		for (Integration integration : ModIntegrator.modIntegrations)
			if (integration.shouldIntegrate() && integration instanceof ThaumcraftManager) {
				((ThaumcraftManager) integration).postPostInit();
				return;
			}
	}

	@EventHandler
	public void processIMCRequests(IMCEvent event) {
		InterModComms.processIMC(event);
	}
}