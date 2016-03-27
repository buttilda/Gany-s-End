package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.tileentities.TileEntityCreativeSpeedyHopper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class CreativeSpeedyHopper extends SpeedyHopper {

	public CreativeSpeedyHopper() {
		super();
		setBlockUnbreakable();
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.CREATIVE_SPEEDY_HOPPER_NAME));
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityCreativeSpeedyHopper();
	}
}