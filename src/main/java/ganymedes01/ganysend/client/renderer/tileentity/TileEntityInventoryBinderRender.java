package ganymedes01.ganysend.client.renderer.tileentity;

import ganymedes01.ganysend.client.model.ModelHead;
import ganymedes01.ganysend.lib.SkullTypes;
import ganymedes01.ganysend.tileentities.TileEntityInventoryBinder;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

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
		double headRotation = 0.0F;
		EntityPlayer player = tilePlayerInv.getWorldObj().getPlayerEntityByName(name);
		if (player != null)
			headRotation = Math.atan2(player.posZ - 0.5F - tile.zCoord, player.posX - 0.5F - tile.xCoord) * 180 / Math.PI;
		else
			headRotation = (float) (2 * 720.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL);

		ModelHead model = ModelHead.getHead(SkullTypes.player.ordinal());
		bindTexture(SkullTypes.player.getTexture(tilePlayerInv.getProfile()));
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glTranslatef((float) x + 0.5F, (float) y + 0.25F, (float) z + 0.5F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glRotatef(270 + (float) headRotation, 0, 1, 0);
		model.render(0);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}
}