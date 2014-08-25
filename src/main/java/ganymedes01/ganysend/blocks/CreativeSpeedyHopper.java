package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.tileentities.TileEntityCreativeSpeedyHopper;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class CreativeSpeedyHopper extends SpeedyHopper {

	public CreativeSpeedyHopper() {
		super();
		setBlockName(Utils.getUnlocalizedName(Strings.CREATIVE_SPEEDY_HOPPER_NAME));
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityCreativeSpeedyHopper();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		super.registerBlockIcons(reg);
		blockOutside = reg.registerIcon(Utils.getBlockTexture(Strings.CREATIVE_SPEEDY_HOPPER_NAME));
	}
}