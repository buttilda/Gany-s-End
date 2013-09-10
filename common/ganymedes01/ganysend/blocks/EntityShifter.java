package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.tileentities.TileEntityEntityShifter;
import net.minecraft.client.renderer.texture.IconRegister;
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

public class EntityShifter extends BlockShifter {

	public EntityShifter(int id) {
		super(id);
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.ENTITY_SHIFTER_NAME));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		blockSide = reg.registerIcon(Utils.getBlockTexture(Strings.ENTITY_SHIFTER_NAME, true) + "side");
		blockBottom = reg.registerIcon(Utils.getBlockTexture(Strings.ENTITY_SHIFTER_NAME, true) + "bottom");
		blockTop = reg.registerIcon(Utils.getBlockTexture(Strings.ENTITY_SHIFTER_NAME, true) + "top");
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityEntityShifter();
	}
}
