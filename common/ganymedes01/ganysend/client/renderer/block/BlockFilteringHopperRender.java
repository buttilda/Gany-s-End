package ganymedes01.ganysend.client.renderer.block;

import ganymedes01.ganysend.blocks.BasicFilteringHopper;
import ganymedes01.ganysend.lib.RenderIDs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHopper;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
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
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess blockAccess, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		BasicFilteringHopper hopper = (BasicFilteringHopper) block;

		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(hopper.getMixedBrightnessForBlock(blockAccess, x, y, z));
		int colour = hopper.colorMultiplier(blockAccess, x, y, z);
		float f1 = (colour >> 16 & 255) / 255.0F;
		float f2 = (colour >> 8 & 255) / 255.0F;
		float f3 = (colour & 255) / 255.0F;

		if (EntityRenderer.anaglyphEnable) {
			f1 = (f1 * 30.0F + f2 * 59.0F + f3 * 11.0F) / 100.0F;
			f2 = (f1 * 30.0F + f2 * 70.0F) / 100.0F;
			f3 = (f1 * 30.0F + f3 * 70.0F) / 100.0F;
		}

		tessellator.setColorOpaque_F(1.0F * f1, 1.0F * f2, 1.0F * f3);
		return renderBlockHopperMetadata(renderer, hopper, x, y, z, blockAccess.getBlockMetadata(x, y, z));
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return false;
	}

	@Override
	public int getRenderId() {
		return RenderIDs.filteringHopper;
	}

	public boolean renderBlockHopperMetadata(RenderBlocks renderer, BasicFilteringHopper hopper, int x, int y, int z, int meta) {
		Tessellator tessellator = Tessellator.instance;
		double d0 = 0.625D;
		renderer.setRenderBounds(0.0D, d0, 0.0D, 1.0D, 1.0D, 1.0D);
		renderer.renderStandardBlock(hopper, x, y, z);

		float f;

		tessellator.setBrightness(hopper.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z));
		float f1 = 1.0F;
		int j1 = hopper.colorMultiplier(renderer.blockAccess, x, y, z);
		f = (j1 >> 16 & 255) / 255.0F;
		float f2 = (j1 >> 8 & 255) / 255.0F;
		float f3 = (j1 & 255) / 255.0F;

		if (EntityRenderer.anaglyphEnable) {
			float f4 = (f * 30.0F + f2 * 59.0F + f3 * 11.0F) / 100.0F;
			float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
			float f6 = (f * 30.0F + f3 * 70.0F) / 100.0F;
			f = f4;
			f2 = f5;
			f3 = f6;
		}

		tessellator.setColorOpaque_F(f1 * f, f1 * f2, f1 * f3);

		Icon outside = hopper.getIconFromString("hopper_outside");
		Icon inside = hopper.getIconFromString("hopper_inside");
		f = 0.125F;

		renderer.renderFaceXPos(hopper, x - 1.0F + f, y, z, outside);
		renderer.renderFaceXNeg(hopper, x + 1.0F - f, y, z, outside);
		renderer.renderFaceZPos(hopper, x, y, z - 1.0F + f, outside);
		renderer.renderFaceZNeg(hopper, x, y, z + 1.0F - f, outside);
		renderer.renderFaceYPos(hopper, x, y - 1.0F + d0, z, inside);

		renderer.setOverrideBlockTexture(outside);
		double d1 = 0.25D;
		double d2 = 0.25D;
		renderer.setRenderBounds(d1, d2, d1, 1.0D - d1, d0 - 0.002D, 1.0D - d1);
		renderer.renderStandardBlock(hopper, x, y, z);
		double d3 = 0.375D;
		double d4 = 0.25D;
		renderer.setOverrideBlockTexture(outside);

		int dir = BlockHopper.getDirectionFromMetadata(meta);
		if (dir == 0) {
			renderer.setRenderBounds(d3, 0.0D, d3, 1.0D - d3, 0.25D, 1.0D - d3);
			renderer.renderStandardBlock(hopper, x, y, z);
		}
		if (dir == 2) {
			renderer.setRenderBounds(d3, d2, 0.0D, 1.0D - d3, d2 + d4, d1);
			renderer.renderStandardBlock(hopper, x, y, z);
		}
		if (dir == 3) {
			renderer.setRenderBounds(d3, d2, 1.0D - d1, 1.0D - d3, d2 + d4, 1.0D);
			renderer.renderStandardBlock(hopper, x, y, z);
		}
		if (dir == 4) {
			renderer.setRenderBounds(0.0D, d2, d3, d1, d2 + d4, 1.0D - d3);
			renderer.renderStandardBlock(hopper, x, y, z);
		}
		if (dir == 5) {
			renderer.setRenderBounds(1.0D - d1, d2, d3, 1.0D, d2 + d4, 1.0D - d3);
			renderer.renderStandardBlock(hopper, x, y, z);
		}

		renderer.clearOverrideBlockTexture();
		return true;
	}
}