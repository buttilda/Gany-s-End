package ganymedes01.ganysend.recipes;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.ModBlocks;
import ganymedes01.ganysend.ModItems;
import ganymedes01.ganysend.core.utils.InventoryUtils;
import ganymedes01.ganysend.lib.SkullTypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */
public class EnderFurnaceRecipe {

	public static final ArrayList<EnderFurnaceRecipe> recipes = new ArrayList<EnderFurnaceRecipe>();
	public static final HashMap<Object, Integer> fuelMap = new HashMap<Object, Integer>();

	static {
		addFuel(Items.ender_pearl, 1600);
		addFuel(Items.ender_eye, 2000);
		addFuel(Blocks.end_stone, 10);
		addFuel("enderFlower", 100);
		addFuel("blockEnderPearl", 1600);

		addRecipe(new ItemStack(Items.ender_pearl, 4), "itemSkull", "itemSkull");
		addRecipe(new ItemStack(Blocks.end_stone), Blocks.stone);
		addRecipe(new ItemStack(ModBlocks.endstoneBrick), Blocks.stonebrick);
		addRecipe(new ItemStack(ModBlocks.enderpearlBlock, 1, 1), ModBlocks.enderpearlBlock);
		addRecipe(new ItemStack(Blocks.stone), Blocks.netherrack);
		addRecipe(new ItemStack(Blocks.stonebrick), Blocks.nether_brick);
		addRecipe(new ItemStack(Blocks.dragon_egg), new ItemStack(ModItems.skull, 1, SkullTypes.enderDragon.ordinal()));
		addRecipe(new ItemStack(Items.nether_star), new ItemStack(ModItems.skull, 1, SkullTypes.wither.ordinal()));
		addRecipe(new ItemStack(ModItems.endiumIngot, 2), "oreEndium");
		addRecipe(new ItemStack(ModItems.enderTag), Items.paper);
		addRecipe(new ItemStack(Blocks.mycelium), Blocks.grass);
		addRecipe(new ItemStack(Items.emerald), Items.diamond, Items.gold_ingot, "itemSkull", new ItemStack(Items.potionitem, 1, 8196));
		addRecipe(new ItemStack(Items.diamond), Items.emerald, Items.gold_ingot, "itemSkull", new ItemStack(Items.potionitem, 1, 8194));
		addRecipe(new ItemStack(Items.experience_bottle), new ItemStack(Items.potionitem, 1, 8197), ModItems.endstoneRod);
		addRecipe(new ItemStack(Items.ender_pearl), Items.ghast_tear, Items.sugar, Items.glowstone_dust, Items.experience_bottle);
		if (GanysEnd.enableTimeManipulator)
			addRecipe(new ItemStack(Blocks.dragon_egg), ModBlocks.timeManipulator);
		addRecipe(new ItemStack(Items.cooked_fished), Items.fish);
		addRecipe(new ItemStack(Items.potato), Items.poisonous_potato);
		addRecipe(new ItemStack(Items.beef), Items.rotten_flesh);
		addRecipe(new ItemStack(Blocks.tallgrass, 1, 2), Blocks.deadbush);
		addRecipe(new ItemStack(Items.skull, 1, 1), Items.skull, new ItemStack(Items.potionitem, 1, 8265), Items.skull, new ItemStack(Items.potionitem, 1, 8259));
		addRecipe(new ItemStack(Items.ghast_tear), Items.gold_nugget, new ItemStack(Items.potionitem, 1, 8270), Items.fire_charge, Items.gold_ingot);
		addRecipe(new ItemStack(Items.record_13), new ItemStack(Items.skull), Items.redstone, new ItemStack(Items.dye, 1, 11), new ItemStack(Items.dye, 1, 15));
		addRecipe(new ItemStack(Items.record_cat), new ItemStack(Items.skull), Items.redstone, new ItemStack(Items.dye, 1, 10), new ItemStack(Items.dye, 1, 10));
		addRecipe(new ItemStack(Items.record_blocks), new ItemStack(Items.skull), Items.redstone, new ItemStack(Items.dye, 1, 14), new ItemStack(Items.dye, 1, 3));
		addRecipe(new ItemStack(Items.record_chirp), new ItemStack(Items.skull), Items.redstone, new ItemStack(Items.dye, 1, 0), new ItemStack(Items.dye, 1, 1));
		addRecipe(new ItemStack(Items.record_far), new ItemStack(Items.skull), Items.redstone, new ItemStack(Items.dye, 1, 15), new ItemStack(Items.dye, 1, 2));
		addRecipe(new ItemStack(Items.record_mall), new ItemStack(Items.skull), Items.redstone, new ItemStack(Items.dye, 1, 5), new ItemStack(Items.dye, 1, 5));
		addRecipe(new ItemStack(Items.record_mellohi), new ItemStack(Items.skull), Items.redstone, new ItemStack(Items.dye, 1, 13), new ItemStack(Items.dye, 1, 15));
		addRecipe(new ItemStack(Items.record_stal), new ItemStack(Items.skull), Items.redstone, new ItemStack(Items.dye, 1, 0), new ItemStack(Items.dye, 1, 0));
		addRecipe(new ItemStack(Items.record_strad), new ItemStack(Items.skull), Items.redstone, new ItemStack(Items.dye, 1, 15), new ItemStack(Items.dye, 1, 15));
		addRecipe(new ItemStack(Items.record_ward), new ItemStack(Items.skull), Items.redstone, new ItemStack(Items.dye, 1, 2), new ItemStack(Items.dye, 1, 10));
		addRecipe(new ItemStack(Items.record_11), Items.record_stal, Items.gunpowder, Items.gunpowder, Items.gunpowder);
		addRecipe(new ItemStack(Items.record_wait), new ItemStack(Items.skull), Items.redstone, new ItemStack(Items.dye, 1, 6), new ItemStack(Items.dye, 1, 6));
		for (int i = 0; i < 16; i++)
			if (i == 4)
				addRecipe(new ItemStack(Items.dye, 2, i), new ItemStack(Items.dye, 1, i), ModBlocks.enderFlower, ModBlocks.enderFlower);
			else
				addRecipe(new ItemStack(Items.dye, 2, i), new ItemStack(Items.dye, 1, i), ModBlocks.enderFlower);
	}

