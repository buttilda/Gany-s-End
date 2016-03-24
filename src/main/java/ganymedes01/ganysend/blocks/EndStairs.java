package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.IConfigurable;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class EndStairs extends BlockStairs implements IConfigurable {

	public EndStairs(IBlockState state) {
		super(state);
		setHardness(1.5F);
		setLightOpacity(0);
		setResistance(10.0F);
		setCreativeTab(GanysEnd.enableDecorativeBlocks ? GanysEnd.endTab : null);
	}

	@Override
	public boolean isEnabled() {
		return GanysEnd.enableDecorativeBlocks;
	}
}