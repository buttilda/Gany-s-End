package ganymedes01.ganysend.items;

import ganymedes01.ganysend.lib.ItemsID;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

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
		endiumHelmet = new EndiumHelmet(ItemsID.ENDIUM_HELMET_ID);
		endiumChestplate = new EndiumChestplate(ItemsID.ENDIUM_CHESTPLATE_ID);
		endiumLeggings = new EndiumLeggings(ItemsID.ENDIUM_LEGGINGS_ID);
		endiumBoots = new EndiumBoots(ItemsID.ENDIUM_BOOTS_ID);

		// Items
		enderTag = new EnderTag(ItemsID.ENDER_TAG_ID);
		endiumIngot = new EndiumIngot(ItemsID.ENDIUM_INGOT_ID);
		endstoneRod = new EndstoneRod(ItemsID.ENDSTONE_ROD_ID);
		enderScythe = new EnderScythe(ItemsID.ENDER_SCYTHE_ID);
		infiniteBucket = new InfiniteBucket(ItemsID.INFINITE_BUCKET_ID);
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.WATER, new ItemStack(infiniteBucket), new ItemStack(infiniteBucket));
		itemNewSkull = new ItemNewSkull(ItemsID.ITEM_NEW_SKULL_ID);
		infusedGem = new InfusedGem(ItemsID.INFUSED_GEM_ID);

		registerNames();
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
}
