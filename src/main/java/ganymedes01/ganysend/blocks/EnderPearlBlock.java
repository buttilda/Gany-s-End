package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.IConfigurable;
import ganymedes01.ganysend.ModBlocks.ISubBlocksBlock;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.items.blocks.ItemEnderPearlBlock;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class EnderPearlBlock extends Block implements ISubBlocksBlock, IConfigurable {

	public EnderPearlBlock() {
		super(Material.iron);
		setHardness(1.5F);
		setResistance(10.0F);
		setBlockName(Utils.getUnlocalisedName(Strings.ENDERPEARL_BLOCK_NAME));
		setCreativeTab(GanysEnd.enableDecorativeBlocks ? GanysEnd.endTab : null);
	}

	@Override
	public int damageDropped(int meta) {
		return meta > 1 ? 1 : meta;
	}

	@Override
	public Class<? extends ItemBlock> getItemBlockClass() {
		return ItemEnderPearlBlock.class;
	}

	@Override
	public boolean isEnabled() {
		return GanysEnd.enableDecorativeBlocks;
	}
}