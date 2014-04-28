package ganymedes01.ganysend.items;

import ganymedes01.ganysend.dispenser.DispenserBehaviorInfiniteBucket;
import ganymedes01.ganysend.dispenser.DispenserBehaviorInfusedGem;
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
	public static final Item enderTag = new EnderTag();
	public static final Item endiumIngot = new EndiumIngot();
	public static final Item endstoneRod = new EndstoneRod();
	public static final Item enderScythe = new EnderScythe();
	public static final Item infiniteBucket = new InfiniteBucket();
	public static final Item itemNewSkull = new ItemNewSkull();
	public static final Item infusedGem = new InfusedGem();
	public static final Item endiumPickaxe = new EndiumPickaxe();
	public static final Item endiumAxe = new EndiumAxe();
	public static final Item endiumShovel = new EndiumShovel();
	public static final Item reinforcedEndiumPickaxe = new ReinforcedEndiumPickaxe();
	public static final Item reinforcedEndiumAxe = new ReinforcedEndiumAxe();
	public static final Item reinforcedEndiumShovel = new ReinforcedEndiumShovel();
	public static final Item enderBag = new EnderBag();

	// Armour
	public static final Item endiumHelmet = new EndiumHelmet();
	public static final Item endiumChestplate = new EndiumChestplate();
	public static final Item endiumLeggings = new EndiumLeggings();
	public static final Item endiumBoots = new EndiumBoots();

	public static void init() {
		// Armour
		registerItem(endiumHelmet);
		registerItem(endiumChestplate);
		registerItem(endiumLeggings);
		registerItem(endiumBoots);

		// Items
		registerItem(enderTag);
		registerItem(endiumIngot);
		registerItem(endstoneRod);
		registerItem(enderScythe);
		registerItem(infiniteBucket);
		registerItem(itemNewSkull);
		registerItem(infusedGem);
		registerItem(endiumPickaxe);
		registerItem(endiumAxe);
		registerItem(endiumShovel);
		registerItem(reinforcedEndiumPickaxe);
		registerItem(reinforcedEndiumAxe);
		registerItem(reinforcedEndiumShovel);
		registerItem(enderBag);

		FluidContainerRegistry.registerFluidContainer(FluidRegistry.WATER, new ItemStack(infiniteBucket), new ItemStack(infiniteBucket));

		BlockDispenser.dispenseBehaviorRegistry.putObject(infiniteBucket, new DispenserBehaviorInfiniteBucket());
		BlockDispenser.dispenseBehaviorRegistry.putObject(infusedGem, new DispenserBehaviorInfusedGem());
	}

	private static void registerItem(Item item) {
		String name = item.getUnlocalizedName();
		String[] strings = name.split("\\.");
		GameRegistry.registerItem(item, strings[strings.length - 1]);
	}
}