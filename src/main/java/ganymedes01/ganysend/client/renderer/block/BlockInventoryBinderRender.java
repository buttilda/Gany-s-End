package ganymedes01.ganysend.client.renderer.block;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.ganysend.client.OpenGLHelper;
import ganymedes01.ganysend.lib.RenderIDs;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

@SideOnly(Side.CLIENT)
public class BlockInventoryBinderRender implements ISimpleBlockRenderingHandler {

	private final ModelBiped biped = new ModelBiped(0.0F);

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		Tessellator tessellator = Tessellator.instance;
		OpenGLHelper.translate(-0.5F, -0.5F, -0.5F);

		IIcon icon = block.getIcon(0, 0);

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0F, 0.0F, 0.0F, icon);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0F, 14.0F / 16.0F, 0.0F, icon);
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0F, 0.0F, 0.0F, icon);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0F, -14.0F / 16.0F, 0.0F, icon);
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, 0.0F, 0.0F, 0.0F, icon);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, -14.0F / 16.0F, 0.0F, 0.0F, icon);
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, 0.0F, 0.0F, 0.0F, icon);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, 14.0F / 16.0F, 0.0F, 0.0F, icon);
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceZPos(block, 0.0F, 0.0F, 0.0F, icon);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0F, -14.0F / 16.0F, icon);
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceZNeg(block, 0.0F, 0.0F, 0.0F, icon);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceZNeg(block, 0.0F, 0.0F, 14.0F / 16.0F, icon);
		tessellator.draw();

		biped.isChild = false;
		OpenGLHelper.colour(0xFFFFFF);
		OpenGLHelper.enableRescaleNormal();
		OpenGLHelper.translate(0.5, 0.75, 0.5);
		OpenGLHelper.scale(0.5, 0.5, 0.5);
		OpenGLHelper.scale(1, -1, -1);
		OpenGLHelper.rotate((float) (720.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL), 0, 1, 0);
		Minecraft.getMinecraft().renderEngine.bindTexture(AbstractClientPlayer.locationStevePng);
		biped.render(null, 0, 0, 0, 0, 0, 1F / 16F);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess blockAccess, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		renderer.renderAllFaces = true;

		renderer.setRenderBounds(0, 0, 0, 1, 1, 1);
		renderer.renderStandardBlock(block, x, y, z);

		renderer.setRenderBounds(1, 14F / 16F, 1, 0, 2F / 16F, 0);
		renderer.renderStandardBlock(block, x, y, z);

		renderer.setRenderBounds(1, 1, 14F / 16F, 0, 0, 2F / 16F);
		renderer.renderStandardBlock(block, x, y, z);

		renderer.setRenderBounds(14F / 16F, 1, 1, 2F / 16F, 0, 0);
		renderer.renderStandardBlock(block, x, y, z);

		renderer.renderAllFaces = false;
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return RenderIDs.INVENTORY_BINDER;
	}
}