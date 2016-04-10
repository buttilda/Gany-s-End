package ganymedes01.ganysend;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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
import ganymedes01.ganysend.lib.Reference;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelResourceLocation;
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
	public static final Item ender_tag = new EnderTag();
	public static final Item endium_ingot = new EndiumIngot();
	public static final Item endstone_rod = new EndstoneRod();
	public static final Item ender_scythe = new EnderScythe();
	public static final Item infinite_bucket = new InfiniteBucket();
	public static final Item infused_gem = new InfusedGem();
	public static final Item endium_pickaxe = new EndiumPickaxe();
	public static final Item endium_axe = new EndiumAxe();
	public static final Item endium_shovel = new EndiumShovel();
	public static final Item endium_sword = new EndiumSword();
	public static final Item endium_bow = new EndiumBow();
	public static final Item reinforced_endium_pickaxe = new ReinforcedEndiumPickaxe();
	public static final Item reinforced_endium_axe = new ReinforcedEndiumAxe();
	public static final Item reinforced_endium_shovel = new ReinforcedEndiumShovel();
	public static final Item reinforced_endium_sword = new ReinforcedEndiumSword();
	public static final Item reinforced_endium_bow = new ReinforcedEndiumBow();
	public static final Item ender_bag = new EnderBag();
	public static final Item reinforced_ender_scythe = new ReinforcedEnderScythe();
	public static final Item anchored_ender_chest_minecart = new ItemAnchoredEnderChestMinecart();

	// Armour
	public static final Item endium_helmet = new EndiumHelmet();
	public static final Item endium_chestplate = new EndiumChestplate();
	public static final Item endium_leggings = new EndiumLeggings();
	public static final Item endium_boots = new EndiumBoots();

	public static void init() {
		for (Item item : getItems())
			registerItem(item);

		if (GanysEnd.enableInfiniteBucket) {
			BlockDispenser.dispenseBehaviorRegistry.putObject(infinite_bucket, new DispenserBehaviorInfiniteBucket());
			FluidContainerRegistry.registerFluidContainer(FluidRegistry.WATER, new ItemStack(infinite_bucket), new ItemStack(infinite_bucket));
		}

		if (GanysEnd.enableTimeManipulator)
			BlockDispenser.dispenseBehaviorRegistry.putObject(infused_gem, new DispenserBehaviorInfusedGem());
	}

	public static void registerRenderers() {
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		for (Item item : getItems())
			if (!(item instanceof IConfigurable) || ((IConfigurable) item).isEnabled())
				if (item instanceof ISubItemsItem) {
					List<String> models = ((ISubItemsItem) item).getModels();
					for (int i = 0; i < models.size(); i++)
						mesher.register(item, i, new ModelResourceLocation(Reference.MOD_ID + ":" + models.get(i), "inventory"));
				} else {
					String name = item.getUnlocalizedName();
					String[] strings = name.split("\\.");
					mesher.register(item, 0, new ModelResourceLocation(Reference.MOD_ID + ":" + strings[strings.length - 1], "inventory"));
				}
	}

	private static List<Item> getItems() {
		List<Item> items = new ArrayList<Item>();
		try {
			for (Field f : ModItems.class.getDeclaredFields()) {
				Object obj = f.get(null);
				if (obj instanceof Item)
					items.add((Item) obj);
				else if (obj instanceof Item[])
					for (Item block : (Item[]) obj)
						if (block != null)
							items.add(block);
			}
			return items;
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	private static void registerItem(Item item) {
		if (!(item instanceof IConfigurable) || ((IConfigurable) item).isEnabled()) {
			String name = item.getUnlocalizedName();
			String[] strings = name.split("\\.");
			GameRegistry.registerItem(item, strings[strings.length - 1]);
		}
	}

	public static interface ISubItemsItem {

		List<String> getModels();
	}
}