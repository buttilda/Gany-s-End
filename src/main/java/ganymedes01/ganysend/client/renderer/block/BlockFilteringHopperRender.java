package ganymedes01.ganysend.client.renderer.block;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.blocks.BasicFilteringHopper;
import ganymedes01.ganysend.lib.RenderIDs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHopper;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

@SideOnly(Side.CLIENT)
public class BlockFilteringHopperRender implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		GL11.glTranslated(-0.5, -0.5, -0.5);
		renderHopperInventory(renderer, (BasicFilteringHopper) block);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess blockAccess, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		return renderHopperWorld(renderer, (BasicFilteringHopper) block, x, y, z, blockAccess.getBlockMetadata(x, y, z));
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return !GanysEnd.enable2DHoppers;
	}

	@Override
	public int getRenderId() {
		return RenderIDs.FILTERING_HOPPER;
	}

	public boolean renderHopperWorld(RenderBlocks renderer, BasicFilteringHopper hopper, int x, int y, int z, int meta) {
		Tessellator tess = Tessellator.instance;
		double d0 = 0.625D;
		renderer.setRenderBounds(0.0D, d0, 0.0D, 1.0D, 1.0D, 1.0D);
		renderer.renderStandardBlock(hopper, x, y, z);

		tess.setBrightness(hopper.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z));
		int j1 = hopper.colorMultiplier(renderer.blockAccess, x, y, z);
		float f = (j1 >> 16 & 255) / 255.0F;
		float f1 = (j1 >> 8 & 255) / 255.0F;
		float f2 = (j1 & 255) / 255.0F;

		if (EntityRenderer.anaglyphEnable) {
			float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
			float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
			float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
			f = f3;
			f1 = f4;
			f2 = f5;
		}

		tess.setColorOpaque_F(f, f1, f2);

		IIcon outside = hopper.getIconFromString("hopper_outside");
		IIcon inside = hopper.getIconFromString("hopper_inside");
		f1 = 0.125F;

		renderer.renderFaceXPos(hopper, x - 1.0F + f1, y, z, outside);
		renderer.renderFaceXNeg(hopper, x + 1.0F - f1, y, z, outside);
		renderer.renderFaceZPos(hopper, x, y, z - 1.0F + f1, outside);
		renderer.renderFaceZNeg(hopper, x, y, z + 1.0F - f1, outside);
		renderer.renderFaceYPos(hopper, x, y - 1.0F + d0, z, inside);

		renderer.setOverrideBlockTexture(outside);
		double d3 = 0.25D;
		double d4 = 0.25D;
		renderer.setRenderBounds(d3, d4, d3, 1.0D - d3, d0 - 0.002D, 1.0D - d3);
		renderer.renderStandardBlock(hopper, x, y, z);

		double d1 = 0.375D;
		double d2 = 0.25D;
		renderer.setOverrideBlockTexture(outside);

		int dir = BlockHopper.getDirectionFromMetadata(meta);
		if (dir == 0) {
			renderer.setRenderBounds(d1, 0.0D, d1, 1.0D - d1, 0.25D, 1.0D - d1);
			renderer.renderStandardBlock(hopper, x, y, z);
		}
		if (dir == 2) {
			renderer.setRenderBounds(d1, d4, 0.0D, 1.0D - d1, d4 + d2, d3);
			renderer.renderStandardBlock(hopper, x, y, z);
		}
		if (dir == 3) {
			renderer.setRenderBounds(d1, d4, 1.0D - d3, 1.0D - d1, d4 + d2, 1.0D);
			renderer.renderStandardBlock(hopper, x, y, z);
		}
		if (dir == 4) {
			renderer.setRenderBounds(0.0D, d4, d1, d3, d4 + d2, 1.0D - d1);
			renderer.renderStandardBlock(hopper, x, y, z);
		}
		if (dir == 5) {
			renderer.setRenderBounds(1.0D - d3, d4, d1, 1.0D, d4 + d2, 1.0D - d1);
			renderer.renderStandardBlock(hopper, x, y, z);
		}

		renderer.clearOverrideBlockTexture();
		return true;
	}

	public boolean renderHopperInventory(RenderBlocks renderer, BasicFilteringHopper hopper) {
		Tessellator tess = Tessellator.instance;

		IIcon outside = hopper.getIconFromString("hopper_outside");
		IIcon inside = hopper.getIconFromString("hopper_inside");

		// TOP
		double d0 = 0.625D;
		renderer.setRenderBounds(0.0D, d0, 0.0D, 1.0D, 1.0D, 1.0D);
		BlockRendererHelper.renderSimpleBlock(hopper, 0, renderer);

		// INSIDE
		float f1 = 0.125F;
		tess.startDrawingQuads();
		tess.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(hopper, -1.0F + f1, 0.0D, 0.0D, outside);
		tess.draw();
		tess.startDrawingQuads();
		tess.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(hopper, 1.0F - f1, 0.0D, 0.0D, outside);
		tess.draw();
		tess.startDrawingQuads();
		tess.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZPos(hopper, 0.0D, 0.0D, -1.0F + f1, outside);
		tess.draw();
		tess.startDrawingQuads();
		tess.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZNeg(hopper, 0.0D, 0.0D, 1.0F - f1, outside);
		tess.draw();
		tess.startDrawingQuads();
		tess.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(hopper, 0.0D, -1.0D + d0, 0.0D, inside);
		tess.draw();

		// BASE
		double d3 = 0.25D;
		double d4 = 0.25D;
		renderer.setRenderBounds(d3, d4, d3, 1.0D - d3, d0 - 0.002D, 1.0D - d3);
		BlockRendererHelper.renderSimpleBlock(hopper, outside, renderer);

		// END
		double d1 = 0.375D;
		renderer.setRenderBounds(d1, 0.0D, d1, 1.0D - d1, 0.25D, 1.0D - d1);
		BlockRendererHelper.renderSimpleBlock(hopper, outside, renderer);

		return true;
	}
}