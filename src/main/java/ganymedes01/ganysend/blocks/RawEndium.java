package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.RenderIDs;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.world.IBlockAccess;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class RawEndium extends Block {

	public RawEndium() {
		super(Material.iron);
		setHardness(51F);
		setResistance(2001.0F);
		setHarvestLevel("pickaxe", 3);
		setCreativeTab(GanysEnd.enableEndium ? GanysEnd.endTab : null);
		setBlockName(Utils.getUnlocalisedName(Strings.RAW_ENDIUM_NAME));
		setBlockTextureName(Utils.getBlockTexture(Strings.RAW_ENDIUM_NAME));
	}

	@Override
	public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity) {
		return !(entity instanceof EntityDragon);
	}

	@Override
	public int getRenderType() {
		return RenderIDs.RAW_ENDIUM;
	}
}