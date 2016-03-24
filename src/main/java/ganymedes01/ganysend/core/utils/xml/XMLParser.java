package ganymedes01.ganysend.core.utils.xml;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
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
			else if (isFluidStackValue(value))
				return parseFluidStackNode(node);
			else if (isItemStackValue(value))
				return parseItemStackNode(node);
			else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error parsing node: " + node, e);
		}
	}

	public static String parseStringNode(XMLNode node) {
		return node.value.replace("\"", "");
	}

	public static boolean isStringValue(String nodeValue) {
		return nodeValue.startsWith("\"") && nodeValue.endsWith("\"");
	}

	public static boolean isItemStackValue(String nodeValue) {
		String[] array = nodeValue.split(" ");
		return array.length == 3 || array.length >= 4 && array[3].startsWith("{");
	}

	public static ItemStack parseItemStackNode(XMLNode node) {
		String[] data = node.value.split(" ");
		Item item = Item.itemRegistry.getObject(new ResourceLocation(data[0]));
		int size = Integer.parseInt(data[1]);
		int meta = Integer.parseInt(data[2]);
		ItemStack stack = new ItemStack(item, size, meta);
		if (data.length >= 4)
			try {
				String nbt = "";
				for (int i = 3; i < data.length; i++)
					nbt += " " + data[i];
				NBTBase nbtbase = JsonToNBT.getTagFromJson(nbt);
				stack.setTagCompound((NBTTagCompound) nbtbase);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		return stack;
	}

	public static boolean isFluidStackValue(String nodeValue) {
		String[] array = nodeValue.split(" ");
		return array.length == 2 || array.length >= 3 && array[2].startsWith("{");
	}

	public static FluidStack parseFluidStackNode(XMLNode node) {
		String[] data = node.value.split(" ");
		Fluid fluid = FluidRegistry.getFluid(data[0]);
		int amount = Integer.parseInt(data[1]);
		FluidStack stack = new FluidStack(fluid, amount);
		if (data.length >= 3)
			try {
				String nbt = "";
				for (int i = 2; i < data.length; i++)
					nbt += " " + data[i];
				NBTBase nbtbase = JsonToNBT.getTagFromJson(nbt);
				stack.tag = (NBTTagCompound) nbtbase;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		return stack;
	}
}