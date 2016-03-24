package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.IConfigurable;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class RawEndium extends Block implements IConfigurable {

	public RawEndium() {
		super(Material.iron);
		setHardness(51F);
		setResistance(2001.0F);
		setHarvestLevel("pickaxe", 3);
		setCreativeTab(GanysEnd.enableEndium ? GanysEnd.endTab : null);
		setBlockName(Utils.getUnlocalisedName(Strings.RAW_ENDIUM_NAME));
	}

	@Override
	public boolean canEntityDestroy(IBlockAccess world, BlockPos pos, Entity entity) {
		return !(entity instanceof EntityDragon);
	}

	@Override
	public boolean isEnabled() {
		return GanysEnd.enableEndium;
	}
}