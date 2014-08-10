package ganymedes01.ganysend.client.renderer.block;

import ganymedes01.ganysend.ModBlocks;
import ganymedes01.ganysend.blocks.InfiniteWaterSource;
import ganymedes01.ganysend.client.renderer.tileentity.TileEntityInfiniteWaterSourceRender;
import ganymedes01.ganysend.lib.RenderIDs;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fluids.FluidRegistry;

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
public class BlockInfiniteWaterSourceRender implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		// AXIS
		renderer.setOverrideBlockTexture(InfiniteWaterSource.axis);
		renderer.setRenderBounds(7F / 16F, 1F / 16F, 7F / 16F, 9F / 16F, 15F / 16F, 9F / 16F);
		BlockRendererHelper.renderSimpleBlock(block, metadata, renderer);
		renderer.setRenderBounds(1F / 16F, 7F / 16F, 7F / 16F, 15F / 16F, 9F / 16F, 9F / 16F);
		BlockRendererHelper.renderSimpleBlock(block, metadata, renderer);
		renderer.setRenderBounds(7F / 16F, 7F / 16F, 1F / 16F, 9F / 16F, 9F / 16F, 15F / 16F);
		BlockRendererHelper.renderSimpleBlock(block, metadata, renderer);

		// NODES
		renderer.setOverrideBlockTexture(InfiniteWaterSource.node);
		renderer.setRenderBounds(5F / 16F, 5F / 16F, 0.75F / 16F, 11F / 16F, 11F / 16F, 1.75F / 16F);
		BlockRendererHelper.renderSimpleBlock(block, metadata, renderer);
		renderer.setRenderBounds(5F / 16F, 5F / 16F, 14.25F / 16F, 11F / 16F, 11F / 16F, 15.25F / 16F);
		BlockRendererHelper.renderSimpleBlock(block, metadata, renderer);
		renderer.setRenderBounds(0.75F / 16F, 5F / 16F, 5F / 16F, 1.75F / 16F, 11F / 16F, 11F / 16F);
		BlockRendererHelper.renderSimpleBlock(block, metadata, renderer);
		renderer.setRenderBounds(14.25F / 16F, 5F / 16F, 5F / 16F, 15.25F / 16F, 11F / 16F, 11F / 16F);
		BlockRendererHelper.renderSimpleBlock(block, metadata, renderer);
		renderer.setRenderBounds(5F / 16F, 0.75F / 16F, 5F / 16F, 11F / 16F, 1.75F / 16F, 11F / 16F);
		BlockRendererHelper.renderSimpleBlock(block, metadata, renderer);
		renderer.setRenderBounds(5F / 16F, 14.25F / 16F, 5F / 16F, 11F / 16F, 15.25F / 16F, 11F / 16F);
		BlockRendererHelper.renderSimpleBlock(block, metadata, renderer);

		// HEADS
		renderer.setOverrideBlockTexture(InfiniteWaterSource.head);
		renderer.setRenderBounds(4F / 16F, 4F / 16F, 0, 12F / 16F, 12F / 16F, 1F / 16F);
		BlockRendererHelper.renderSimpleBlock(block, metadata, renderer);
		renderer.setRenderBounds(4F / 16F, 4F / 16F, 15F / 16F, 12F / 16F, 12F / 16F, 1);
		BlockRendererHelper.renderSimpleBlock(block, metadata, renderer);
		renderer.setRenderBounds(0, 4F / 16F, 4F / 16F, 1F / 16F, 12F / 16F, 12F / 16F);
		BlockRendererHelper.renderSimpleBlock(block, metadata, renderer);
		renderer.setRenderBounds(15F / 16F, 4F / 16F, 4F / 16F, 1, 12F / 16F, 12F / 16F);
		BlockRendererHelper.renderSimpleBlock(block, metadata, renderer);
		renderer.setRenderBounds(4F / 16F, 0, 4F / 16F, 12F / 16F, 1F / 16F, 12F / 16F);
		BlockRendererHelper.renderSimpleBlock(block, metadata, renderer);
		renderer.setRenderBounds(4F / 16F, 15F / 16F, 4F / 16F, 12F / 16F, 1, 12F / 16F);
		BlockRendererHelper.renderSimpleBlock(block, metadata, renderer);

		renderer.clearOverrideBlockTexture();

		// CORE
		if (block == ModBlocks.infiniteWaterSource) {
			GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			TileEntityInfiniteWaterSourceRender.renderCore(renderer, FluidRegistry.WATER.getStillIcon(), block.getIcon(0, 0), FluidRegistry.WATER.getColor());
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glPopMatrix();
		}
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess blockAccess, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		renderer.renderAllFaces = true;

		// AXIS
		renderer.setOverrideBlockTexture(InfiniteWaterSource.axis);
		renderer.setRenderBounds(7F / 16F, 1F / 16F, 7F / 16F, 9F / 16F, 15F / 16F, 9F / 16F);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(1F / 16F, 7F / 16F, 7F / 16F, 15F / 16F, 9F / 16F, 9F / 16F);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(7F / 16F, 7F / 16F, 1F / 16F, 9F / 16F, 9F / 16F, 15F / 16F);
		renderer.renderStandardBlock(block, x, y, z);

		// NODES
		renderer.setOverrideBlockTexture(InfiniteWaterSource.node);
		renderer.setRenderBounds(5F / 16F, 5F / 16F, 0.75F / 16F, 11F / 16F, 11F / 16F, 1.75F / 16F);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(5F / 16F, 5F / 16F, 14.25F / 16F, 11F / 16F, 11F / 16F, 15.25F / 16F);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(0.75F / 16F, 5F / 16F, 5F / 16F, 1.75F / 16F, 11F / 16F, 11F / 16F);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(14.25F / 16F, 5F / 16F, 5F / 16F, 15.25F / 16F, 11F / 16F, 11F / 16F);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(5F / 16F, 0.75F / 16F, 5F / 16F, 11F / 16F, 1.75F / 16F, 11F / 16F);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(5F / 16F, 14.25F / 16F, 5F / 16F, 11F / 16F, 15.25F / 16F, 11F / 16F);
		renderer.renderStandardBlock(block, x, y, z);

		// HEADS
		renderer.setOverrideBlockTexture(InfiniteWaterSource.head);
		renderer.setRenderBounds(4F / 16F, 4F / 16F, 0, 12F / 16F, 12F / 16F, 1F / 16F);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(4F / 16F, 4F / 16F, 15F / 16F, 12F / 16F, 12F / 16F, 1);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(0, 4F / 16F, 4F / 16F, 1F / 16F, 12F / 16F, 12F / 16F);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(15F / 16F, 4F / 16F, 4F / 16F, 1, 12F / 16F, 12F / 16F);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(4F / 16F, 0, 4F / 16F, 12F / 16F, 1F / 16F, 12F / 16F);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(4F / 16F, 15F / 16F, 4F / 16F, 12F / 16F, 1, 12F / 16F);
		renderer.renderStandardBlock(block, x, y, z);

		renderer.renderAllFaces = false;
		renderer.clearOverrideBlockTexture();
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return RenderIDs.INFINITE_WATER_SOURCE;
	}
}