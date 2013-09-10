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

public class ExclusiveFilteringHopper extends BasicFilteringHopper {

	public ExclusiveFilteringHopper(int id) {
		super(id);
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.EXCLUSIVE_FILTERING_HOPPER_NAME));
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		TileEntityFilteringHopper tile = new TileEntityFilteringHopper();
		tile.setExclusive();
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
		blockOutside = reg.registerIcon(Utils.getBlockTexture(Strings.EXCLUSIVE_FILTERING_HOPPER_NAME, true) + "outside");
		blockTop = reg.registerIcon(Utils.getBlockTexture(Strings.EXCLUSIVE_FILTERING_HOPPER_NAME, true) + "top");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getItemIconName() {
		return Reference.ITEM_BLOCK_TEXTURE_PATH + Strings.EXCLUSIVE_FILTERING_HOPPER_NAME;
	}
}
