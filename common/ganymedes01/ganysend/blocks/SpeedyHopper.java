package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Reference;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.tileentities.TileEntitySpeedyHopper;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SpeedyHopper extends BasicFilteringHopper {

	public SpeedyHopper(int id) {
		super(id);
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.SPEEDY_HOPPER_NAME));
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntitySpeedyHopper();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return side == 1 ? blockTop : blockOutside;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		blockOutside = reg.registerIcon(Utils.getBlockTexture(Strings.SPEEDY_HOPPER_NAME, false));
		blockTop = reg.registerIcon("hopper_top");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getItemIconName() {
		return Reference.ITEM_BLOCK_TEXTURE_PATH + Strings.SPEEDY_HOPPER_NAME;
	}
}
