package ganymedes01.ganysend.client.renderer.tileentity;

import org.lwjgl.opengl.GL11;

import ganymedes01.ganysend.client.OpenGLHelper;
import ganymedes01.ganysend.tileentities.TileEntityInfiniteWaterSource;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

@SideOnly(Side.CLIENT)
public class TileEntityInfiniteWaterSourceRender extends TileEntitySpecialRenderer<TileEntityInfiniteWaterSource> {

	@Override
	public void renderTileEntityAt(TileEntityInfiniteWaterSource tile, double x, double y, double z, float partialTick, int destroyStage) {
		TileEntityInfiniteWaterSource source = tile;
		FluidStack fluid = source.getFluidForRendering();
		if (fluid == null)
			return;

		OpenGLHelper.pushMatrix();
		OpenGLHelper.enableBlend();
		OpenGLHelper.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		OpenGLHelper.translate(x, y + 1, z + 1);
		OpenGLHelper.scale(1.0F, -1.0F, -1.0F);
		bindTexture(TextureMap.locationBlocksTexture);
		//		renderCore(renderer, fluid.getFluid().getStillIcon(), source.getBlockType().getIcon(0, 0), source.getFluid().getFluid().getColor(source.getFluid()));
		OpenGLHelper.disableBlend();
		OpenGLHelper.popMatrix();
	}

	//	public static void renderCore(RenderBlocks renderer, IIcon fluidIcon, IIcon coreIcon, int colour) {
	//		float rotation = (float) (2 * 720.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL);
	//
	//		OpenGLHelper.translate(0.5, 0.5, 0.5);
	//		OpenGLHelper.scale(0.5, 0.5, 0.5);
	//
	//		OpenGLHelper.rotate(rotation, 1, 0, 0);
	//		OpenGLHelper.rotate(rotation, 0, 1, 0);
	//		OpenGLHelper.rotate(rotation, 0, 0, 1);
	//
	//		OpenGLHelper.translate(-0.5, -0.5, -0.5);
	//
	//		renderer.setRenderBounds(0, 0, 0, 1, 1, 1);
	//
	//		drawCube(renderer, coreIcon);
	//
	//		if (fluidIcon != null) {
	//			double num = 0.002;
	//			renderer.setRenderBounds(num, num, num, 1 - num, 1 - num, 1 - num);
	//			OpenGLHelper.colour(colour);
	//			OpenGLHelper.disableLighting();
	//			drawCube(renderer, fluidIcon);
	//			OpenGLHelper.enableLighting();
	//		}
	//	}
	//
	//	private static void drawCube(RenderBlocks renderer, IIcon icon) {
	//		Tessellator tess = Tessellator.instance;
	//		tess.startDrawingQuads();
	//		tess.setNormal(0.0F, -1.0F, 0.0F);
	//		renderer.renderFaceYNeg(null, 0.0D, 0.0D, 0.0D, icon);
	//		tess.setNormal(0.0F, 1.0F, 0.0F);
	//		renderer.renderFaceYPos(null, 0.0D, 0.0D, 0.0D, icon);
	//		tess.setNormal(0.0F, 0.0F, -1.0F);
	//		renderer.renderFaceZNeg(null, 0.0D, 0.0D, 0.0D, icon);
	//		tess.setNormal(0.0F, 0.0F, 1.0F);
	//		renderer.renderFaceZPos(null, 0.0D, 0.0D, 0.0D, icon);
	//		tess.setNormal(-1.0F, 0.0F, 0.0F);
	//		renderer.renderFaceXNeg(null, 0.0D, 0.0D, 0.0D, icon);
	//		tess.setNormal(1.0F, 0.0F, 0.0F);
	//		renderer.renderFaceXPos(null, 0.0D, 0.0D, 0.0D, icon);
	//		tess.draw();
	//	}
}