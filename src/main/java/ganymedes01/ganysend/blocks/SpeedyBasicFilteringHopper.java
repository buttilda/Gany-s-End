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

public class SpeedyBasicFilteringHopper extends BasicFilteringHopper {

	public SpeedyBasicFilteringHopper() {
		super();
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.SPEEDY_BASIC_FILTERING_HOPPER_NAME));
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		TileEntityFilteringHopper tile = new TileEntityFilteringHopper();
		tile.setBasic();
		tile.setSpeedy();
		return tile;
	}
}