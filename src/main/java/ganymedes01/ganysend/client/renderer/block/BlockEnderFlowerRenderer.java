package ganymedes01.ganysend.client.renderer.block;

import ganymedes01.ganysend.lib.RenderIDs;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
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
public class BlockEnderFlowerRenderer implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
		tessellator.setColorOpaque_F(1, 1, 1);

		double xx = x;
		double zz = z;

		long l = x * 3129871 ^ z * 116129781L ^ y;
		l = l * l * 42317861L + l * 11L;
		xx += ((l >> 16 & 15L) / 15.0F - 0.5D) * 0.3D;
		zz += ((l >> 24 & 15L) / 15.0F - 0.5D) * 0.3D;

		IIcon icon = renderer.getBlockIconFromSideAndMetadata(block, 0, world.getBlockMetadata(x, y, z));
		renderer.drawCrossedSquares(icon, xx, y, zz, 1.0F);
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return false;
	}

	@Override
	public int getRenderId() {
		return RenderIDs.ENDER_FLOWER;
	}
}