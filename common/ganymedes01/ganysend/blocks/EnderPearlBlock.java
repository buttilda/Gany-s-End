package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class EnderPearlBlock extends Block {

	public EnderPearlBlock(int id) {
		super(id, Material.iron);
		setHardness(1.5F);
		setResistance(10.0F);
		setCreativeTab(GanysEnd.endTab);
	}
}
