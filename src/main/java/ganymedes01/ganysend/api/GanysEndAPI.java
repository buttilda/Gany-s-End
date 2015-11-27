package ganymedes01.ganysend.api;

import java.lang.reflect.Field;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.event.FMLInterModComms;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class GanysEndAPI {

	/**
	 * Adds a recipe to the Ender Furnace input parameter must have between 1 and 4 items
	 * 
	 * @param output
	 * @param input
	 *            (Can contain ItemStacks and/or Strings)
	 */
	public static void addEnderFurnaceRecipe(ItemStack output, Object... input) {
		NBTTagCompound nbt = new NBTTagCompound();

		NBTTagCompound out = new NBTTagCompound();
		output.writeToNBT(out);
		nbt.setTag("output", out);

		for (int i = 0; i < input.length; i++)
			if (input[i] != null)
				if (input[i] instanceof ItemStack) {
					NBTTagCompound in = new NBTTagCompound();
					((ItemStack) input[i]).writeToNBT(in);
					nbt.setTag("input" + i, in);
				} else
					nbt.setString("input" + i, (String) input[i]);

		FMLInterModComms.sendMessage("ganysend", "enderFurnaceRecipe", nbt);
	}

	/**
	 * Adds an item that can be burned in the Ender Furnace
	 * 
	 * @param fuel
	 *            (Can be an ItemStack or a String)
	 * @param burnTime
	 *            (200 is the time it takes to smelt 1 item in the furnace)
	 */
	public static void addEnderFurnaceFuel(Object fuel, int burnTime) {
		Object toAdd;
		if (fuel instanceof Item)
			toAdd = new ItemStack((Item) fuel);
		else if (fuel instanceof Block)
			toAdd = new ItemStack((Block) fuel);
		else
			toAdd = fuel;

		NBTTagCompound nbt = new NBTTagCompound();
		if (toAdd instanceof ItemStack) {
			NBTTagCompound f = new NBTTagCompound();
			((ItemStack) toAdd).writeToNBT(f);
			nbt.setTag("fuel", f);
		} else
			nbt.setString("fuel", (String) toAdd);

		nbt.setInteger("burnTime", burnTime);

		FMLInterModComms.sendMessage("ganysend", "enderFurnaceFuel", nbt);
	}

	public static final Block getBlock(String blockName) {
		try {
			Class<?> modBlocks = Class.forName("ganymedes01.ganysend.blocks.ModBlocks");
			Field block = modBlocks.getField(blockName);
			return (Block) block.get(null);
		} catch (Exception e) {
			FMLLog.warning("[ganysend] Problems retrieving block: " + blockName);
			return null;
		}
	}

	public static final Item getItem(String itemName) {
		try {
			Class<?> modBlocks = Class.forName("ganymedes01.ganysend.items.ModItems");
			Field item = modBlocks.getField(itemName);
			return (Item) item.get(null);
		} catch (Exception e) {
			FMLLog.warning("[ganysend] Problems retrieving item: " + itemName);
			return null;
		}
	}
}