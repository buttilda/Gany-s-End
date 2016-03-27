package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.tileentities.TileEntityFilteringHopper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class SpeedyExclusiveFilteringHopper extends BasicFilteringHopper {

	public SpeedyExclusiveFilteringHopper() {
		super();
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.SPEEDY_EXCLUSIVE_FILTERING_HOPPER_NAME));
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		TileEntityFilteringHopper tile = new TileEntityFilteringHopper(1);
		tile.setExclusive();
		tile.setSpeedy();
		return tile;
	}
}