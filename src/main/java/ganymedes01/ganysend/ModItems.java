package ganymedes01.ganysend;

import java.lang.reflect.Field;

import ganymedes01.ganysend.dispenser.DispenserBehaviorInfiniteBucket;
import ganymedes01.ganysend.dispenser.DispenserBehaviorInfusedGem;
import ganymedes01.ganysend.items.EnderBag;
import ganymedes01.ganysend.items.EnderScythe;
import ganymedes01.ganysend.items.EnderTag;
import ganymedes01.ganysend.items.EndiumAxe;
import ganymedes01.ganysend.items.EndiumBoots;
import ganymedes01.ganysend.items.EndiumBow;
import ganymedes01.ganysend.items.EndiumChestplate;
import ganymedes01.ganysend.items.EndiumHelmet;
import ganymedes01.ganysend.items.EndiumIngot;
import ganymedes01.ganysend.items.EndiumLeggings;
import ganymedes01.ganysend.items.EndiumPickaxe;
import ganymedes01.ganysend.items.EndiumShovel;
import ganymedes01.ganysend.items.EndiumSword;
import ganymedes01.ganysend.items.EndstoneRod;
import ganymedes01.ganysend.items.InfiniteBucket;
import ganymedes01.ganysend.items.InfusedGem;
import ganymedes01.ganysend.items.ItemAnchoredEnderChestMinecart;
import ganymedes01.ganysend.items.ReinforcedEnderScythe;
import ganymedes01.ganysend.items.ReinforcedEndiumAxe;
import ganymedes01.ganysend.items.ReinforcedEndiumBow;
import ganymedes01.ganysend.items.ReinforcedEndiumPickaxe;
import ganymedes01.ganysend.items.ReinforcedEndiumShovel;
import ganymedes01.ganysend.items.ReinforcedEndiumSword;
import net.minecraft.block.BlockDispenser;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

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
	public static final Item infusedGem = new InfusedGem();
	public static final Item endiumPickaxe = new EndiumPickaxe();
	public static final Item endiumAxe = new EndiumAxe();
	public static final Item endiumShovel = new EndiumShovel();
	public static final Item endiumSword = new EndiumSword();
	public static final Item endiumBow = new EndiumBow();
	public static final Item reinforcedEndiumPickaxe = new ReinforcedEndiumPickaxe();
	public static final Item reinforcedEndiumAxe = new ReinforcedEndiumAxe();
	public static final Item reinforcedEndiumShovel = new ReinforcedEndiumShovel();
	public static final Item reinforcedEndiumSword = new ReinforcedEndiumSword();
	public static final Item reinforcedEndiumBow = new ReinforcedEndiumBow();
	public static final Item enderBag = new EnderBag();
	public static final Item reinforcedEnderScythe = new ReinforcedEnderScythe();
	public static final Item anchoredEnderChestMinecart = new ItemAnchoredEnderChestMinecart();

	// Armour
	public static final Item endiumHelmet = new EndiumHelmet();
	public static final Item endiumChestplate = new EndiumChestplate();
	public static final Item endiumLeggings = new EndiumLeggings();
	public static final Item endiumBoots = new EndiumBoots();

	public static void init() {
		try {
			for (Field f : ModItems.class.getDeclaredFields()) {
				Object obj = f.get(null);
				if (obj instanceof Item)
					registerItem((Item) obj);
				else if (obj instanceof Item[])
					for (Item item : (Item[]) obj)
						registerItem(item);
			}
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}

		if (GanysEnd.enableInfiniteBucket) {
			BlockDispenser.dispenseBehaviorRegistry.putObject(infiniteBucket, new DispenserBehaviorInfiniteBucket());
			FluidContainerRegistry.registerFluidContainer(FluidRegistry.WATER, new ItemStack(infiniteBucket), new ItemStack(infiniteBucket));
		}

		if (GanysEnd.enableTimeManipulator)
			BlockDispenser.dispenseBehaviorRegistry.putObject(infusedGem, new DispenserBehaviorInfusedGem());
	}

	private static void registerItem(Item item) {
		if (!(item instanceof IConfigurable) || ((IConfigurable) item).isEnabled()) {
			String name = item.getUnlocalizedName();
			String[] strings = name.split("\\.");
			GameRegistry.registerItem(item, strings[strings.length - 1]);
		}
	}
}