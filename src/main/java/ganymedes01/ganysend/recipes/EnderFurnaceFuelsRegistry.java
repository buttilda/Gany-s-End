package ganymedes01.ganysend.recipes;

import ganymedes01.ganysend.core.utils.InventoryUtils;
import ganymedes01.ganysend.core.utils.xml.XMLBuilder;
import ganymedes01.ganysend.core.utils.xml.XMLNode;
import ganymedes01.ganysend.core.utils.xml.XMLParser;
import ganymedes01.ganysend.recipes.EnderFurnaceFuelsRegistry.FuelEntry;

import java.util.List;

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

public class EnderFurnaceFuelsRegistry extends RecipeRegistry<FuelEntry> {

	public static final EnderFurnaceFuelsRegistry INSTANCE = new EnderFurnaceFuelsRegistry();

	EnderFurnaceFuelsRegistry() {
		super("EnderFurnaceFuels");
	}

	@Override
	protected XMLNode toXML(FuelEntry recipe) {
		XMLBuilder builder = new XMLBuilder("entry");

		builder.makeEntry("fuel", recipe.fuel);
		builder.makeEntry("burnTime", recipe.burnTime);

		return builder.toNode();
	}

	@Override
	protected FuelEntry makeRecipe(XMLNode node) {
		return new FuelEntry(XMLParser.parseNode(node.getNode("fuel")), Integer.parseInt(node.getNode("burnTime").getValue()));
	}

	@Override
	protected void addDefaultRecipes() {
		addFuel(Items.ender_pearl, 1600);
		addFuel(Items.ender_eye, 2000);
		addFuel(Blocks.end_stone, 10);
		addFuel("flowerEnder", 100);
		addFuel("blockEnderPearl", 1600);
	}

	public int getBurnTime(ItemStack stack) {
		if (stack == null)
			return 0;
		for (FuelEntry entry : getRecipes())
			if (areStacksTheSame(entry.fuel, stack))
				return entry.burnTime;

		return 0;
	}

	public void addFuel(Object fuel, int burnTime) {
		addRecipe(new FuelEntry(fuel, burnTime));
	}

	public static class FuelEntry {

		final Object fuel;
		final int burnTime;

		FuelEntry(Object fuel, int burnTime) {
			if (fuel instanceof Item)
				this.fuel = new ItemStack((Item) fuel);
			else if (fuel instanceof Block)
				this.fuel = new ItemStack((Block) fuel);
			else
				this.fuel = fuel;

			this.burnTime = burnTime;
		}

		public Object getFuel() {
			return fuel;
		}
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
}