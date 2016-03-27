package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.GUIsID;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.tileentities.TileEntityAdvancedFilteringHopper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class AdvancedFilteringHopper extends BasicFilteringHopper {

	public AdvancedFilteringHopper() {
		super();
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.ADVANCED_FILTERING_HOPPER_NAME));
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		TileEntityAdvancedFilteringHopper tile = Utils.getTileEntity(world, pos, TileEntityAdvancedFilteringHopper.class);
		if (tile != null)
			player.openGui(GanysEnd.instance, GUIsID.ADVANCED_FILTERING_HOPPER, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		TileEntityAdvancedFilteringHopper tile = new TileEntityAdvancedFilteringHopper();
		tile.setBasic();
		return tile;
	}
}