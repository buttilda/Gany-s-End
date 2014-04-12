package ganymedes01.ganysend.recipes;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.blocks.ModBlocks;
import ganymedes01.ganysend.items.ModItems;
import ganymedes01.ganysend.lib.SkullTypes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */
public class EnderFurnaceRecipe {

	public static final ArrayList<EnderFurnaceRecipe> recipes = new ArrayList<EnderFurnaceRecipe>();
	private static final ArrayList<Integer> usedOres = new ArrayList<Integer>();

	static {
		addRecipe(new ItemStack(Item.enderPearl, 4), "mobHead", "mobHead");
		addRecipe(new ItemStack(Block.whiteStone), Block.stone);
		addRecipe(new ItemStack(ModBlocks.endstoneBrick), Block.stoneBrick);
		addRecipe(new ItemStack(ModBlocks.enderpearlBlock, 1, 1), ModBlocks.enderpearlBlock);
		addRecipe(new ItemStack(Block.stone), Block.netherrack);
		addRecipe(new ItemStack(Block.stoneBrick), Block.netherBrick);
		addRecipe(new ItemStack(Block.dragonEgg), new ItemStack(ModItems.itemNewSkull, 1, SkullTypes.enderDragon.ordinal()));
		addRecipe(new ItemStack(Item.netherStar, 2), new ItemStack(ModItems.itemNewSkull, 1, SkullTypes.wither.ordinal()));
		addRecipe(new ItemStack(ModItems.endiumIngot, 2), ModBlocks.rawEndium);
		addRecipe(new ItemStack(ModItems.enderTag), Item.paper);
		addRecipe(new ItemStack(Block.mycelium), Block.grass);
		addRecipe(new ItemStack(Item.emerald), Item.diamond, Item.ingotGold, "mobHead", new ItemStack(Item.potion, 1, 8196));
		addRecipe(new ItemStack(Item.expBottle), new ItemStack(Item.potion, 1, 8197), Block.whiteStone);
		addRecipe(new ItemStack(Item.enderPearl), Item.ghastTear, Item.sugar, Item.glowstone, Item.expBottle);
		if (GanysEnd.enableTimeManipulator)
			addRecipe(new ItemStack(Block.dragonEgg), ModBlocks.timeManipulator);
		addRecipe(new ItemStack(Item.fishCooked), Item.fishRaw);
		addRecipe(new ItemStack(Item.skull, 1, 1), Item.skull, new ItemStack(Item.potion, 1, 8265), Item.skull, new ItemStack(Item.potion, 1, 8259));
	}

	public static ItemStack getOuput(ItemStack... stacks) {
		List<ItemStack> list = new ArrayList<ItemStack>();
		for (int i = 0; i < stacks.length; i++)
			if (stacks[i] != null)
				list.add(stacks[i]);

		Collections.sort(list, new StackComparator());
		System.out.println(list);
		label: for (EnderFurnaceRecipe recipe : recipes) {
			if (recipe.input.length != list.size())
				continue;
			for (int i = 0; i < list.size(); i++)
				if (!doStacksMatch(recipe.input[i], list.get(i)))
					continue label;
			return recipe.output.copy();
		}
		return null;
	}

	public static boolean doStacksMatch(Object obj, ItemStack stack2) {
		if (obj == null && stack2 == null)
			return true;

		if (obj instanceof ItemStack) {
			ItemStack stack1 = (ItemStack) obj;
			if (stack1 != null && stack2 != null)
				if (stack1.itemID == stack2.itemID)
					if (stack1.getItemDamage() == stack2.getItemDamage() || stack1.getItemDamage() == OreDictionary.WILDCARD_VALUE)
						if (stack1.hasTagCompound())
							return stack2.hasTagCompound() ? stack1.getTagCompound().equals(stack2.getTagCompound()) : false;
						else
							return true;
		} else if (obj instanceof String)
			for (ItemStack stack : OreDictionary.getOres((String) obj))
				if (doStacksMatch(stack, stack2))
					return true;
		return false;
	}

	private final Object[] input;
	private final ItemStack output;

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

		Collections.sort(list, new StackComparator());

		this.input = list.toArray();
	}

	public ItemStack getOutput() {
		return output.copy();
	}

	public Object[] getInput() {
		return input.clone();
	}

	private static void addRecipe(ItemStack output, Object... input) {
		recipes.add(new EnderFurnaceRecipe(output, input));
	}

	static class StackComparator implements Comparator {

		@Override
		public int compare(Object obj1, Object obj2) {
			if (obj1 == null && obj2 != null)
				return -1;
			if (obj2 == null && obj1 != null)
				return 1;
			if (obj1 == null && obj2 == null)
				return 0;

			if (obj1 instanceof ItemStack && obj2 instanceof ItemStack)
				return compareStacks((ItemStack) obj1, (ItemStack) obj2);
			else if (obj1 instanceof String && obj2 instanceof String)
				return ((String) obj1).compareTo((String) obj2);
			else if (obj1 instanceof String && obj2 instanceof ItemStack)
				return compareStringWithStack((String) obj1, (ItemStack) obj2);
			else if (obj1 instanceof ItemStack && obj2 instanceof String)
				return -compareStringWithStack((String) obj2, (ItemStack) obj1);
			return -1;
		}

		private int compareStringWithStack(String string, ItemStack stack) {
			int ore1 = OreDictionary.getOreID(string);
			int ore2 = OreDictionary.getOreID(stack);
			if (ore1 < 0 && ore2 < 0)
				return -1;
			else if (ore1 == ore2)
				return 0;
			else
				return ore1 - ore2;
		}

		private int compareStacks(ItemStack stack1, ItemStack stack2) {
			String name1 = stack1.getUnlocalizedName();
			String name2 = stack2.getUnlocalizedName();

			if (name1.equals(name2)) {
				int meta1 = stack1.getItemDamage();
				int meta2 = stack2.getItemDamage();
				if (meta1 == meta2) {
					int size1 = stack1.stackSize;
					int size2 = stack2.stackSize;
					if (size1 == size2) {
						NBTTagCompound nbt1 = stack1.stackTagCompound;
						NBTTagCompound nbt2 = stack2.stackTagCompound;
						if (nbt1 == null && nbt2 != null)
							return -1;
						if (nbt2 == null && nbt1 != null)
							return 1;
						if (nbt1 != null && nbt2 != null)
							return nbt1.hashCode() - nbt2.hashCode();
						else
							return 0;
					} else
						return size1 - size2;
				} else
					return meta1 - meta2;
			} else
				return name1.compareTo(name2);
		}
	}
}