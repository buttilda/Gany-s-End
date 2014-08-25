package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.GUIsID;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.tileentities.TileEntityAdvancedFilteringHopper;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class AdvancedExclusiveFilteringHopper extends BasicFilteringHopper {

	public AdvancedExclusiveFilteringHopper() {
		super();
		setBlockName(Utils.getUnlocalizedName(Strings.ADVANCED_EXCLUSIVE_FILTERING_HOPPER_NAME));
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		if (player.isSneaking())
			return false;
		else {
			TileEntityAdvancedFilteringHopper tile = Utils.getTileEntity(world, x, y, z, TileEntityAdvancedFilteringHopper.class);
			if (tile != null)
				player.openGui(GanysEnd.instance, GUIsID.ADVANCED_FILTERING_HOPPER, world, x, y, z);
			return true;
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		TileEntityAdvancedFilteringHopper tile = new TileEntityAdvancedFilteringHopper();
		tile.setExclusive();
		return tile;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return side == 1 ? blockTop : blockOutside;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		blockOutside = reg.registerIcon(Utils.getBlockTexture(Strings.ADVANCED_EXCLUSIVE_FILTERING_HOPPER_NAME));
		blockTop = reg.registerIcon(Utils.getBlockTexture(Strings.EXCLUSIVE_FILTERING_HOPPER_NAME) + "_top");
		registerExtraIcons(reg);
	}
}