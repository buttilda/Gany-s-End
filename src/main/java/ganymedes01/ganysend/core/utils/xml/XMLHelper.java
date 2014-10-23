package ganymedes01.ganysend.core.utils.xml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import net.minecraft.block.Block;
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

public class XMLHelper {

	public static BufferedWriter getWriter(File file, final String group, String comment) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(file)) {

			@Override
			public void close() throws IOException {
				write("</" + group + ">");
				super.close();
			}
		};
		bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		bw.newLine();
		bw.newLine();
		bw.write("<!--" + comment + "-->");
		bw.newLine();
		bw.newLine();
		bw.write("<" + group + ">");
		bw.newLine();

		return bw;
	}

	public static void writeNode(BufferedWriter bw, String node) throws IOException {
		bw.write("\t" + node.replace("\n", "\n\t"));
		bw.newLine();
		bw.newLine();
	}

	public static String readFile(File file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String l, line = "";
		boolean foundComment = false;
		while ((l = br.readLine()) != null)
			if (foundComment) {
				int ind = l.indexOf("-->");
				if (ind >= 0) {
					l = l.substring(ind + 3);
					line += l;
					foundComment = false;
				}
			} else {
				int ind = l.indexOf("<!--");
				if (ind >= 0) {
					foundComment = true;
					l = l.substring(0, ind);
				}
				line += l;
			}
		br.close();

		line = line.replace("\t", "");
		return line.substring(line.indexOf('>') + 1);
	}

	public static void getNodes(String str, List<XMLNode> list) {
		while (!str.isEmpty()) {
			String nodeName = str.substring(str.indexOf('<') + 1, str.indexOf('>'));
			XMLNode node = new XMLNode(getName(nodeName));
			stripProperties(nodeName, node.properties);

			String str2 = getNodeText(nodeName, str);
			if (str2.startsWith("<"))
				getNodes(str2, node.nodes);
			else
				node.value = str2;

			list.add(node);
			String s = "</" + node.name + ">";
			str = str.substring(str.indexOf(s) + s.length());
		}
	}

	private static String getNodeText(String name, String str) {
		String b = "<" + name + ">";
		return str.substring(str.indexOf(b) + b.length(), str.indexOf("</" + getName(name) + ">")).trim();
	}

	private static String getName(String str) {
		int space = str.indexOf(' ');
		if (space > 0)
			return str.substring(0, space);
		else
			return str;
	}

	private static List<XMLProperty> stripProperties(String str, List<XMLProperty> list) {
		String[] lines = str.split(" ");
		if (lines.length > 1)
			for (int i = 1; i < lines.length; i++)
				list.add(getProperty(lines[i]));

		return list;
	}

	private static XMLProperty getProperty(String str) {
		String[] data = str.split("=");
		return new XMLProperty(data[0], fixPropValue(data[1]));
	}

	private static String fixPropValue(String value) {
		if (value.startsWith("\""))
			value = value.substring(1);
		if (value.endsWith("\""))
			value = value.substring(0, value.length() - 1);

		return value;
	}

	public static Object processEntry(XMLNode node, Type type) {
		if (!node.hasValue())
			return null;

		String value = node.value;
		if (value.startsWith("\"") && value.endsWith("\""))
			return value.replace("\"", "");

		String[] data = value.split(" ");
		if (type.equals(FluidStack.class)) {
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
		} else
			try {
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
			} catch (NumberFormatException e) {
				throw new RuntimeException("Error when parsing entry: <" + value + ">", e);
			} catch (ArrayIndexOutOfBoundsException e) {
				throw new RuntimeException("Error when parsing entry: <" + value + ">", e);
			}
	}

	public static String toNodeValue(Object obj) {
		obj = fixObj(obj);

		if (obj instanceof ItemStack) {
			ItemStack stack = (ItemStack) obj;
			return Item.itemRegistry.getNameForObject(stack.getItem()) + " " + stack.stackSize + " " + stack.getItemDamage() + (stack.hasTagCompound() ? " " + stack.getTagCompound() : "");
		} else if (obj instanceof String)
			return "\"" + obj + "\"";
		else if (obj instanceof FluidStack) {
			FluidStack stack = (FluidStack) obj;
			return FluidRegistry.getFluidName(stack.fluidID) + " " + stack.amount + (stack.tag != null ? " " + stack.tag : "");
		}

		return obj.toString();
	}

	private static Object fixObj(Object obj) {
		if (obj instanceof Block)
			return new ItemStack((Block) obj);
		else if (obj instanceof Item)
			return new ItemStack((Item) obj);

		return obj;
	}
}