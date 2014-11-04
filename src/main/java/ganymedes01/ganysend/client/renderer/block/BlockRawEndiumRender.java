package ganymedes01.ganysend.client.renderer.block;

import ganymedes01.ganysend.lib.RenderIDs;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.init.Blocks;
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
public class BlockRawEndiumRender implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int meta, int modelID, RenderBlocks renderer) {
		GL11.glTranslated(-0.5, -0.5, -0.5);
		BlockRendererHelper.renderSimpleBlock(Blocks.end_stone, meta, renderer);
		BlockRendererHelper.renderSimpleBlock(block, meta, renderer);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess blockAccess, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		boolean base = renderer.renderStandardBlock(Blocks.end_stone, x, y, z);
		boolean overlay = !renderer.hasOverrideBlockTexture() && renderer.renderStandardBlock(block, x, y, z);
		return base || overlay;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return RenderIDs.RAW_ENDIUM;
	}
}