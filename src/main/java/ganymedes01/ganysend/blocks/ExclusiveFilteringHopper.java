package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Reference;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.tileentities.TileEntityFilteringHopper;
import net.minecraft.client.renderer.texture.IIconRegister;
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

public class ExclusiveFilteringHopper extends BasicFilteringHopper {

	public ExclusiveFilteringHopper() {
		super();
		setBlockName(Utils.getUnlocalisedName(Strings.EXCLUSIVE_FILTERING_HOPPER_NAME));
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		TileEntityFilteringHopper tile = new TileEntityFilteringHopper();
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
		blockOutside = reg.registerIcon(Utils.getBlockTexture(Strings.EXCLUSIVE_FILTERING_HOPPER_NAME) + "_outside");
		blockTop = reg.registerIcon(Utils.getBlockTexture(Strings.EXCLUSIVE_FILTERING_HOPPER_NAME) + "_top");
		registerExtraIcons(reg);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getItemIconName() {
		return GanysEnd.enable2DHoppers ? Reference.ITEM_BLOCK_TEXTURE_PATH + Strings.EXCLUSIVE_FILTERING_HOPPER_NAME : null;
	}
}