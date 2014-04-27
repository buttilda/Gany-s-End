package ganymedes01.ganysend.client.renderer.tileentity;

import ganymedes01.ganysend.client.model.ModelInfiniteWaterSource;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

@SideOnly(Side.CLIENT)
public class TileEntityInfiniteWaterSourceRender extends TileEntitySpecialRenderer {

	private final ModelInfiniteWaterSource model = new ModelInfiniteWaterSource();

	private int textureOffsetY = 0;
	private int coolDown = 10;

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float angle) {
		GL11.glPushMatrix();
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glTranslatef((float) x, (float) y + 1.0F, (float) z + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);

		bindTexture(Utils.getResource(Utils.getEntityTexture(Strings.INFINITE_WATER_SOURCE_NAME)));
		model.renderAxis();
		bindTexture(Utils.getResource(Utils.getEntityTexture(Strings.INFINITE_WATER_SOURCE_NAME + "_core")));
		coolDown--;
		if (coolDown <= 0) {
			textureOffsetY += 16;
			if (textureOffsetY >= 512)
				textureOffsetY = 0;
			coolDown = 10;
		}
		model.renderCore(textureOffsetY);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}
}