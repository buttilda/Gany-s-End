package ganymedes01.ganysend;

import ganymedes01.ganysend.blocks.ModBlocks;
import ganymedes01.ganysend.configuration.ConfigurationHandler;
import ganymedes01.ganysend.core.handlers.ArmourHandler;
import ganymedes01.ganysend.core.handlers.BlockHarvestEvent;
import ganymedes01.ganysend.core.handlers.BonemealOnTheEndEvent;
import ganymedes01.ganysend.core.handlers.EntityDeathEvent;
import ganymedes01.ganysend.core.handlers.VersionCheckTickHandler;
import ganymedes01.ganysend.core.proxy.CommonProxy;
import ganymedes01.ganysend.core.utils.VersionHelper;
import ganymedes01.ganysend.creativetab.CreativeTabEnd;
import ganymedes01.ganysend.items.ModItems;
import ganymedes01.ganysend.lib.Reference;
import ganymedes01.ganysend.modsupport.BuildCraftFacadeManager;
import ganymedes01.ganysend.modsupport.EE3Manager;
import ganymedes01.ganysend.modsupport.ThaumCraftManager;
import ganymedes01.ganysend.network.PacketHandler;
import ganymedes01.ganysend.recipes.ModRecipes;
import ganymedes01.ganysend.world.EnderFlowerGenerator;

import java.io.File;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
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

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ConfigurationHandler.init(new File(event.getModConfigurationDirectory().getAbsolutePath() + File.separator + Reference.MASTER + File.separator + Reference.MOD_ID + ".cfg"));

		if (shouldDoVersionCheck) {
			VersionHelper.execute();
			TickRegistry.registerTickHandler(new VersionCheckTickHandler(), Side.CLIENT);
		}

		MinecraftForge.EVENT_BUS.register(new BonemealOnTheEndEvent());
		MinecraftForge.EVENT_BUS.register(new BlockHarvestEvent());
		MinecraftForge.EVENT_BUS.register(new EntityDeathEvent());
		MinecraftForge.EVENT_BUS.register(new ArmourHandler());

		ModBlocks.init();
		ModItems.init();
		ModRecipes.init();
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
		NetworkRegistry.instance().registerGuiHandler(instance, proxy);
		proxy.registerTileEntities();
		proxy.registerRenderers();
		GameRegistry.registerWorldGenerator(new EnderFlowerGenerator());

		BuildCraftFacadeManager.registerFacades();
		ThaumCraftManager.init();
		EE3Manager.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
	}
}