	public static int getBurnTime(ItemStack stack) {
		if (stack == null)
			return 0;
		for (Entry<Object, Integer> entry : EnderFurnaceRecipe.fuelMap.entrySet())
			if (areStacksTheSame(entry.getKey(), stack))
				return entry.getValue();
		return 0;
	}

	public static ItemStack getOuput(ItemStack... stacks) {
		for (EnderFurnaceRecipe recipe : recipes)
			if (recipe.matches(stacks))
				return recipe.getOutput();
		return null;
	}

	public static void addRecipe(ItemStack output, Object... input) {
		recipes.add(new EnderFurnaceRecipe(output, input));
	}

	public static void addFuel(Object fuel, int burnTime) {
		Object valid = getValidObject(fuel);
		if (fuel != null)
			fuelMap.put(valid, burnTime);
	}

	@SuppressWarnings("unchecked")
	private static boolean areStacksTheSame(Object obj, ItemStack target) {
		if (obj instanceof ItemStack)
			return InventoryUtils.areStacksTheSame((ItemStack) obj, target, false);
		else if (obj instanceof List) {
			List<ItemStack> list = (List<ItemStack>) obj;
			for (ItemStack stack : list)
				if (InventoryUtils.areStacksTheSame(stack, target, false))
					return true;
		}

		return false;
	}

	private static Object getValidObject(Object obj) {
		if (obj instanceof String)
			return OreDictionary.getOres((String) obj);
		else if (obj instanceof Item)
			return new ItemStack((Item) obj);
		else if (obj instanceof Block)
			return new ItemStack((Block) obj);
		else if (obj instanceof ItemStack)
			return obj;

		return null;
	}

	private final Object[] inputs;
	private final ItemStack output;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	EnderFurnaceRecipe(ItemStack output, Object... input) {
		this.output = output;

		List list = new ArrayList();
		for (Object obj : input) {
			Object valid = getValidObject(obj);
			if (obj != null)
				list.add(valid);
		}

		inputs = list.toArray();
	}

	public boolean matches(ItemStack... stacks) {
		List<ItemStack> list = new ArrayList<ItemStack>();
		for (ItemStack stack : stacks)
			if (stack != null)
				list.add(stack);

		label: for (Object input : inputs) {
			for (int i = 0; i < list.size(); i++) {
				ItemStack stack = list.get(i);
				if (stack != null)
					if (areStacksTheSame(input, stack)) {
						list.remove(i);
						continue label;
					}
			}

			return false;
		}

		return list.isEmpty();
	}

	public boolean isPartOfInput(ItemStack target) {
		for (Object input : inputs)
			if (areStacksTheSame(input, target))
				return true;

		return false;
	}

	public ItemStack getOutput() {
		return ItemStack.copyItemStack(output);
	}

	public Object[] getInput() {
		return inputs;
	}
}