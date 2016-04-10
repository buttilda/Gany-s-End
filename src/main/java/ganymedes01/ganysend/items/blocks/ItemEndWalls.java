package ganymedes01.ganysend.items.blocks;

import ganymedes01.ganysend.lib.Reference;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class ItemEndWalls extends ItemBlock {

	public ItemEndWalls(Block block) {
		super(block);
		setHasSubtypes(true);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int meta = stack.getItemDamage();
		String type = meta == 0 ? "endstone_bricks_walls" : "enderpearl_bricks_walls";
		return "tile." + Reference.MOD_ID + "." + type;
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}
}