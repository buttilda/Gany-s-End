package ganymedes01.ganysend.client.renderer.tileentity;

import ganymedes01.ganysend.client.model.ModelTimeLord;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

@SideOnly(Side.CLIENT)
public class TileEntityTimeLordRender extends TileEntitySpecialRenderer {

	private ModelTimeLord model;

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float angle) {
		model = new ModelTimeLord(tile.getBlockMetadata());
		if (tile.getBlockMetadata() > 3)
			bindTexture(Utils.getResource(Utils.getEntityTexture(Strings.TIME_MANIPULATOR_NAME + "_top")));
		else
			bindTexture(Utils.getResource(Utils.getEntityTexture(Strings.TIME_MANIPULATOR_NAME + "_bottom")));

		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glTranslatef((float) x, (float) y + 1.0F, (float) z + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		model.renderAll();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}
}
