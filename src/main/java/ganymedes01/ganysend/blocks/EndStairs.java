package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class EndStairs extends BlockStairs {

	public EndStairs(Block block, int meta) {
		super(block, meta);
		setHardness(1.5F);
		setLightOpacity(0);
		setResistance(10.0F);
		setCreativeTab(GanysEnd.enableDecorativeBlocks ? GanysEnd.endTab : null);
	}
}