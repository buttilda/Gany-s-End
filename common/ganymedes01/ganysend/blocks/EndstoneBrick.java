package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class EndstoneBrick extends Block {

	public EndstoneBrick(int id) {
		super(id, Material.rock);
		setHardness(3.0F);
		setResistance(15.0F);
		setCreativeTab(GanysEnd.endTab);
		setTextureName(Utils.getBlockTexture(Strings.ENDSTONE_BRICK_NAME, false));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.ENDSTONE_BRICK_NAME));
	}
}
