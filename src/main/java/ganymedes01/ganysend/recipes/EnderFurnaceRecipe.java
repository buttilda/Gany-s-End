package ganymedes01.ganysend.recipes;

import ganymedes01.ganysend.core.utils.InventoryUtils;
import ganymedes01.ganysend.core.utils.xml.XMLBuilder;
import ganymedes01.ganysend.core.utils.xml.XMLNode;
import ganymedes01.ganysend.core.utils.xml.XMLParser;

import java.util.ArrayList;
import java.util.List;

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

	EnderFurnaceRecipe(XMLNode node) {
		output = XMLParser.parseItemStackNode(node.getNode("output"));
		List<Object> inputs = new ArrayList<Object>();
		for (int i = 1; i <= 4; i++) {
			XMLNode n = node.getNode("input" + i);
			if (n != null)
				inputs.add(XMLParser.parseNode(n));
		}

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

	public XMLNode getNode() {
		XMLBuilder builder = new XMLBuilder("recipe");
		builder.makeEntry("output", output);
		builder.makeEntries("input", inputs);

		return builder.toNode();
	}

	@Override
	public String toString() {
		return getNode().toString();
	}

	private boolean areStacksTheSame(Object obj, ItemStack target) {
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

	private Object getValidObject(Object obj) {
		if (obj instanceof Item)
			return new ItemStack((Item) obj);
		else if (obj instanceof Block)
			return new ItemStack((Block) obj);
		else if (obj instanceof ItemStack || obj instanceof String)
			return obj;

		return null;
	}
}