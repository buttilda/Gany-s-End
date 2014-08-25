package ganymedes01.ganysend.client.renderer.block;

import ganymedes01.ganysend.lib.RenderIDs;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
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
public class BlockTimeManipulatorRender implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		GL11.glTranslatef(0, -0.25F, 0);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		GL11.glTranslated(-0.5, -0.5, -0.5);

		float pixel = 1.0F / 16.0F;

		// BOTTOM
		renderer.setRenderBounds(2 * pixel, 0.0F, 2 * pixel, 14 * pixel, 14 * pixel, 14 * pixel);
		BlockRendererHelper.renderSimpleBlock(block, 3, renderer);
		renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0F, pixel * 2, 1.0F);
		BlockRendererHelper.renderSimpleBlock(block, 3, renderer);

		// TOP
		GL11.glTranslatef(0.0F, 0.87F, 0.0F);
		renderer.setRenderBounds(2 * pixel, 0.0F, 2 * pixel, 14 * pixel, 14 * pixel, 14 * pixel);
		BlockRendererHelper.renderSimpleBlock(block, 7, renderer);
		renderer.setRenderBounds(0.0D, pixel * 14, 0.0D, 1.0F, 1.0F, 1.0F);
		BlockRendererHelper.renderSimpleBlock(block, 7, renderer);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess blockAccess, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		if (blockAccess.getBlockMetadata(x, y, z) < 4)
			renderBottom(renderer, block, x, y, z);
		else
			renderTop(renderer, block, x, y, z);

		return true;
	}

	private void renderTop(RenderBlocks renderer, Block block, int x, int y, int z) {
		float pixel = 1.0F / 16.0F;

		renderer.setRenderBounds(2 * pixel, 0.0F, 2 * pixel, 14 * pixel, 14 * pixel, 14 * pixel);
		renderer.renderStandardBlock(block, x, y, z);

		renderer.setRenderBounds(0.0D, pixel * 14, 0.0D, 1.0F, 1.0F, 1.0F);
		renderer.renderStandardBlock(block, x, y, z);
	}

	private void renderBottom(RenderBlocks renderer, Block block, int x, int y, int z) {
		float pixel = 1.0F / 16.0F;

		renderer.setRenderBounds(2 * pixel, 2 * pixel, 2 * pixel, 14 * pixel, 1.0F, 14 * pixel);
		renderer.renderStandardBlock(block, x, y, z);

		renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0F, pixel * 2, 1.0F);
		renderer.renderStandardBlock(block, x, y, z);
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return RenderIDs.TIME_MANIPULATOR;
	}
}