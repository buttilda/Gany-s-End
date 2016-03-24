package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.tileentities.TileEntitySpeedyHopper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class SpeedyHopper extends BasicFilteringHopper {

	public SpeedyHopper() {
		super();
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.SPEEDY_HOPPER_NAME));
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntitySpeedyHopper();
	}
}