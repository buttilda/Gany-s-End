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
	public static final HashMap<Object, Integer> fuelMap = new HashMap();

	static {
		addFuel(Item.enderPearl, 1600);
		addFuel(Item.eyeOfEnder, 2000);
		addFuel(Block.whiteStone, 10);
		addFuel("enderFlower", 100);
		addFuel("blockEnderPearl", 1600);

		addRecipe(new ItemStack(Item.enderPearl, 4), "mobHead", "mobHead");
		addRecipe(new ItemStack(Block.whiteStone), Block.stone);
		addRecipe(new ItemStack(ModBlocks.endstoneBrick), Block.stoneBrick);
		addRecipe(new ItemStack(ModBlocks.enderpearlBlock, 1, 1), ModBlocks.enderpearlBlock);
		addRecipe(new ItemStack(Block.stone), Block.netherrack);
		addRecipe(new ItemStack(Block.stoneBrick), Block.netherBrick);
		addRecipe(new ItemStack(Block.dragonEgg), new ItemStack(ModItems.itemNewSkull, 1, SkullTypes.enderDragon.ordinal()));
		addRecipe(new ItemStack(Item.netherStar, 2), new ItemStack(ModItems.itemNewSkull, 1, SkullTypes.wither.ordinal()));
		addRecipe(new ItemStack(ModItems.endiumIngot, 2), "oreEndium");
		addRecipe(new ItemStack(ModItems.enderTag), Item.paper);
		addRecipe(new ItemStack(Block.mycelium), Block.grass);
		addRecipe(new ItemStack(Item.emerald), Item.diamond, Item.ingotGold, "mobHead", new ItemStack(Item.potion, 1, 8196));
		addRecipe(new ItemStack(Item.diamond), Item.emerald, Item.ingotGold, "mobHead", new ItemStack(Item.potion, 1, 8194));
		addRecipe(new ItemStack(Item.expBottle), new ItemStack(Item.potion, 1, 8197), ModItems.endstoneRod);
		addRecipe(new ItemStack(Item.enderPearl), Item.ghastTear, Item.sugar, Item.glowstone, Item.expBottle);
		if (GanysEnd.enableTimeManipulator)
			addRecipe(new ItemStack(Block.dragonEgg), ModBlocks.timeManipulator);
		addRecipe(new ItemStack(Item.fishCooked), Item.fishRaw);
		addRecipe(new ItemStack(Item.potato), Item.poisonousPotato);
		addRecipe(new ItemStack(Item.beefRaw), Item.rottenFlesh);
		addRecipe(new ItemStack(Block.tallGrass, 1, 2), Block.deadBush);
		addRecipe(new ItemStack(Item.skull, 1, 1), Item.skull, new ItemStack(Item.potion, 1, 8265), Item.skull, new ItemStack(Item.potion, 1, 8259));
		addRecipe(new ItemStack(Item.ghastTear), Item.goldNugget, new ItemStack(Item.potion, 1, 8270), Item.fireballCharge, Item.ingotGold);
		addRecipe(new ItemStack(Item.record13), new ItemStack(Item.skull), Item.redstone, new ItemStack(Item.dyePowder, 1, 11), new ItemStack(Item.dyePowder, 1, 15));
		addRecipe(new ItemStack(Item.recordCat), new ItemStack(Item.skull), Item.redstone, new ItemStack(Item.dyePowder, 1, 10), new ItemStack(Item.dyePowder, 1, 10));
		addRecipe(new ItemStack(Item.recordBlocks), new ItemStack(Item.skull), Item.redstone, new ItemStack(Item.dyePowder, 1, 14), new ItemStack(Item.dyePowder, 1, 3));
		addRecipe(new ItemStack(Item.recordChirp), new ItemStack(Item.skull), Item.redstone, new ItemStack(Item.dyePowder, 1, 0), new ItemStack(Item.dyePowder, 1, 1));
		addRecipe(new ItemStack(Item.recordFar), new ItemStack(Item.skull), Item.redstone, new ItemStack(Item.dyePowder, 1, 15), new ItemStack(Item.dyePowder, 1, 2));
		addRecipe(new ItemStack(Item.recordMall), new ItemStack(Item.skull), Item.redstone, new ItemStack(Item.dyePowder, 1, 5), new ItemStack(Item.dyePowder, 1, 5));
		addRecipe(new ItemStack(Item.recordMellohi), new ItemStack(Item.skull), Item.redstone, new ItemStack(Item.dyePowder, 1, 13), new ItemStack(Item.dyePowder, 1, 15));
		addRecipe(new ItemStack(Item.recordStal), new ItemStack(Item.skull), Item.redstone, new ItemStack(Item.dyePowder, 1, 0), new ItemStack(Item.dyePowder, 1, 0));
		addRecipe(new ItemStack(Item.recordStrad), new ItemStack(Item.skull), Item.redstone, new ItemStack(Item.dyePowder, 1, 15), new ItemStack(Item.dyePowder, 1, 15));
		addRecipe(new ItemStack(Item.recordWard), new ItemStack(Item.skull), Item.redstone, new ItemStack(Item.dyePowder, 1, 2), new ItemStack(Item.dyePowder, 1, 10));
		addRecipe(new ItemStack(Item.record11), Item.recordStal, Item.gunpowder, Item.gunpowder, Item.gunpowder);
		addRecipe(new ItemStack(Item.recordWait), new ItemStack(Item.skull), Item.redstone, new ItemStack(Item.dyePowder, 1, 6), new ItemStack(Item.dyePowder, 1, 6));
		for (int i = 0; i < 16; i++)
			if (i == 4)
				addRecipe(new ItemStack(Item.dyePowder, 2, i), new ItemStack(Item.dyePowder, 1, i), ModBlocks.enderFlower, ModBlocks.enderFlower);
			else
				addRecipe(new ItemStack(Item.dyePowder, 2, i), new ItemStack(Item.dyePowder, 1, i), ModBlocks.enderFlower);
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
				if (stack1.itemID == stack2.itemID)
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