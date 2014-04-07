package ganymedes01.ganysend;

import ganymedes01.ganysend.blocks.ModBlocks;
import ganymedes01.ganysend.configuration.ConfigurationHandler;
import ganymedes01.ganysend.core.handlers.RenderCapeHandler;
import ganymedes01.ganysend.core.handlers.VersionCheckTickHandler;
import ganymedes01.ganysend.core.proxy.CommonProxy;
import ganymedes01.ganysend.core.utils.VersionHelper;
import ganymedes01.ganysend.creativetab.CreativeTabEnd;
import ganymedes01.ganysend.enchantment.ModEnchants;
import ganymedes01.ganysend.integration.Integration;
import ganymedes01.ganysend.integration.ModIntegrator;
import ganymedes01.ganysend.integration.ThaumCraftManager;
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
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION_NUMBER, dependencies = Reference.DEPENDENCIES)
@NetworkMod(channels = { Reference.CHANNEL_NAME }, clientSideRequired = true, serverSideRequired = true, packetHandler = PacketHandler.class)
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

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ModIntegrator.preInit();

		ConfigurationHandler.init(new File(event.getModConfigurationDirectory().getAbsolutePath() + File.separator + Reference.MASTER + File.separator + Reference.MOD_ID + ".cfg"));

		if (shouldDoVersionCheck) {
			VersionHelper.execute();
			TickRegistry.registerTickHandler(new VersionCheckTickHandler(), Side.CLIENT);
		}

		proxy.registerEventHandlers();

		ModBlocks.init();
		ModItems.init();
		ModRecipes.init();
		ModEnchants.init();
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
		NetworkRegistry.instance().registerGuiHandler(instance, proxy);
		proxy.registerTileEntities();
		proxy.registerRenderers();
		GameRegistry.registerWorldGenerator(new EndWorldGenerator());

		if (!Loader.isModLoaded("mobsplice"))
			if (event.getSide() == Side.CLIENT) {
				RenderCapeHandler.getUsernames();
				MinecraftForge.EVENT_BUS.register(new RenderCapeHandler());
			}

		ModIntegrator.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		ModIntegrator.postInit();

		try {
			Field f = ForgeHooks.class.getDeclaredField("grassList");
			f.setAccessible(true);
			for (Object obj : (List) f.get(null)) {
				Field f2 = obj.getClass().getField("block");
				f2.setAccessible(true);
				OreDictionary.registerOre("dayGemMaterial", (Block) f2.get(obj));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@EventHandler
	public void postPostInit(FMLServerAboutToStartEvent event) {
		for (Integration integration : ModIntegrator.modIntegrations)
			if (integration.shouldIntegrate() && integration instanceof ThaumCraftManager) {
				((ThaumCraftManager) integration).postPostInit();
				return;
			}
	}
}