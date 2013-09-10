package ganymedes01.ganysend.client.model;

import net.minecraft.client.model.ModelEnderman;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelSkeletonHead;
import net.minecraft.entity.Entity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

@SideOnly(Side.CLIENT)
public class ModelHead extends ModelSkeletonHead {

	private ModelRenderer head;
	private ModelRenderer overlay;

	public ModelHead() {
		this(32);
	}

	public ModelHead(int height) {
		textureWidth = 64;
		textureHeight = height;
		head = new ModelRenderer(this, 0, 0);
		overlay = new ModelRenderer(this, 32, 0);
		head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
		head.setRotationPoint(0.0F, 0.0F, 0.0F);
		overlay.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.5F);
		overlay.setRotationPoint(0.0F, 0.0F, 0.0F);
	}

	private void hideOverlay() {
		overlay = new ModelRenderer(this, 0, 0);
		overlay.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0F);
		overlay.setRotationPoint(0.0F, 0.0F, 0.0F);
	}

	public void setPig() {
		head = new ModelRenderer(this, 0, 0);
		head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
		head.setTextureOffset(16, 16).addBox(-2.0F, -4.0F, -5.0F, 4, 3, 1, 0.0F);
		hideOverlay();
	}

	public void setCow() {
		head = new ModelRenderer(this, 0, 0);
		head.addBox(-4.0F, -8.0F, -2.0F, 8, 8, 6, 0.0F);
		head.setTextureOffset(22, 0).addBox(-5.0F, -9.0F, 0.0F, 1, 3, 1, 0.0F);
		head.setTextureOffset(22, 0).addBox(4.0F, -9.0F, 0.0F, 1, 3, 1, 0.0F);
		hideOverlay();
	}

	public void setSpider() {
		head = new ModelRenderer(this, 32, 4);
		head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
		hideOverlay();
	}

	public void setSheepFur() {
		head = new ModelRenderer(this, 0, 0);
		head.addBox(-3.0F, -7.0F, -2.5F, 6, 6, 6, 0.6F);
		hideOverlay();
	}

	public void setSheep() {
		head = new ModelRenderer(this, 0, 0);
		head.addBox(-3.0F, -7.0F, -4.5F, 6, 6, 8, 0.0F);
		hideOverlay();
	}

	public void setWolf() {
		head = new ModelRenderer(this, 0, 0);
		head.addBox(-3.0F, -6.0F, 0.0F, 6, 6, 4, 0.0F);

		head.setTextureOffset(16, 14).addBox(-3.0F, -8.0F, 2.0F, 2, 2, 1, 0.0F);
		head.setTextureOffset(16, 14).addBox(1.0F, -8.0F, 2.0F, 2, 2, 1, 0.0F);
		head.setTextureOffset(0, 10).addBox(-1.5F, -3.0F, -3.0F, 3, 3, 4, 0.0F);
		hideOverlay();
	}

	public void setEnderman() {
		ModelEnderman model = new ModelEnderman();
		head = model.bipedHead;
		overlay = model.bipedHeadwear;
		head.setRotationPoint(0F, 0F, 0F);
		overlay.setRotationPoint(0F, 0F, 0F);
	}

	public void setVillager() {
		head = (new ModelRenderer(this)).setTextureSize(64, 64);
		head.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.setTextureOffset(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, 0.0F);
		overlay = (new ModelRenderer(this)).setTextureSize(64, 64);
		overlay.setRotationPoint(0.0F, -2.0F, 0.0F);
		overlay.setTextureOffset(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2, 4, 2, 0.0F);
	}

	public void setChicken() {
		head = new ModelRenderer(this, 0, 0);
		head.addBox(-2.0F, -6.0F, 1.0F, 4, 6, 3, 0.0F);
		overlay = new ModelRenderer(this, 14, 0);
		overlay.addBox(-2.0F, -4.0F, -1.0F, 4, 2, 2, 0.0F);
		ModelRenderer overlay2 = new ModelRenderer(this, 14, 4);
		overlay2.addBox(-1.0F, -2.0F, 0.0F, 2, 2, 2, 0.0F);
		overlay.addChild(overlay2);
	}

	public void setWitch() {
		head = (new ModelRenderer(this)).setTextureSize(64, 128);
		head.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.setTextureOffset(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, 0.0F);

		overlay = (new ModelRenderer(this)).setTextureSize(64, 128);
		overlay.setRotationPoint(0.0F, -2.0F, 0.0F);
		overlay.setTextureOffset(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2, 4, 2, 0.0F);

		ModelRenderer wart = (new ModelRenderer(this)).setTextureSize(64, 128);
		wart.setRotationPoint(0.0F, -2.0F, 0.0F);
		wart.setTextureOffset(0, 0).addBox(0.0F, 3.0F, -6.75F, 1, 1, 1, -0.25F);
		overlay.addChild(wart);

		ModelRenderer hatBase = (new ModelRenderer(this)).setTextureSize(64, 128);
		hatBase.setRotationPoint(-5.0F, -10.03125F, -5.0F);
		hatBase.setTextureOffset(0, 64).addBox(0.0F, 0.0F, 0.0F, 10, 2, 10);
		hatBase.rotateAngleX = head.rotateAngleX;
		hatBase.rotateAngleY = head.rotateAngleY;
		head.addChild(hatBase);

		ModelRenderer hat1 = (new ModelRenderer(this)).setTextureSize(64, 128);
		hat1.setRotationPoint(1.75F, -4.0F, 2.0F);
		hat1.setTextureOffset(0, 76).addBox(0.0F, 0.0F, 0.0F, 7, 4, 7);
		hat1.rotateAngleX = -0.05235988F;
		hat1.rotateAngleZ = 0.02617994F;
		hatBase.addChild(hat1);

		ModelRenderer hat2 = (new ModelRenderer(this)).setTextureSize(64, 128);
		hat2.setRotationPoint(1.75F, -4.0F, 2.0F);
		hat2.setTextureOffset(0, 87).addBox(0.0F, 0.0F, 0.0F, 4, 4, 4);
		hat2.rotateAngleX = -0.10471976F;
		hat2.rotateAngleZ = 0.05235988F;
		hat1.addChild(hat2);

		ModelRenderer hat3 = (new ModelRenderer(this)).setTextureSize(64, 128);
		hat3.setRotationPoint(1.75F, -2.0F, 2.0F);
		hat3.setTextureOffset(0, 95).addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.25F);
		hat3.rotateAngleX = -0.20943952F;
		hat3.rotateAngleZ = 0.10471976F;
		hat2.addChild(hat3);
	}

	public void setZombieVillager() {
		head = (new ModelRenderer(this)).setTextureSize(64, 64);
		head.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.setTextureOffset(0, 32).addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, 0.0F);
		overlay = (new ModelRenderer(this)).setTextureSize(64, 64);
		overlay.setRotationPoint(0.0F, -2.0F, 0.0F);
		overlay.setTextureOffset(24, 32).addBox(-1.0F, -1.0F, -6.0F, 2, 4, 2, 0.0F);
	}

	@Override
	public void render(Entity entity, float par2, float par3, float par4, float par5, float par6, float par7) {
		setRotationAngles(par2, par3, par4, par5, par6, par7, entity);
		head.render(par7);
		overlay.render(par7);
	}

	@Override
	public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity entity) {
		super.setRotationAngles(par1, par2, par3, par4, par5, par6, entity);
		head.rotateAngleY = par4 / (180F / (float) Math.PI);
		head.rotateAngleX = par5 / (180F / (float) Math.PI);
		overlay.rotateAngleY = par4 / (180F / (float) Math.PI);
		overlay.rotateAngleX = par5 / (180F / (float) Math.PI);
	}
}
