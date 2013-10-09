package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.ModIDs;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class EndstoneBrick extends Block {

	public EndstoneBrick() {
		super(ModIDs.ENDSTONE_BRICK_ID, Material.rock);
		setHardness(3.0F);
		setResistance(15.0F);
		setCreativeTab(GanysEnd.endTab);
		setTextureName(Utils.getBlockTexture(Strings.ENDSTONE_BRICK_NAME, false));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.ENDSTONE_BRICK_NAME));
	}
}
