package ganymedes01.ganysend.client.model;

import java.awt.Color;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;

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
public class ModelInfiniteWaterSource extends ModelBase {

	private final RenderBlocks renderer = new RenderBlocks();
	private final Tessellator tess = Tessellator.instance;
	private final ModelRenderer tubeX, tubeY, tubeZ;
	private final ModelRenderer[] heads = new ModelRenderer[6];

	public ModelInfiniteWaterSource() {
		tubeX = new ModelRenderer(this, 0, 0).setTextureSize(32, 27);
		tubeY = new ModelRenderer(this, 0, 0).setTextureSize(32, 27);
		tubeZ = new ModelRenderer(this, 0, 0).setTextureSize(32, 27);

		for (int i = 0; i < heads.length; i++) {
			heads[i] = new ModelRenderer(this, 0, 18).setTextureSize(32, 27);
			heads[i].addBox(4.0F, 0.0F, 4.0F, 8, 1, 8);
			heads[i].addChild(new ModelRenderer(this, 8, 0).setTextureSize(32, 27).addBox(5.0F, 0.5F, 5.0F, 6, 1, 6));
		}
		heads[0].rotationPointY = 16F;
		heads[0].rotateAngleX = (float) (Math.PI / 2);
		heads[1].rotationPointX = 16F;
		heads[1].rotateAngleZ = (float) (Math.PI / 2);

		heads[2].rotationPointY = 16F;
		heads[2].rotateAngleZ = -(float) (Math.PI / 2);
		heads[3].rotationPointZ = 16F;
		heads[3].rotateAngleX = -(float) (Math.PI / 2);

		heads[4].rotationPointX = 16F;
		heads[4].rotationPointY = 16F;
		heads[4].rotateAngleZ = (float) Math.PI;

		tubeY.addBox(7.0F, 1.0F, 7.0F, 2, 14, 2);
		tubeX.addBox(7.0F, 1.0F, 7.0F, 2, 14, 2);
		tubeZ.addBox(7.0F, 1.0F, 7.0F, 2, 14, 2);
		tubeX.rotationPointY = 16F;
		tubeX.rotateAngleX = (float) (Math.PI / 2);
		tubeZ.rotationPointX = 16F;
		tubeZ.rotateAngleZ = (float) (Math.PI / 2);
	}

	public void renderAxis() {
		tubeY.render(0.0625F);
		tubeX.render(0.0625F);
		tubeZ.render(0.0625F);

		for (ModelRenderer head : heads)
			head.render(0.0625F);
	}

	public void renderCore(IIcon fluidIcon, IIcon coreIcon, int colour) {
		float rotation = (float) (2 * 720.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL);

		GL11.glTranslated(0.5, 0.5, 0.5);
		GL11.glScaled(0.5, 0.5, 0.5);

		GL11.glRotated(rotation, 1, 0, 0);
		GL11.glRotated(rotation, 0, 1, 0);
		GL11.glRotated(rotation, 0, 0, 1);

		GL11.glTranslated(-0.5, -0.5, -0.5);

		renderer.setRenderBounds(0, 0, 0, 1, 1, 1);

		drawCube(coreIcon);

		double num = 0.002;
		renderer.setRenderBounds(num, num, num, 1 - num, 1 - num, 1 - num);
		Color c = new Color(colour);
		GL11.glColor3f(c.getRed() / 255F, c.getGreen() / 255F, c.getBlue() / 255F);
		GL11.glDisable(GL11.GL_LIGHTING);
		drawCube(fluidIcon);
		GL11.glEnable(GL11.GL_LIGHTING);
	}

	private void drawCube(IIcon icon) {
		tess.startDrawingQuads();
		tess.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(null, 0.0D, 0.0D, 0.0D, icon);
		tess.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(null, 0.0D, 0.0D, 0.0D, icon);
		tess.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZNeg(null, 0.0D, 0.0D, 0.0D, icon);
		tess.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZPos(null, 0.0D, 0.0D, 0.0D, icon);
		tess.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(null, 0.0D, 0.0D, 0.0D, icon);
		tess.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(null, 0.0D, 0.0D, 0.0D, icon);
		tess.draw();
	}
}