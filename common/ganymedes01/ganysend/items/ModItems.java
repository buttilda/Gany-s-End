package ganymedes01.ganysend.items;

import ganymedes01.ganysend.lib.Strings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class ModItems {

	// Items
	public static Item enderTag;
	public static Item endiumIngot;
	public static Item endstoneRod;
	public static Item enderScythe;
	public static Item infiniteBucket;
	public static Item itemNewSkull;
	public static Item infusedGem;

	// Armour
	public static Item endiumHelmet;
	public static Item endiumChestplate;
	public static Item endiumLeggings;
	public static Item endiumBoots;

	public static void init() {
		// Armour
		endiumHelmet = new EndiumHelmet();
		endiumChestplate = new EndiumChestplate();
		endiumLeggings = new EndiumLeggings();
		endiumBoots = new EndiumBoots();

		// Items
		enderTag = new EnderTag();
		endiumIngot = new EndiumIngot();
		endstoneRod = new EndstoneRod();
		enderScythe = new EnderScythe();
		infiniteBucket = new InfiniteBucket();
		itemNewSkull = new ItemNewSkull();
		infusedGem = new InfusedGem();

		registerNames();
		registerForge();
	}

	private static void registerNames() {
		// Armour
		GameRegistry.registerItem(endiumHelmet, Strings.ENDIUM_HELMET_NAME);
		GameRegistry.registerItem(endiumChestplate, Strings.ENDIUM_CHESTPLATE_NAME);
		GameRegistry.registerItem(endiumLeggings, Strings.ENDIUM_LEGGINGS_NAME);
		GameRegistry.registerItem(endiumBoots, Strings.ENDIUM_BOOTS_NAME);

		// Items
		GameRegistry.registerItem(enderTag, Strings.ENDER_TAG_NAME);
		GameRegistry.registerItem(endiumIngot, Strings.ENDIUM_INGOT_NAME);
		GameRegistry.registerItem(endstoneRod, Strings.ENDSTONE_ROD_NAME);
		GameRegistry.registerItem(enderScythe, Strings.ENDER_SCYTHE_NAME);
		GameRegistry.registerItem(infiniteBucket, Strings.INFINITE_BUCKET_NAME);
		GameRegistry.registerItem(itemNewSkull, Strings.ITEM_NEW_SKULL_NAME);
		GameRegistry.registerItem(infusedGem, Strings.INFUSED_GEM_NAME);
	}

	private static void registerForge() {
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.WATER, new ItemStack(infiniteBucket), new ItemStack(infiniteBucket));
	}
}
