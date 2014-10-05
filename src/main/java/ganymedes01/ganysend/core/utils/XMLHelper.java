package ganymedes01.ganysend.core.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */
public class XMLHelper {

	public static BufferedWriter getWriter(File file) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		bw.newLine();
		bw.newLine();

		return bw;
	}

	public static String readFile(File file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String l, line = null;
		while ((l = br.readLine()) != null)
			line += l;
		br.close();

		return line.replace("\t", "");
	}

	public static Iterator<String> getIterator(String key, String line) {
		return new XMLIterator(key, line);
	}

	public static Object processEntry(String entry) {
		if (entry.startsWith("\"") && entry.endsWith("\""))
			return entry.replace("\"", "");

		String[] data = entry.split(" ");
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

	public static String getEntry(String line, String entryName) {
		String start = "<" + entryName + ">";
		line = line.substring(line.indexOf(start) + start.length());
		line = line.substring(0, line.indexOf("</" + entryName + ">"));

		return line;
	}

	public static String encode(Object obj) {
		if (obj instanceof ItemStack) {
			ItemStack stack = (ItemStack) obj;
			return Item.itemRegistry.getNameForObject(stack.getItem()) + " " + stack.stackSize + " " + stack.getItemDamage() + (stack.hasTagCompound() ? " " + stack.getTagCompound() : "");
		} else if (obj instanceof String)
			return "\"" + obj + "\"";

		return obj.toString();
	}

	public static String makeGroup(String key, Object obj) {
		String str = XMLHelper.encode(obj);
		return "<" + key + ">\n" + str.substring(1, str.length() - 1) + "</" + key + ">";
	}

	public static String makeEntry(String key, Object obj) {
		return "\t<" + key + ">" + XMLHelper.encode(obj) + "</" + key + ">\n";
	}

	private static class XMLIterator implements Iterator<String> {

		int start = 0;
		String line, key;

		XMLIterator(String key, String line) {
			this.key = key;
			this.line = line;
		}

		@Override
		public boolean hasNext() {
			return (start = line.indexOf("<" + key + ">")) >= 0;
		}

		@Override
		public String next() {
			int end = line.indexOf("</" + key + ">");
			String str = line.substring(start, end + ("</" + key + ">").length());
			line = line.replace(str, "");
			return str.replace("<" + key + ">", "").replace("</" + key + ">", "");
		}

		@Override
		public void remove() {
		}
	}
}