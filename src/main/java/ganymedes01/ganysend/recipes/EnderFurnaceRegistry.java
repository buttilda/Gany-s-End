package ganymedes01.ganysend.recipes;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.ModBlocks;
import ganymedes01.ganysend.ModItems;
import ganymedes01.ganysend.core.utils.xml.XMLNode;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class EnderFurnaceRegistry extends RecipeRegistry<EnderFurnaceRecipe> {

	public static final EnderFurnaceRegistry INSTANCE = new EnderFurnaceRegistry();

	EnderFurnaceRegistry() {
		super("EnderFurnace");
	}

	@Override
	protected XMLNode toXML(EnderFurnaceRecipe recipe) {
		return recipe.getNode();
	}

	@Override
	protected EnderFurnaceRecipe makeRecipe(XMLNode node) {
		return new EnderFurnaceRecipe(node);
	}

	@Override
	protected void addDefaultRecipes() {
		addRecipe(new ItemStack(Items.ender_pearl, 4), "itemSkull", "itemSkull");
		addRecipe(new ItemStack(Blocks.end_stone), Blocks.stone);
		if (GanysEnd.enableDecorativeBlocks) {
			addRecipe(new ItemStack(ModBlocks.endstone_bricks), Blocks.stonebrick);
			addRecipe(new ItemStack(ModBlocks.enderpearl_block, 1, 1), ModBlocks.enderpearl_block);
		}
		addRecipe(new ItemStack(Blocks.stone), Blocks.netherrack);
		addRecipe(new ItemStack(Blocks.stonebrick), Blocks.nether_brick);
		if (GanysEnd.enableEndium)
			addRecipe(new ItemStack(ModItems.endiumIngot, 2), "oreEndium");
		if (GanysEnd.enableShifters) {
			ItemStack enderTag = new ItemStack(ModItems.enderTag);
			enderTag.setTagCompound(new NBTTagCompound());
			addRecipe(enderTag, Items.paper);
		}
		addRecipe(new ItemStack(Blocks.mycelium), Blocks.grass);
		addRecipe(new ItemStack(Items.emerald), Items.diamond, Items.gold_ingot, "itemSkull", new ItemStack(Items.potionitem, 1, 8196));
		addRecipe(new ItemStack(Items.diamond), Items.emerald, Items.gold_ingot, "itemSkull", new ItemStack(Items.potionitem, 1, 8194));
		if (GanysEnd.enableEndiumTools || GanysEnd.enableScythe)
			addRecipe(new ItemStack(Items.experience_bottle), new ItemStack(Items.potionitem, 1, 8197), ModItems.endstoneRod);
		addRecipe(new ItemStack(Items.ender_pearl), Items.ghast_tear, Items.sugar, Items.glowstone_dust, Items.experience_bottle);
		if (GanysEnd.enableTimeManipulator)
			addRecipe(new ItemStack(Blocks.dragon_egg), ModBlocks.time_manipulator);
		addRecipe(new ItemStack(Items.cooked_fish), Items.fish);
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
		for (int i = 0; i < 16; i++)
			if (i == 4)
				addRecipe(new ItemStack(Items.dye, 2, i), new ItemStack(Items.dye, 1, i), "flowerEnder", "flowerEnder");
			else
				addRecipe(new ItemStack(Items.dye, 2, i), new ItemStack(Items.dye, 1, i), "flowerEnder");
		if (GanysEnd.enableEndium)
			addRecipe(new ItemStack(ModBlocks.raw_endium), "oreDiamond");
	}

	public void addRecipe(ItemStack output, Object... input) {
		addRecipe(new EnderFurnaceRecipe(output, input));
	}

	public ItemStack getOuput(ItemStack... stacks) {
		for (EnderFurnaceRecipe recipe : getRecipes())
			if (recipe.matches(stacks))
				return recipe.getOutput();

		return null;
	}
}