package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Reference;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.tileentities.TileEntitySpeedyHopper;
import net.minecraft.block.BlockHopper;
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

public class SpeedyHopper extends BasicFilteringHopper {

	public SpeedyHopper() {
		super();
		setBlockName(Utils.getUnlocalizedName(Strings.SPEEDY_HOPPER_NAME));
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntitySpeedyHopper();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return side == 1 ? blockTop : blockOutside;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		blockOutside = reg.registerIcon(Utils.getBlockTexture(Strings.SPEEDY_HOPPER_NAME));
		blockTop = reg.registerIcon("hopper_top");
		registerExtraIcons(reg);
	}

	@Override
	protected void registerExtraIcons(IIconRegister reg) {
		blockBottom = BlockHopper.getHopperIcon("hopper_outside");
		blockInside = BlockHopper.getHopperIcon("hopper_inside");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getItemIconName() {
		return Reference.ITEM_BLOCK_TEXTURE_PATH + Strings.SPEEDY_HOPPER_NAME;
	}
}