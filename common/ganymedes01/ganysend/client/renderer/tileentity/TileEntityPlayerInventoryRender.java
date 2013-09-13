package ganymedes01.ganysend.client.renderer.tileentity;

import ganymedes01.ganysend.client.model.ModelHead;
import ganymedes01.ganysend.client.model.ModelPlayerInventory;
import ganymedes01.ganysend.client.model.ModelPlayerInventoryInside;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.tileentities.TileEntityPlayerInventory;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

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
public class TileEntityPlayerInventoryRender extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float rotation) {
		ModelPlayerInventory blockModel = new ModelPlayerInventory();
		bindTexture(new ResourceLocation(Utils.getEntityTexture(Strings.PLAYER_INVENTORY_NAME)));
		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glTranslatef((float) x, (float) y + 1.0F, (float) z + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		blockModel.render();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();

		TileEntityPlayerInventory tilePLayerInv = (TileEntityPlayerInventory) tile;
		String name = tilePLayerInv.getPlayerName();

		ResourceLocation resourcelocation = AbstractClientPlayer.locationStevePng;
		if (name != null && name.length() > 0) {
			resourcelocation = AbstractClientPlayer.getLocationSkull(name);
			AbstractClientPlayer.getDownloadImageSkin(resourcelocation, name);
		}
		ModelHead model = new ModelHead();
		double headRotation = 0.0F, cX, cZ, eX, eZ, defaultYrot = 0.0F, transX = 0.0F, transY = 0.0F;

		bindTexture(new ResourceLocation(Utils.getEntityTexture(Strings.PLAYER_INVENTORY_NAME + "2")));
		EntityPlayer player = tilePLayerInv.getWorldObj().getPlayerEntityByName(name);
		if (player != null) {
			cX = player.posX - 0.5F;
			cZ = player.posZ - 0.5F;
			eX = tilePLayerInv.xCoord;
			eZ = tilePLayerInv.zCoord;
			headRotation = ((Math.atan2(cZ - eZ, cX - eX)) * 180 / Math.PI);
			bindTexture(new ResourceLocation(Utils.getEntityTexture(Strings.PLAYER_INVENTORY_NAME + "1")));
		} else {
			defaultYrot = 90.0F;
			transY = 0.25F;
			transX = 0.25F;
		}

		ModelPlayerInventoryInside insideModel = new ModelPlayerInventoryInside();
		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glTranslatef((float) x, (float) y + 1.0F, (float) z + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		insideModel.render();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();

		bindTexture(resourcelocation);
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glTranslatef((float) x + 0.5F - (float) transX, (float) y + 0.25F + (float) transY, (float) z + 0.5F);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glScalef(-1.0F, -1.0F, 1.0F);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glRotatef(360.0F, 0, 1, 0);
		model.render((Entity) null, 0.0F, 0.0F, 0.0F, 90.0F + (float) headRotation, (float) defaultYrot, 0.0625F);
		GL11.glPopMatrix();
	}
}
