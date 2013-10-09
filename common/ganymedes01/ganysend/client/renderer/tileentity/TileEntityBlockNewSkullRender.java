package ganymedes01.ganysend.client.renderer.tileentity;

import ganymedes01.ganysend.client.model.ModelHead;
import ganymedes01.ganysend.core.utils.HeadTextures;
import ganymedes01.ganysend.tileentities.TileEntityBlockNewSkull;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
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
public class TileEntityBlockNewSkullRender extends TileEntitySpecialRenderer {

	private ModelHead model;

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float angle) {
		TileEntityBlockNewSkull tileSkull = (TileEntityBlockNewSkull) tile;
		renderHead((float) x, (float) y, (float) z, tileSkull.getBlockMetadata() & 7, tileSkull.func_82119_b() * 360 / 16.0F, tileSkull.getSkullType(), tileSkull.getExtraType());
	}

	public void renderHead(float x, float y, float z, int meta, float skullRotation, int skullType, String playerName) {
		bindTexture(HeadTextures.getHeadTexture(skullType, playerName));

		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_ALPHA_TEST);

		skullRotation = translateHead(x, y, z, meta, skullRotation);
		GL11.glScalef(-1.0F, -1.0F, 1.0F);
		model = new ModelHead().setHeadType(skullType);
		model.render(skullRotation);
		renderSpecial(skullType, skullRotation);

		GL11.glPopMatrix();
	}

	private void renderSpecial(int skullType, float skullRotation) {
		if (HeadTextures.getSecondTexture(skullType) != null) {
			bindTexture(HeadTextures.getSecondTexture(skullType));
			if (skullType == 9)
				model.renderOverlay(skullRotation);
			else
				model.render(skullRotation);
		}
	}

	private float translateHead(float x, float y, float z, int meta, float skullRotation) {
		switch (meta) {
			case 1:
				GL11.glTranslatef(x + 0.5F, y, z + 0.5F);
				return skullRotation;
			case 2:
				GL11.glTranslatef(x + 0.5F, y + 0.25F, z + 0.74F);
				return skullRotation;
			case 3:
				GL11.glTranslatef(x + 0.5F, y + 0.25F, z + 0.26F);
				return 180.0F;
			case 4:
				GL11.glTranslatef(x + 0.74F, y + 0.25F, z + 0.5F);
				return 270.0F;
			default:
				GL11.glTranslatef(x + 0.26F, y + 0.25F, z + 0.5F);
				return 90.0F;
		}
	}
}
