package ganymedes01.ganysend.client.renderer.block;

import ganymedes01.ganysend.client.OpenGLHelper;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.RenderIDs;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.FMLClientHandler;
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
public class BlockChestRenderer implements ISimpleBlockRenderingHandler {

	private final ModelChest chest = new ModelChest();
	private final ResourceLocation TEXTURE = Utils.getResource(Utils.getEntityTexture(Strings.ANCHORED_ENDER_CHEST_NAME + "_on"));

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		OpenGLHelper.pushMatrix();
		OpenGLHelper.rotate(90, 0, 1, 0);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(TEXTURE);
		OpenGLHelper.scale(1, -1, -1);
		OpenGLHelper.translate(-0.5F, -0.5F, -0.5F);
		chest.renderAll();
		OpenGLHelper.popMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		if (renderer.hasOverrideBlockTexture()) {
			renderer.setRenderBounds(1F / 16F, 0, 1F / 16F, 15F / 16F, 14F / 16F, 15F / 16F);
			return renderer.renderStandardBlock(block, x, y, z);
		}
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return RenderIDs.ANCHORED_ENDER_CHEST;
	}
}