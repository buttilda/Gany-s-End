package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Reference;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.tileentities.TileEntityFilteringHopper;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class SpeedyBasicFilteringHopper extends BasicFilteringHopper {

	public SpeedyBasicFilteringHopper(int id) {
		super(id);
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.SPEEDY_BASIC_FILTERING_HOPPER_NAME));
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		TileEntityFilteringHopper tile = new TileEntityFilteringHopper();
		tile.setBasic();
		tile.setSpeedy();
		return tile;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return side == 1 ? blockTop : blockOutside;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		blockOutside = reg.registerIcon(Utils.getBlockTexture(Strings.BASIC_FILTERING_HOPPER_NAME, true) + "outside_speedy");
		blockTop = reg.registerIcon(Utils.getBlockTexture(Strings.BASIC_FILTERING_HOPPER_NAME, true) + "top");
		registerExtraIcons(reg);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getItemIconName() {
		return Reference.ITEM_BLOCK_TEXTURE_PATH + Strings.SPEEDY_BASIC_FILTERING_HOPPER_NAME;
	}
}
