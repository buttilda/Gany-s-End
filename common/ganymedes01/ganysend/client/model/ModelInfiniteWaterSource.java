package ganymedes01.ganysend.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
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

	private ModelRenderer tubeX, tubeY, tubeZ;
	private ModelRenderer[] heads = new ModelRenderer[6];
	private ModelRenderer core;

	public ModelInfiniteWaterSource() {
		tubeX = new ModelRenderer(this, 0, 0).setTextureSize(32, 27);
		tubeY = new ModelRenderer(this, 0, 0).setTextureSize(32, 27);
		tubeZ = new ModelRenderer(this, 0, 0).setTextureSize(32, 27);
		core = new ModelRenderer(this, 0, 0).setTextureSize(16, 512);

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

	public void renderCore(int textureOffsetY) {
		float rotation = (float) (2 * 720.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL);

		core = new ModelRenderer(this, 0, textureOffsetY).setTextureSize(8, 256);
		core.addBox(-4F, -4F, -4F, 8, 8, 8);
		core.setRotationPoint(8.0F, 8.0F, 8.0F);
		core.rotateAngleZ = rotation / (180F / (float) Math.PI);
		core.rotateAngleY = rotation / (180F / (float) Math.PI);
		core.rotateAngleX = rotation / (180F / (float) Math.PI);
		core.render(0.0625F);
	}
}