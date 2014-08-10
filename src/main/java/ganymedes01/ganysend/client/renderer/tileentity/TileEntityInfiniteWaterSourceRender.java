package ganymedes01.ganysend.client.renderer.tileentity;

import ganymedes01.ganysend.tileentities.TileEntityInfiniteWaterSource;

import java.awt.Color;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidStack;

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

	private final RenderBlocks renderer = new RenderBlocks();

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float angle) {
		TileEntityInfiniteWaterSource source = (TileEntityInfiniteWaterSource) tile;
		FluidStack fluid = source.getFluidForRendering();
		if (fluid == null)
			return;

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glTranslatef((float) x, (float) y + 1.0F, (float) z + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		bindTexture(TextureMap.locationBlocksTexture);
		renderCore(renderer, fluid.getFluid().getStillIcon(), source.getBlockType().getIcon(0, 0), source.getFluid().getFluid().getColor(source.getFluid()));
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

	public static void renderCore(RenderBlocks renderer, IIcon fluidIcon, IIcon coreIcon, int colour) {
		float rotation = (float) (2 * 720.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL);

		GL11.glTranslated(0.5, 0.5, 0.5);
		GL11.glScaled(0.5, 0.5, 0.5);

		GL11.glRotated(rotation, 1, 0, 0);
		GL11.glRotated(rotation, 0, 1, 0);
		GL11.glRotated(rotation, 0, 0, 1);

		GL11.glTranslated(-0.5, -0.5, -0.5);

		renderer.setRenderBounds(0, 0, 0, 1, 1, 1);

		drawCube(renderer, coreIcon);

		double num = 0.002;
		renderer.setRenderBounds(num, num, num, 1 - num, 1 - num, 1 - num);
		Color c = new Color(colour);
		GL11.glColor3f(c.getRed() / 255F, c.getGreen() / 255F, c.getBlue() / 255F);
		GL11.glDisable(GL11.GL_LIGHTING);
		drawCube(renderer, fluidIcon);
		GL11.glEnable(GL11.GL_LIGHTING);
	}

	private static void drawCube(RenderBlocks renderer, IIcon icon) {
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		tess.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(null, 0.0D, 0.0D, 0.0D, icon);
		tess.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(null, 0.0D, 0.0D, 0.0D, icon);
		tess.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZNeg(null, 0.0D, 0.0D, 0.0D, icon);
		tess.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZPos(null, 0.0D, 0.0D, 0.0D, icon);
		tess.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(null, 0.0D, 0.0D, 0.0D, icon);
		tess.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(null, 0.0D, 0.0D, 0.0D, icon);
		tess.draw();
	}
}