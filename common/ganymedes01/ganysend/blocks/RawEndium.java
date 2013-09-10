package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class RawEndium extends Block {

	public RawEndium(int id) {
		super(id, Material.iron);
		setHardness(51F);
		setResistance(2001.0F);
		setCreativeTab(GanysEnd.endTab);
		setTextureName(Utils.getBlockTexture(Strings.RAW_ENDIUM_NAME, false));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.RAW_ENDIUM_NAME));
	}
}
