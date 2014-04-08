package ganymedes01.ganysend.items;

import ganymedes01.ganysend.dispenser.DispenserBehaviorInfiniteBucket;
import ganymedes01.ganysend.dispenser.DispenserBehaviorInfusedGem;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.block.BlockDispenser;
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
	public static Item endiumPickaxe;
	public static Item endiumAxe;
	public static Item endiumShovel;
	public static Item reinforcedEndiumPickaxe;
	public static Item reinforcedEndiumAxe;
	public static Item reinforcedEndiumShovel;
	public static Item enderBag;

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
		endiumPickaxe = new EndiumPickaxe();
		endiumAxe = new EndiumAxe();
		endiumShovel = new EndiumShovel();
		reinforcedEndiumPickaxe = new ReinforcedEndiumPickaxe();
		reinforcedEndiumAxe = new ReinforcedEndiumAxe();
		reinforcedEndiumShovel = new ReinforcedEndiumShovel();
		enderBag = new EnderBag();

		registerNames();
		registerForge();
		registerDispenserActions();
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
		GameRegistry.registerItem(endiumPickaxe, Strings.ENDIUM_PICKAXE_NAME);
		GameRegistry.registerItem(endiumAxe, Strings.ENDIUM_AXE_NAME);
		GameRegistry.registerItem(endiumShovel, Strings.ENDIUM_SHOVEL_NAME);
		GameRegistry.registerItem(reinforcedEndiumPickaxe, Strings.REINFORCED_ENDIUM_PICKAXE_NAME);
		GameRegistry.registerItem(reinforcedEndiumAxe, Strings.REINFORCED_ENDIUM_AXE_NAME);
		GameRegistry.registerItem(reinforcedEndiumShovel, Strings.REINFORCED_ENDIUM_SHOVEL_NAME);
		GameRegistry.registerItem(enderBag, Strings.ENDER_BAG_NAME);
	}

	private static void registerForge() {
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.WATER, new ItemStack(infiniteBucket), new ItemStack(infiniteBucket));
	}

	private static void registerDispenserActions() {
		BlockDispenser.dispenseBehaviorRegistry.putObject(infiniteBucket, new DispenserBehaviorInfiniteBucket());
		BlockDispenser.dispenseBehaviorRegistry.putObject(infusedGem, new DispenserBehaviorInfusedGem());
	}
}