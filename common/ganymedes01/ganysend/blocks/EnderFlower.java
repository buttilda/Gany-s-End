package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.ModIDs;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.world.World;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class EnderFlower extends BlockFlower {

	protected EnderFlower() {
		super(ModIDs.ENDER_FLOWER_ID);
		setLightValue(0.3F);
		setCreativeTab(GanysEnd.endTab);
		setTextureName(Utils.getBlockTexture(Strings.ENDER_FLOWER_NAME));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.ENDER_FLOWER_NAME));
	}

	@Override
	protected boolean canThisPlantGrowOnThisBlockID(int id) {
		return id == Block.whiteStone.blockID;
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z) {
		if (world.provider.dimensionId == 1) {
			Block soil = blocksList[world.getBlockId(x, y - 1, z)];
			return soil != null && soil.blockID == Block.whiteStone.blockID;
		} else
			return false;
	}
}