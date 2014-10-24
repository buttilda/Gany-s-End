package ganymedes01.ganysend.core.utils.xml;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class XMLParser {

	public static Object parseNode(XMLNode node) {
		if (!node.hasValue())
			return null;
		String value = node.value;

		try {
			if (isStringValue(value))
				return parseStringNode(node);
			else if (isItemStackValue(value))
				return parseItemStackNode(node);
			else if (isFluidStackValue(value))
				return parseFluidStackNode(node);
		} catch (Exception e) {
			throw new RuntimeException("Error when parsing entry: <" + value + ">", e);
		}

		return null;
	}

	public static String parseStringNode(XMLNode node) {
		return node.value.replace("\"", "");
	}

	static boolean isStringValue(String nodeValue) {
		String[] array = nodeValue.split(" ");
		return array[0].startsWith("\"") && array[0].endsWith("\"");
	}

	static boolean isItemStackValue(String nodeValue) {
		String[] array = nodeValue.split(" ");
		return array.length == 3 || array.length == 4 && array[3].startsWith("{");
	}

	public static ItemStack parseItemStackNode(XMLNode node) {
		String[] data = node.value.split(" ");
		Item item = (Item) Item.itemRegistry.getObject(data[0]);
		int size = Integer.parseInt(data[1]);
		int meta = Integer.parseInt(data[2]);
		ItemStack stack = new ItemStack(item, size, meta);
		if (data.length >= 4)
			try {
				NBTBase nbtbase = JsonToNBT.func_150315_a(data[3]);
				stack.setTagCompound((NBTTagCompound) nbtbase);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		return stack;
	}

	static boolean isFluidStackValue(String nodeValue) {
		String[] array = nodeValue.split(" ");
		return array.length == 2 || array.length == 3 && array[2].startsWith("{");
	}

	public static FluidStack parseFluidStackNode(XMLNode node) {
		String[] data = node.value.split(" ");
		Fluid fluid = FluidRegistry.getFluid(data[0]);
		int amount = Integer.parseInt(data[1]);
		FluidStack stack = new FluidStack(fluid, amount);
		if (data.length >= 3)
			try {
				NBTBase nbtbase = JsonToNBT.func_150315_a(data[2]);
				stack.tag = (NBTTagCompound) nbtbase;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		return stack;
	}
}