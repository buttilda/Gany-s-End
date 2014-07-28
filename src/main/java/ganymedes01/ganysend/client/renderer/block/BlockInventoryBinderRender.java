package ganymedes01.ganysend.client.renderer.block;

import ganymedes01.ganysend.client.renderer.tileentity.TileEntityBlockSkullRender;
import ganymedes01.ganysend.lib.RenderIDs;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import com.mojang.authlib.GameProfile;

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
public class BlockInventoryBinderRender implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		Tessellator tessellator = Tessellator.instance;
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

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

		EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
		GameProfile profile = player != null ? player.getGameProfile() : null;
		TileEntityBlockSkullRender.instance.renderHead(0.25F, 0, 0, 6, 1, 3, profile);
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

		IIcon icon = block.getIcon(0, 0);
		float off = 14.0F / 16.0F;

		renderer.renderFaceXPos(block, x, y, z, icon);
		renderer.renderFaceXPos(block, x - off, y, z, icon);
		renderer.renderFaceXNeg(block, x, y, z, icon);
		renderer.renderFaceXNeg(block, x + off, y, z, icon);
		renderer.renderFaceZPos(block, x, y, z, icon);
		renderer.renderFaceZPos(block, x, y, z - off, icon);
		renderer.renderFaceZNeg(block, x, y, z, icon);
		renderer.renderFaceZNeg(block, x, y, z + off, icon);
		renderer.renderFaceYPos(block, x, y, z, icon);
		renderer.renderFaceYPos(block, x, y - off, z, icon);
		renderer.renderFaceYNeg(block, x, y, z, icon);
		renderer.renderFaceYNeg(block, x, y + off, z, icon);

		icon = block.getIcon(0, 1);
		renderer.renderFaceXPos(block, x - off, y, z, icon);
		renderer.renderFaceXNeg(block, x + off, y, z, icon);
		renderer.renderFaceZPos(block, x, y, z - off, icon);
		renderer.renderFaceZNeg(block, x, y, z + off, icon);
		renderer.renderFaceYPos(block, x, y - off, z, icon);
		renderer.renderFaceYNeg(block, x, y + off, z, icon);

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