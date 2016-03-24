package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.IConfigurable;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class EndstoneBrick extends Block implements IConfigurable {

	public EndstoneBrick() {
		super(Material.rock);
		setHardness(3.0F);
		setResistance(15.0F);
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.ENDSTONE_BRICK_NAME));
		setCreativeTab(GanysEnd.enableDecorativeBlocks ? GanysEnd.endTab : null);
	}

	@Override
	public boolean isEnabled() {
		return GanysEnd.enableDecorativeBlocks;
	}
}