package ganymedes01.ganysend.client.renderer.tileentity;

import ganymedes01.ganysend.client.model.ModelHead;
import ganymedes01.ganysend.lib.SkullTypes;
import ganymedes01.ganysend.tileentities.TileEntityBlockNewSkull;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.passive.EntitySheep;
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

	private ModelHead model;
	public static TileEntityBlockNewSkullRender instance;

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float angle) {
		TileEntityBlockNewSkull tileSkull = (TileEntityBlockNewSkull) tile;
		renderHead((float) x, (float) y, (float) z, tileSkull.getBlockMetadata() & 7, tileSkull.func_82119_b() * 360 / 16.0F, tileSkull.getSkullType(), tileSkull.getExtraType());
	}

	@Override
	public void setTileEntityRenderer(TileEntityRenderer renderer) {
		super.setTileEntityRenderer(renderer);
		instance = this;
	}

	public void renderHead(float x, float y, float z, int meta, float skullRotation, int skullType, String playerName) {
		bindTexture(SkullTypes.values()[skullType].getTexture(playerName));

		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_ALPHA_TEST);

		skullRotation = translateHead(x, y, z, meta, skullRotation);
		GL11.glScalef(-1.0F, -1.0F, 1.0F);
		model = ModelHead.getHead(skullType);
		model.render(skullRotation);
		renderSpecial(skullType, skullRotation);

		GL11.glPopMatrix();
	}

	private void renderSpecial(int skullType, float skullRotation) {
		ResourceLocation secondTex = SkullTypes.values()[skullType].getSecondTexture();
		if (secondTex != null) {
			bindTexture(secondTex);
			if (skullType == SkullTypes.sheep.ordinal() || skullType == SkullTypes.bighorn.ordinal()) {
				int c = 12;
				if (skullType == SkullTypes.bighorn.ordinal())
					GL11.glColor3f(EntitySheep.fleeceColorTable[c][0], EntitySheep.fleeceColorTable[c][1], EntitySheep.fleeceColorTable[c][2]);
				model.renderOverlay(skullRotation);
			} else if (skullType == SkullTypes.mooshroom.ordinal()) {
				GL11.glScaled(1, -1, 1);
				GL11.glTranslated(0, 1, 0);
				GL11.glEnable(GL11.GL_CULL_FACE);
				RenderBlocks renderer = new RenderBlocks();
				renderer.renderBlockAsItem(Block.mushroomRed, 0, 1.0F);
				GL11.glDisable(GL11.GL_CULL_FACE);
			} else
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
