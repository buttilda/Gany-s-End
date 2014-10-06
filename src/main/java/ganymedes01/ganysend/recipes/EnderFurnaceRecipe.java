package ganymedes01.ganysend.recipes;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.ModBlocks;
import ganymedes01.ganysend.ModItems;
import ganymedes01.ganysend.core.utils.InventoryUtils;
import ganymedes01.ganysend.core.utils.XMLHelper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
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
	public static final HashMap<Object, Integer> fuelMap = new HashMap<Object, Integer>();
	private static File recipesFile, fuelsFile;

	public static void init(File recipes, File fuels) {
		recipesFile = recipes;
		fuelsFile = fuels;
	}

	public static void init() {
		try {
			if (!fuelsFile.exists()) {
				addDefaultFuels();
				BufferedWriter bw = XMLHelper.getWriter(fuelsFile);
				for (Entry<Object, Integer> entry : fuelMap.entrySet()) {
					StringBuffer buffer = new StringBuffer();
					buffer.append(XMLHelper.makeEntry("input", entry.getKey()));
					buffer.append(XMLHelper.makeEntry("burntime", entry.getValue()));
					bw.write(XMLHelper.makeGroup("fuel", buffer.toString()));
					bw.newLine();
					bw.newLine();
				}
				bw.close();
			} else {
				String line = XMLHelper.readFile(fuelsFile);
				Iterator<String> iterator = XMLHelper.getIterator("fuel", line);
				while (iterator.hasNext()) {
					String entry = iterator.next();
					String input = XMLHelper.getEntry(entry, "input");
					String burntime = XMLHelper.getEntry(entry, "burntime");
					fuelMap.put(XMLHelper.processEntry(input), Integer.parseInt(burntime));
				}
			}
		} catch (IOException e) {
			throw new RuntimeException("Problem reading Ender Furnace fuels!" + e);
		}

		try {
			if (!recipesFile.exists()) {
				addDefaultRecipes();
				BufferedWriter bw = XMLHelper.getWriter(recipesFile);
				for (EnderFurnaceRecipe recipe : recipes) {
					bw.write(recipe.toString());
					bw.newLine();
					bw.newLine();
				}
				bw.close();
			} else {
				String line = XMLHelper.readFile(recipesFile);
				Iterator<String> iterator = XMLHelper.getIterator("recipe", line);
				while (iterator.hasNext())
					recipes.add(new EnderFurnaceRecipe(iterator.next()));
			}
		} catch (IOException e) {
			throw new RuntimeException("Problem reading Ender Furnace recipes!" + e);
		}
	}

	private static void addDefaultFuels() {
		addFuel(Items.ender_pearl, 1600);
		addFuel(Items.ender_eye, 2000);
		addFuel(Blocks.end_stone, 10);
		addFuel("flowerEnder", 100);
		addFuel("blockEnderPearl", 1600);
	}

	private static void addDefaultRecipes() {
		addRecipe(new ItemStack(Items.ender_pearl, 4), "itemSkull", "itemSkull");
		addRecipe(new ItemStack(Blocks.end_stone), Blocks.stone);
		addRecipe(new ItemStack(ModBlocks.endstoneBrick), Blocks.stonebrick);
		addRecipe(new ItemStack(ModBlocks.enderpearlBlock, 1, 1), ModBlocks.enderpearlBlock);
		addRecipe(new ItemStack(Blocks.stone), Blocks.netherrack);
		addRecipe(new ItemStack(Blocks.stonebrick), Blocks.nether_brick);
		addRecipe(new ItemStack(Blocks.dragon_egg), "skullEnderDragon");
		addRecipe(new ItemStack(Items.nether_star), "skullWither");
		addRecipe(new ItemStack(ModItems.endiumIngot, 2), "oreEndium");
		ItemStack enderTag = new ItemStack(ModItems.enderTag);
		enderTag.setTagCompound(new NBTTagCompound());
		addRecipe(enderTag, Items.paper);
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
		if (GanysEnd.enableEnderFlower)
			for (int i = 0; i < 16; i++)
				if (i == 4)
					addRecipe(new ItemStack(Items.dye, 2, i), new ItemStack(Items.dye, 1, i), "flowerEnder", "flowerEnder");
				else
					addRecipe(new ItemStack(Items.dye, 2, i), new ItemStack(Items.dye, 1, i), "flowerEnder");
		addRecipe(new ItemStack(ModBlocks.rawEndium), "oreDiamond");
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

	private static boolean areStacksTheSame(Object obj, ItemStack target) {
		if (obj instanceof ItemStack)
			return InventoryUtils.areStacksTheSame((ItemStack) obj, target, false);
		else if (obj instanceof String) {
			List<ItemStack> list = OreDictionary.getOres((String) obj);
			for (ItemStack stack : list)
				if (InventoryUtils.areStacksTheSame(stack, target, false))
					return true;
		}

		return false;
	}

	private static Object getValidObject(Object obj) {
		if (obj instanceof Item)
			return new ItemStack((Item) obj);
		else if (obj instanceof Block)
			return new ItemStack((Block) obj);
		else if (obj instanceof ItemStack || obj instanceof String)
			return obj;

		return null;
	}

	private final Object[] inputs;
	private final ItemStack output;

	EnderFurnaceRecipe(ItemStack output, Object... input) {
		this.output = output;

		List<Object> list = new ArrayList<Object>();
		for (Object obj : input) {
			Object valid = getValidObject(obj);
			if (obj != null)
				list.add(valid);
		}

		inputs = list.toArray();
	}

	EnderFurnaceRecipe(String line) {
		output = (ItemStack) XMLHelper.processEntry(XMLHelper.getEntry(line, "output"));
		List<Object> inputs = new ArrayList<Object>();
		for (int i = 1; i <= 4; i++)
			if (line.contains("input" + i))
				inputs.add(XMLHelper.processEntry(XMLHelper.getEntry(line, "input" + i)));

		this.inputs = inputs.toArray();
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

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(XMLHelper.makeEntry("output", output));

		int count = 1;
		for (Object input : inputs) {
			buffer.append(XMLHelper.makeEntry("input" + count, input));
			count++;
		}

		return XMLHelper.makeGroup("recipe", buffer.toString());
	}
}