package ganymedes01.ganysend.client.renderer.tileentity;

import ganymedes01.ganysend.client.OpenGLHelper;
import ganymedes01.ganysend.client.model.ModelHead;
import ganymedes01.ganysend.lib.SkullTypes;
import ganymedes01.ganysend.tileentities.TileEntityInventoryBinder;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

@SideOnly(Side.CLIENT)
public class TileEntityInventoryBinderRender extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float rotation) {
		TileEntityInventoryBinder tilePlayerInv = (TileEntityInventoryBinder) tile;
		String name = tilePlayerInv.getPlayerName();
		if (name == null)
			return;

		double headRotation = 0.0F;
		EntityPlayer player = tilePlayerInv.getWorldObj().getPlayerEntityByName(name);
		if (player != null)
			headRotation = Math.atan2(player.posZ - 0.5F - tile.zCoord, player.posX - 0.5F - tile.xCoord) * 180 / Math.PI;
		else
			headRotation = (float) (2 * 720.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL);

		ModelHead model = ModelHead.getHead(SkullTypes.player.ordinal());
		bindTexture(SkullTypes.player.getTexture(tilePlayerInv.getProfile()));
		OpenGLHelper.pushMatrix();
		OpenGLHelper.disableCull();
		OpenGLHelper.translate((float) x + 0.5F, (float) y + 0.25F, (float) z + 0.5F);
		OpenGLHelper.scale(1.0F, -1.0F, -1.0F);
		OpenGLHelper.rotate(270 + (float) headRotation, 0, 1, 0);
		model.render(0);
		OpenGLHelper.enableCull();
		OpenGLHelper.popMatrix();
	}
}