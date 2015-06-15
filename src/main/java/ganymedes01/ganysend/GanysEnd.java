package ganymedes01.ganysend;

import ganymedes01.ganysend.configuration.ConfigurationHandler;
import ganymedes01.ganysend.core.handlers.InterModComms;
import ganymedes01.ganysend.core.proxy.CommonProxy;
import ganymedes01.ganysend.creativetab.CreativeTabEnd;
import ganymedes01.ganysend.enchantment.ModEnchants;
import ganymedes01.ganysend.integration.Integration;
import ganymedes01.ganysend.integration.ModIntegrator;
import ganymedes01.ganysend.integration.ThaumcraftManager;
import ganymedes01.ganysend.lib.Reference;
import ganymedes01.ganysend.network.PacketHandler;
import ganymedes01.ganysend.recipes.EnderFurnaceFuelsRegistry;
import ganymedes01.ganysend.recipes.EnderFurnaceRegistry;
import ganymedes01.ganysend.recipes.ModRecipes;
import ganymedes01.ganysend.world.EndWorldGenerator;

import java.lang.reflect.Field;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.FlowerEntry;
import net.minecraftforge.oredict.OreDictionary;
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
	public static boolean enableShifters = true;
	public static boolean enableTimeManipulator = true;
	public static boolean enableRandomHeadDrop = true;
	public static boolean enableVanillaHeadsDrop = true;
	public static boolean enableEnderBag = true;
	public static boolean enableAnchoredEnderChest = true;
	public static boolean enable2DHoppers = false;
	public static boolean enableEnderFlower = true;
	public static boolean enableEndiumGen = true;
	public static boolean enableEndiumArmour = true;
	public static boolean enableEndiumTools = true;
	public static boolean enableHoppers = true;
	public static boolean enableDecorativeBlocks = true;
	public static boolean enableVoidCrate = true;
	public static boolean enableInfiniteWaterSource = true;
	public static boolean enableInventoryBinder = true;
	public static boolean enableEnderFurnace = true;
	public static boolean enableEmulator = true;
	public static boolean enableScythe = true;
	public static boolean enableInfiniteBucket = true;
	public static boolean enableSkulls = true;
	public static boolean enableEnderToggler = true;
	public static boolean enableEndium = true;

	public static boolean isHeadcrumbsLoaded = false;
	public static boolean isBotaniaLoaded = false;
	public static Item headcrumbsHead = null;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ModIntegrator.preInit();

		ConfigurationHandler.INSTANCE.init(event);

		GameRegistry.registerWorldGenerator(new EndWorldGenerator(), 0);

		ModBlocks.init();
		ModItems.init();
		ModEnchants.init();
		ModRecipes.registerOreDictionary();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		isHeadcrumbsLoaded = Loader.isModLoaded("headcrumbs");
		isBotaniaLoaded = Loader.isModLoaded("Botania");
		if (isHeadcrumbsLoaded)
			ModItems.skull.setCreativeTab(null);
		ModRecipes.init();

		if (enableEnderFurnace) {
			EnderFurnaceRegistry.INSTANCE.init();
			EnderFurnaceFuelsRegistry.INSTANCE.init();
		}

		PacketHandler.init();

		NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);

		proxy.registerEvents();
		proxy.registerTileEntities();
		proxy.registerRenderers();
		proxy.registerEntities();

		ModIntegrator.init();

		if (GanysEnd.enableEnderBag || GanysEnd.enableAnchoredEnderChest)
			try {
				Block blockEnderChest = (Block) Class.forName("codechicken.enderstorage.EnderStorage").getDeclaredField("blockEnderChest").get(null);
				for (int i = 0; i < 0x1000; i++)
					OreDictionary.registerOre("chestEnder", new ItemStack(blockEnderChest, 1, i));
			} catch (Exception e) {
			}
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

		if (isHeadcrumbsLoaded)
			try {
				Class<?> cls = Class.forName("ganymedes01.headcrumbs.ModItems");
				Field f = cls.getDeclaredField("skull");
				if (!f.isAccessible())
					f.setAccessible(true);
				headcrumbsHead = (Item) f.get(null);
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