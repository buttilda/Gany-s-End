package ganymedes01.ganysend.client.renderer.tileentity;

import ganymedes01.ganysend.client.model.ModelHead;
import ganymedes01.ganysend.client.model.ModelInventoryBinder;
import ganymedes01.ganysend.core.utils.HeadTextures;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Reference;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.tileentities.TileEntityInventoryBinder;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

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

	private final ModelInventoryBinder blockModel = new ModelInventoryBinder();

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float rotation) {
		TileEntityInventoryBinder tilePlayerInv = (TileEntityInventoryBinder) tile;
		String name = tilePlayerInv.getPlayerName();
		double headRotation = 0.0F, cX, cZ, eX, eZ, defaultYrot = 0.0F, transX = 0.0F, transY = 0.0F;

		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glTranslatef((float) x, (float) y + 1.0F, (float) z + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

		bindTexture(Utils.getResource(Reference.ITEM_BLOCK_TEXTURE_PATH + "textures/blocks/endstoneBrick.png"));
		blockModel.renderFrame();

		bindTexture(Utils.getResource(Utils.getEntityTexture(Strings.INVENTORY_BINDER_NAME + "2")));
		EntityPlayer player = tilePlayerInv.getWorldObj().getPlayerEntityByName(name);
		if (player != null) {
			cX = player.posX - 0.5F;
			cZ = player.posZ - 0.5F;
			eX = tilePlayerInv.xCoord;
			eZ = tilePlayerInv.zCoord;
			headRotation = Math.atan2(cZ - eZ, cX - eX) * 180 / Math.PI;
			bindTexture(Utils.getResource(Utils.getEntityTexture(Strings.INVENTORY_BINDER_NAME + "1")));
		} else {
			defaultYrot = 90.0F;
			transY = 0.25F;
			transX = 0.25F;
		}
		blockModel.renderInside();

		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();

		ModelHead model = new ModelHead();
		bindTexture(HeadTextures.getPlayerSkin(name));
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glTranslatef((float) x + 0.5F - (float) transX, (float) y + 0.25F + (float) transY, (float) z + 0.5F);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glScalef(-1.0F, -1.0F, 1.0F);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glRotatef(360.0F, 0, 1, 0);
		model.render(90.0F + (float) headRotation, (float) defaultYrot);
		GL11.glPopMatrix();
	}
}
