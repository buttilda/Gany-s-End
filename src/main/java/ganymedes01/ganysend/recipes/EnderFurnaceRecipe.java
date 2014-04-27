package ganymedes01.ganysend.recipes;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.blocks.ModBlocks;
import ganymedes01.ganysend.items.ModItems;
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

		addRecipe(new ItemStack(Items.ender_pearl, 4), "mobHead", "mobHead");
		addRecipe(new ItemStack(Blocks.end_stone), Blocks.stone);
		addRecipe(new ItemStack(ModBlocks.endstoneBrick), Blocks.stonebrick);
		addRecipe(new ItemStack(ModBlocks.enderpearlBlock, 1, 1), ModBlocks.enderpearlBlock);
		addRecipe(new ItemStack(Blocks.stone), Blocks.netherrack);
		addRecipe(new ItemStack(Blocks.stonebrick), Blocks.nether_brick);
		addRecipe(new ItemStack(Blocks.dragon_egg), new ItemStack(ModItems.itemNewSkull, 1, SkullTypes.enderDragon.ordinal()));
		addRecipe(new ItemStack(Items.nether_star, 2), new ItemStack(ModItems.itemNewSkull, 1, SkullTypes.wither.ordinal()));
		addRecipe(new ItemStack(ModItems.endiumIngot, 2), "oreEndium");
		addRecipe(new ItemStack(ModItems.enderTag), Items.paper);
		addRecipe(new ItemStack(Blocks.mycelium), Blocks.grass);
		addRecipe(new ItemStack(Items.emerald), Items.diamond, Items.gold_ingot, "mobHead", new ItemStack(Items.potionitem, 1, 8196));
		addRecipe(new ItemStack(Items.diamond), Items.emerald, Items.gold_ingot, "mobHead", new ItemStack(Items.potionitem, 1, 8194));
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
			if (stacksMatch(entry.getKey(), stack))
				return entry.getValue();
		return 0;
	}

	public static ItemStack getOuput(ItemStack... stacks) {
		List<ItemStack> list = new ArrayList<ItemStack>();
		for (int i = 0; i < stacks.length; i++)
			if (stacks[i] != null)
				list.add(stacks[i]);

		label: for (EnderFurnaceRecipe recipe : recipes) {
			if (recipe.input.length != list.size())
				continue label;
			for (ItemStack stack : list)
				if (!arrayContainsStack(recipe.input, stack))
					continue label;
			return recipe.output.copy();
		}
		return null;
	}

	private static boolean arrayContainsStack(Object[] array, ItemStack stack) {
		for (Object obj : array)
			if (stacksMatch(obj, stack))
				return true;
		return false;
	}

	public static boolean stacksMatch(Object obj, ItemStack stack2) {
		if (obj == null && stack2 == null)
			return true;

		if (obj instanceof ItemStack) {
			ItemStack stack1 = (ItemStack) obj;
			if (stack1 != null && stack2 != null)
				if (stack1.getItem() == stack2.getItem())
					if (stack1.getItemDamage() == stack2.getItemDamage() || stack1.getItemDamage() == OreDictionary.WILDCARD_VALUE)
						if (stack1.hasTagCompound())
							return stack2.hasTagCompound() ? stack1.getTagCompound().equals(stack2.getTagCompound()) : false;
						else
							return true;
		} else if (obj instanceof String) {
			for (ItemStack stack : OreDictionary.getOres((String) obj))
				if (stacksMatch(stack, stack2))
					return true;
		} else if (obj instanceof Item)
			return stacksMatch(new ItemStack((Item) obj), stack2);
		else if (obj instanceof Block)
			return stacksMatch(new ItemStack((Block) obj), stack2);

		return false;
	}

	private final Object[] input;
	private final ItemStack output;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	EnderFurnaceRecipe(ItemStack output, Object... input) {
		this.output = output;

		List list = new ArrayList();
		for (Object obj : input)
			if (obj instanceof String || obj instanceof ItemStack)
				list.add(obj);
			else if (obj instanceof Item)
				list.add(new ItemStack((Item) obj));
			else if (obj instanceof Block)
				list.add(new ItemStack((Block) obj));

		this.input = list.toArray();
	}

	public ItemStack getOutput() {
		return output.copy();
	}

	public Object[] getInput() {
		return input.clone();
	}

	public static void addRecipe(ItemStack output, Object... input) {
		recipes.add(new EnderFurnaceRecipe(output, input));
	}

	public static void addFuel(Object fuel, int burnTime) {
		fuelMap.put(fuel, burnTime);
	}
}