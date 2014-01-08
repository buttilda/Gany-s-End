package ganymedes01.ganysend.client.renderer.block;

import ganymedes01.ganysend.lib.RenderIDs;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
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
		Tessellator tessellator = Tessellator.instance;
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		GL11.glTranslatef(-0.5F, -1.0F, -0.5F);
		float x = 0.0F;
		float y = 0.0F;
		float z = 0.0F;

		float pixel = 1.0F / 16.0F;
		float offset = pixel * 2;

		// BOTTOM
		renderer.setRenderBounds(0.0D, pixel * 2, 0.0D, 1.0F, 1.0F, 1.0F);
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYNeg(block, x, y, z, block.getIcon(0, 1));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZPos(block, x, y, z - offset, block.getIcon(2, 0));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZNeg(block, x, y, z + offset, block.getIcon(1, 0));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, x + offset, y, z, block.getIcon(3, 0));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, x - offset, y, z, block.getIcon(0, 0));
		tessellator.draw();

		renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0F, pixel * 2, 1.0F);
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZPos(block, x, y, z, block.getIcon(0, 0));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZNeg(block, x, y, z, block.getIcon(2, 0));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, x, y, z, block.getIcon(1, 0));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, x, y, z, block.getIcon(3, 0));
		tessellator.draw();

		// SURFACE
		renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0F, 2 * pixel, 1.0F);
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, x, y, z, block.getIcon(0, 1));
		tessellator.draw();

		// TOP

		GL11.glTranslatef(0.0F, 1.0F, 0.0F);
		renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0F, 1.0F, 1.0F);
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, x, y, z, block.getIcon(0, 1));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZPos(block, x, y, z - offset, block.getIcon(6, 0));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZNeg(block, x, y, z + offset, block.getIcon(5, 0));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, x + offset, y, z, block.getIcon(7, 0));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, x - offset, y, z, block.getIcon(4, 0));
		tessellator.draw();

		renderer.setRenderBounds(0.0D, 14 * pixel, 0.0D, 1.0F, 1.0F, 1.0F);
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZPos(block, x, y, z, block.getIcon(6, 0));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZNeg(block, x, y, z, block.getIcon(5, 0));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, x, y, z, block.getIcon(7, 0));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, x, y, z, block.getIcon(4, 0));
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYNeg(block, x, y, z, block.getIcon(0, 1));
		tessellator.draw();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess blockAccess, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(block.getMixedBrightnessForBlock(blockAccess, x, y, z));
		int colorMult = block.colorMultiplier(blockAccess, x, y, z);
		float R = (colorMult >> 16 & 255) / 255.0F;
		float G = (colorMult >> 8 & 255) / 255.0F;
		float B = (colorMult & 255) / 255.0F;

		if (EntityRenderer.anaglyphEnable) {
			R = (R * 30.0F + G * 59.0F + B * 11.0F) / 100.0F;
			G = (R * 30.0F + G * 70.0F) / 100.0F;
			B = (R * 30.0F + B * 70.0F) / 100.0F;
		}

		tessellator.setColorOpaque_F(R, G, B);
		int meta = blockAccess.getBlockMetadata(x, y, z);

		int frontID = 0;
		int backID = 0;
		int rightID = 0;
		int leftID = 0;

		if (meta >= 4)
			meta -= 4;

		switch (meta) {
			case 0:
				frontID = 0;
				leftID = 2;
				rightID = 1;
				backID = 3;
				break;
			case 1:
				frontID = 1;
				leftID = 0;
				rightID = 3;
				backID = 2;
				break;
			case 2:
				frontID = 3;
				leftID = 1;
				rightID = 2;
				backID = 0;
				break;
			case 3:
				frontID = 2;
				leftID = 3;
				rightID = 0;
				backID = 1;
				break;
		}

		meta = blockAccess.getBlockMetadata(x, y, z);
		Icon front = block.getIcon(frontID, 0);
		Icon left = block.getIcon(leftID, 0);
		Icon right = block.getIcon(rightID, 0);
		Icon back = block.getIcon(backID, 0);
		if (meta < 4)
			renderBottom(renderer, block, x, y, z, front, left, right, back);
		else {
			front = block.getIcon(frontID + 4, 0);
			left = block.getIcon(leftID + 4, 0);
			right = block.getIcon(rightID + 4, 0);
			back = block.getIcon(backID + 4, 0);
			renderTop(renderer, block, x, y, z, front, left, right, back);
		}
		return true;
	}

	private void renderTop(RenderBlocks renderer, Block block, int x, int y, int z, Icon front, Icon left, Icon right, Icon back) {
		float pixel = 1.0F / 16.0F;

		renderer.setRenderBounds(2 * pixel, 0.0F, 2 * pixel, 14 * pixel, 14 * pixel, 14 * pixel);
		renderer.renderFaceXPos(block, x, y, z, right);
		renderer.renderFaceXNeg(block, x, y, z, left);
		renderer.renderFaceZPos(block, x, y, z, front);
		renderer.renderFaceZNeg(block, x, y, z, back);

		renderer.setRenderBounds(0.0D, pixel * 14, 0.0D, 1.0F, 1.0F, 1.0F);
		renderer.renderFaceXPos(block, x, y, z, right);
		renderer.renderFaceXNeg(block, x, y, z, left);
		renderer.renderFaceZPos(block, x, y, z, front);
		renderer.renderFaceZNeg(block, x, y, z, back);

		Icon surface = block.getIcon(0, 1);
		renderer.renderFaceYPos(block, x, y, z, surface);
		renderer.renderFaceYNeg(block, x, y, z, surface);
	}

	private void renderBottom(RenderBlocks renderer, Block block, int x, int y, int z, Icon front, Icon left, Icon right, Icon back) {
		float pixel = 1.0F / 16.0F;

		renderer.setRenderBounds(2 * pixel, 2 * pixel, 2 * pixel, 14 * pixel, 1.0F, 14 * pixel);
		renderer.renderFaceXPos(block, x, y, z, right);
		renderer.renderFaceXNeg(block, x, y, z, left);
		renderer.renderFaceZPos(block, x, y, z, front);
		renderer.renderFaceZNeg(block, x, y, z, back);

		renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0F, pixel * 2, 1.0F);
		renderer.renderFaceXPos(block, x, y, z, right);
		renderer.renderFaceXNeg(block, x, y, z, left);
		renderer.renderFaceZPos(block, x, y, z, front);
		renderer.renderFaceZNeg(block, x, y, z, back);

		Icon surface = block.getIcon(0, 1);
		renderer.renderFaceYPos(block, x, y, z, surface);
		renderer.renderFaceYNeg(block, x, y, z, surface);
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public int getRenderId() {
		return RenderIDs.TIME_MANIPULATOR;
	}
}