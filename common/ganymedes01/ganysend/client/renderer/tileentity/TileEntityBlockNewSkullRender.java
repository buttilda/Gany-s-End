package ganymedes01.ganysend.client.renderer.tileentity;

import ganymedes01.ganysend.client.model.ModelHead;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.tileentities.TileEntityBlockNewSkull;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
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
public class TileEntityBlockNewSkullRender extends TileEntitySpecialRenderer {

	private static final ResourceLocation sheepFur = Utils.getResource(Utils.SHEEP_FUR_HEAD);
	private static final ResourceLocation endermanEyes = Utils.getResource(Utils.ENDERMAN_EYES);

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float angle) {
		TileEntityBlockNewSkull tileSkull = (TileEntityBlockNewSkull) tile;
		renderHead((float) x, (float) y, (float) z, tileSkull.getBlockMetadata() & 7, tileSkull.func_82119_b() * 360 / 16.0F, tileSkull.getSkullType(), tileSkull.getExtraType());
	}

	public void renderHead(float x, float y, float z, int meta, float skullRotation, int skullType, String playerName) {
		if (skullType == 3) {
			ResourceLocation resourcelocation = AbstractClientPlayer.locationStevePng;
			if (playerName != null && playerName.length() > 0) {
				resourcelocation = AbstractClientPlayer.getLocationSkull(playerName);
				AbstractClientPlayer.getDownloadImageSkin(resourcelocation, playerName);
			}
			bindTexture(resourcelocation);
		} else
			bindTexture(Utils.headTextures[skullType]);

		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);
		translateHead(x, y, z, meta, skullRotation);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glScalef(-1.0F, -1.0F, 1.0F);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		ModelHead model = new ModelHead().setHeadType(skullType);
		renderModel(model, skullRotation);

		switch (skullType) {
			case 1:
				bindTexture(endermanEyes);
				renderModel(model, skullRotation);
				break;
			case 9:
				bindTexture(sheepFur);
				model.setSheepFur();
				renderModel(model, skullRotation);
				break;
		}
		GL11.glPopMatrix();
	}

	private void renderModel(ModelHead model, float skullRotation) {
		model.render((Entity) null, 0.0F, 0.0F, 0.0F, skullRotation, 0.0F, 0.0625F);
	}

	private void translateHead(float x, float y, float z, int meta, float skullRotation) {
		if (meta != 1)
			switch (meta) {
				case 2:
					GL11.glTranslatef(x + 0.5F, y + 0.25F, z + 0.74F);
					break;
				case 3:
					GL11.glTranslatef(x + 0.5F, y + 0.25F, z + 0.26F);
					skullRotation = 180.0F;
					break;
				case 4:
					GL11.glTranslatef(x + 0.74F, y + 0.25F, z + 0.5F);
					skullRotation = 270.0F;
					break;
				default:
					GL11.glTranslatef(x + 0.26F, y + 0.25F, z + 0.5F);
					skullRotation = 90.0F;
			}
		else
			GL11.glTranslatef(x + 0.5F, y, z + 0.5F);
	}
}
