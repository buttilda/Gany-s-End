package ganymedes01.ganysend.items.blocks;

import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class ItemEnderPearlBlock extends ItemBlock {

	public ItemEnderPearlBlock(Block block) {
		super(block);
		setHasSubtypes(true);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "tile." + (stack.getItemDamage() == 0 ? Utils.getUnlocalisedName(Strings.ENDERPEARL_BLOCK_NAME) : Utils.getUnlocalisedName(Strings.ENDERPEARL_BRICK_NAME));
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}
}