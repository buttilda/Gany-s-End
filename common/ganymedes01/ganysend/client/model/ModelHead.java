package ganymedes01.ganysend.client.model;

import ganymedes01.ganysend.lib.SkullTypes;
import net.minecraft.client.model.ModelEnderman;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelSkeletonHead;

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
public class ModelHead extends ModelSkeletonHead {

	private ModelRenderer head;
	private ModelRenderer overlay;
	private boolean renderOverlay;

	public ModelHead() {
		this(32);
	}

	public ModelHead(int height) {
		textureWidth = 64;
		textureHeight = height;
		renderOverlay = true;
		head = new ModelRenderer(this, 0, 0);
		overlay = new ModelRenderer(this, 32, 0);
		head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
		head.setRotationPoint(0.0F, 0.0F, 0.0F);
		overlay.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.5F);
		overlay.setRotationPoint(0.0F, 0.0F, 0.0F);
	}

	private ModelHead hideOverlay() {
		renderOverlay = false;
		return this;
	}

	private void setPig() {
		head = new ModelRenderer(this, 0, 0);
		head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
		head.setTextureOffset(16, 16).addBox(-2.0F, -4.0F, -5.0F, 4, 3, 1, 0.0F);
		hideOverlay();
	}

	private void setCow() {
		head = new ModelRenderer(this, 0, 0);
		head.addBox(-4.0F, -8.0F, -2.0F, 8, 8, 6, 0.0F);
		head.setTextureOffset(22, 0).addBox(-5.0F, -9.0F, 0.0F, 1, 3, 1, 0.0F);
		head.setTextureOffset(22, 0).addBox(4.0F, -9.0F, 0.0F, 1, 3, 1, 0.0F);
		hideOverlay();
	}

	private void setSpider() {
		head = new ModelRenderer(this, 32, 4);
		head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
		hideOverlay();
	}

	private void setSheep() {
		head = new ModelRenderer(this, 0, 0);
		head.addBox(-3.0F, -7.0F, -4.5F, 6, 6, 8, 0.0F);
		overlay = new ModelRenderer(this, 0, 0);
		overlay.addBox(-3.0F, -7.0F, -2.5F, 6, 6, 6, 0.6F);
		hideOverlay();
	}

	private void setWolf() {
		head = new ModelRenderer(this, 0, 0);
		head.addBox(-3.0F, -6.0F, 0.0F, 6, 6, 4, 0.0F);

		head.setTextureOffset(16, 14).addBox(-3.0F, -8.0F, 2.0F, 2, 2, 1, 0.0F);
		head.setTextureOffset(16, 14).addBox(1.0F, -8.0F, 2.0F, 2, 2, 1, 0.0F);
		head.setTextureOffset(0, 10).addBox(-1.5F, -3.0F, -3.0F, 3, 3, 4, 0.0F);
		hideOverlay();
	}

	private void setEnderman() {
		ModelEnderman model = new ModelEnderman();
		head = model.bipedHead;
		overlay = model.bipedHeadwear;
		head.setRotationPoint(0F, 0F, 0F);
		overlay.setRotationPoint(0F, 0F, 0F);
	}

	private void setVillager(int textureSize) {
		head = new ModelRenderer(this).setTextureSize(textureSize, textureSize);
		head.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.setTextureOffset(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, 0.0F);
		overlay = new ModelRenderer(this).setTextureSize(textureSize, textureSize);
		overlay.setRotationPoint(0.0F, -2.0F, 0.0F);
		overlay.setTextureOffset(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2, 4, 2, 0.0F);
	}

	private void setChicken() {
		head = new ModelRenderer(this, 0, 0);
		head.addBox(-2.0F, -6.0F, 1.0F, 4, 6, 3, 0.0F);
		overlay = new ModelRenderer(this, 14, 0);
		overlay.addBox(-2.0F, -4.0F, -1.0F, 4, 2, 2, 0.0F);
		ModelRenderer overlay2 = new ModelRenderer(this, 14, 4);
		overlay2.addBox(-1.0F, -2.0F, 0.0F, 2, 2, 2, 0.0F);
		overlay.addChild(overlay2);
	}

	private void setWitch() {
		head = new ModelRenderer(this).setTextureSize(64, 128);
		head.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.setTextureOffset(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, 0.0F);

		overlay = new ModelRenderer(this).setTextureSize(64, 128);
		overlay.setRotationPoint(0.0F, -2.0F, 0.0F);
		overlay.setTextureOffset(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2, 4, 2, 0.0F);

		ModelRenderer wart = new ModelRenderer(this).setTextureSize(64, 128);
		wart.setRotationPoint(0.0F, -2.0F, 0.0F);
		wart.setTextureOffset(0, 0).addBox(0.0F, 3.0F, -6.75F, 1, 1, 1, -0.25F);
		overlay.addChild(wart);

		ModelRenderer hatBase = new ModelRenderer(this).setTextureSize(64, 128);
		hatBase.setRotationPoint(-5.0F, -10.03125F, -5.0F);
		hatBase.setTextureOffset(0, 64).addBox(0.0F, 0.0F, 0.0F, 10, 2, 10);
		hatBase.rotateAngleX = head.rotateAngleX;
		hatBase.rotateAngleY = head.rotateAngleY;
		head.addChild(hatBase);

		ModelRenderer hat1 = new ModelRenderer(this).setTextureSize(64, 128);
		hat1.setRotationPoint(1.75F, -4.0F, 2.0F);
		hat1.setTextureOffset(0, 76).addBox(0.0F, 0.0F, 0.0F, 7, 4, 7);
		hat1.rotateAngleX = -0.05235988F;
		hat1.rotateAngleZ = 0.02617994F;
		hatBase.addChild(hat1);

		ModelRenderer hat2 = new ModelRenderer(this).setTextureSize(64, 128);
		hat2.setRotationPoint(1.75F, -4.0F, 2.0F);
		hat2.setTextureOffset(0, 87).addBox(0.0F, 0.0F, 0.0F, 4, 4, 4);
		hat2.rotateAngleX = -0.10471976F;
		hat2.rotateAngleZ = 0.05235988F;
		hat1.addChild(hat2);

		ModelRenderer hat3 = new ModelRenderer(this).setTextureSize(64, 128);
		hat3.setRotationPoint(1.75F, -2.0F, 2.0F);
		hat3.setTextureOffset(0, 95).addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.25F);
		hat3.rotateAngleX = -0.20943952F;
		hat3.rotateAngleZ = 0.10471976F;
		hat2.addChild(hat3);
	}

	private void setZombieVillager() {
		head = new ModelRenderer(this).setTextureSize(64, 64);
		head.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.setTextureOffset(0, 32).addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, 0.0F);
		overlay = new ModelRenderer(this).setTextureSize(64, 64);
		overlay.setRotationPoint(0.0F, -2.0F, 0.0F);
		overlay.setTextureOffset(24, 32).addBox(-1.0F, -1.0F, -6.0F, 2, 4, 2, 0.0F);
	}

	private void setSquid() {
		head = new ModelRenderer(this).setTextureSize(64, 32);
		head.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.addBox(-6.0F, -16F, -6.0F + 0.25F, 12, 16, 12);
		hideOverlay();

		// Hacky
		GL11.glScaled(2F / 3F, 2F / 3F, 2F / 3F);
	}

	private void setBunny() {
		hideOverlay();

		setTextureOffset("head.head", 0, 0);
		setTextureOffset("head.ear2", 16, 0);
		setTextureOffset("head.ear1", 16, 0);

		head = new ModelRenderer(this, "head").setTextureSize(32, 32);
		head.setRotationPoint(0.0F, 0.0F, 0.0F);
		setRotation(head, 0.0F, 0.0F, 0.0F);
		head.mirror = true;
		head.addBox("head", -2.0F, -4.0F, 0.0F, 4, 4, 4);
		head.addBox("ear2", -2.5F, -8.0F, 2.5F, 2, 4, 1);
		head.addBox("ear1", 0.5F, -8.0F, 2.5F, 2, 4, 1);
	}

	private void setPenguin() {
		head = new ModelRenderer(this, 0, 0).setTextureSize(64, 32);
		head.addBox(-3.5F, -5.0F, -3.5F, 7, 5, 7);
		head.setRotationPoint(0.0F, 0.0F, 0.0F);

		overlay = new ModelRenderer(this, 0, 13);
		overlay.addBox(-1.0F, -2.0F, -5.0F, 2, 1, 2);
		overlay.setRotationPoint(0.0F, 0.0F, 0.0F);
	}

	private void setBighorn() {
		setSheep();
		head = new ModelRenderer(this, 0, 0);
		head.addBox(-3.0F, -7.0F, -4.5F, 6, 6, 7, 0.0F);
		head.setRotationPoint(0.0F, 0.0F, 0.0F);

		head.setTextureOffset(28, 16).addBox(-5.0F, -7.0F, -1.0F, 2, 2, 2, 0.0F);
		head.setTextureOffset(16, 13).addBox(-6.0F, -8.0F, 0.0F, 2, 2, 4, 0.0F);
		head.setTextureOffset(16, 19).addBox(-7.0F, -7.0F, 3.0F, 2, 5, 2, 0.0F);
		head.setTextureOffset(18, 27).addBox(-8.0F, -3.0F, 1.0F, 2, 2, 3, 0.0F);
		head.setTextureOffset(28, 27).addBox(-9.0F, -4.0F, 0.0F, 2, 2, 1, 0.0F);

		head.setTextureOffset(28, 16).addBox(3.0F, -7.0F, -1.0F, 2, 2, 2, 0.0F);
		head.setTextureOffset(16, 13).addBox(4.0F, -8.0F, 0.0F, 2, 2, 4, 0.0F);
		head.setTextureOffset(16, 19).addBox(5.0F, -7.0F, 3.0F, 2, 5, 2, 0.0F);
		head.setTextureOffset(18, 27).addBox(6.0F, -3.0F, 1.0F, 2, 2, 3, 0.0F);
		head.setTextureOffset(28, 27).addBox(7.0F, -4.0F, 0.0F, 2, 2, 1, 0.0F);

		overlay = new ModelRenderer(this, 0, 0);
		overlay.addBox(-3.0F, -7.0F, -2.5F, 6, 6, 6, 0.6F);
		hideOverlay();
	}

	private void setDeer() {
		hideOverlay();

		head = new ModelRenderer(this, 0, 5);
		head.addBox(-2.0F, -8.0F, -2.0F, 4, 6, 6, 0.0F);
		head.setRotationPoint(0.0F, 2.0F, 0.0F);

		head.setTextureOffset(52, 0).addBox(-1.5F, -5.0F, -5.0F, 3, 3, 3, 0.0F);

		head.setTextureOffset(20, 0);
		head.addBox(-3.0F, -10.0F, 2.0F, 2, 2, 2, 0.0F);
		head.addBox(-3.0F, -10.0F, 2.0F, 2, 2, 2, 0.0F);
		head.addBox(-4.0F, -10.0F, 3.0F, 1, 1, 3, 0.0F);
		head.addBox(-5.0F, -11.0F, 5.0F, 1, 1, 5, 0.0F);
		head.addBox(-5.0F, -14.0F, 7.0F, 1, 4, 1, 0.0F);
		head.addBox(-6.0F, -17.0F, 6.0F, 1, 4, 1, 0.0F);
		head.addBox(-6.0F, -13.0F, 4.0F, 1, 1, 3, 0.0F);
		head.addBox(-6.0F, -14.0F, 1.0F, 1, 1, 4, 0.0F);
		head.addBox(-7.0F, -15.0F, -2.0F, 1, 1, 4, 0.0F);
		head.addBox(-6.0F, -16.0F, -5.0F, 1, 1, 4, 0.0F);
		head.addBox(-7.0F, -18.0F, 3.0F, 1, 5, 1, 0.0F);
		head.addBox(-6.0F, -19.0F, -2.0F, 1, 5, 1, 0.0F);

		head.addBox(1.0F, -10.0F, 2.0F, 2, 2, 2, 0.0F);
		head.addBox(3.0F, -10.0F, 3.0F, 1, 1, 3, 0.0F);
		head.addBox(4.0F, -11.0F, 5.0F, 1, 1, 5, 0.0F);
		head.addBox(4.0F, -14.0F, 6.0F, 1, 4, 1, 0.0F);
		head.addBox(5.0F, -17.0F, 7.0F, 1, 4, 1, 0.0F);
		head.addBox(5.0F, -13.0F, 4.0F, 1, 1, 3, 0.0F);
		head.addBox(5.0F, -14.0F, 1.0F, 1, 1, 4, 0.0F);
		head.addBox(6.0F, -15.0F, -2.0F, 1, 1, 4, 0.0F);
		head.addBox(5.0F, -16.0F, -5.0F, 1, 1, 4, 0.0F);
		head.addBox(6.0F, -18.0F, 3.0F, 1, 5, 1, 0.0F);
		head.addBox(5.0F, -19.0F, -2.0F, 1, 5, 1, 0.0F);
	}

	private void setBoar() {
		hideOverlay();

		head = new ModelRenderer(this, 0, 0);
		head.addBox(-4.0F, -2.0F, -2.0F, 8, 7, 6, 0.0F);
		head.setRotationPoint(0.0F, -5.0F, 0.0F);

		head.setTextureOffset(28, 0).addBox(-3.0F, 1.0F, -5.0F, 6, 4, 3, 0.0F);

		head.setTextureOffset(17, 17).addBox(3.0F, 2.0F, -5.0F, 1, 2, 1, 0.0F);
		head.setTextureOffset(17, 17).addBox(-4.0F, 2.0F, -5.0F, 1, 2, 1, 0.0F);
	}

	private void setRedcap() {
		head = new ModelRenderer(this, 0, 0);
		head = new ModelRenderer(this, 0, 0);
		head.addBox(-3.4F, -7.0F, -4.0F, 7, 7, 7, 0.0F);
		head.setRotationPoint(0.0F, 0.0F, 0.0F);

		overlay = new ModelRenderer(this, 32, 0);
		overlay.addBox(-2.0F, -8.0F, -3.0F, 4, 5, 7, 0.0F);
		overlay.setRotationPoint(0.0F, 0.0F, 0.0F);

		ModelRenderer goblinRightEar = new ModelRenderer(this, 48, 20);
		goblinRightEar.addBox(3.0F, -7.0F, -1.0F, 2, 3, 1, 0.0F);
		goblinRightEar.setRotationPoint(0.0F, 0.0F, 0.0F);

		ModelRenderer goblinLeftEar = new ModelRenderer(this, 48, 24);
		goblinLeftEar.addBox(-5.0F, -7.0F, -1.0F, 2, 3, 1, 0.0F);
		goblinLeftEar.setRotationPoint(0.0F, 0.0F, 0.0F);

		head.addChild(goblinLeftEar);
		head.addChild(goblinRightEar);
	}

	private void setGhast() {
		hideOverlay();
		textureWidth = 32;
		textureHeight = 16;

		head = new ModelRenderer(this, 0, 0);
		head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
	}

	private void setLich() {
		textureWidth = 64;
		textureHeight = 64;

		head = new ModelRenderer(this, 0, 0);
		head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
		head.setRotationPoint(0.0F, 0.0F, 0.0F);

		overlay = new ModelRenderer(this, 32, 0);
		overlay.addBox(-4.0F, -12.0F, -4.0F, 8, 8, 8, 0.5F);
		overlay.setRotationPoint(0.0F, 0.0F, 0.0F);
	}

	private void setEnderDragon() {
		float f1 = -14.0F;
		float f2 = -8.0F;
		textureWidth = 256;
		textureHeight = 256;

		setTextureOffset("head.upperhead", 112, 30);
		setTextureOffset("head.upperlip", 176, 44);
		setTextureOffset("head.scale", 0, 0);
		setTextureOffset("head.nostril", 112, 0);
		setTextureOffset("jaw.jaw", 176, 65);

		head = new ModelRenderer(this, "head");
		head.addBox("upperlip", -6.0F, -1.0F + f2, -8.0F + f1, 12, 5, 16);
		head.addBox("upperhead", -8.0F, -8.0F + f2, 6.0F + f1, 16, 16, 16);
		head.mirror = true;
		head.addBox("scale", -5.0F, -12.0F + f2, 12.0F + f1, 2, 4, 6);
		head.addBox("nostril", -5.0F, -3.0F + f2, -6.0F + f1, 2, 2, 4);
		head.mirror = false;
		head.addBox("scale", 3.0F, -12.0F + f2, 12.0F + f1, 2, 4, 6);
		head.addBox("nostril", 3.0F, -3.0F + f2, -6.0F + f1, 2, 2, 4);
		overlay = new ModelRenderer(this, "jaw");
		overlay.setRotationPoint(0.0F, 4.0F + f2, 8.0F + f1);
		overlay.addBox("jaw", -6.0F, 0.0F, -16.0F, 12, 4, 16);
		overlay.rotateAngleX = 0.2F;
		head.addChild(overlay);
		hideOverlay();

		// Hacky
		GL11.glScaled(0.5, 0.5, 0.5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public ModelHead setHeadType(int type) {
		switch (SkullTypes.values()[type]) {
			case enderman:
				setEnderman();
				break;
			case pigman:
				return new ModelHead(64);
			case spider:
			case caveSpider:
			case hedgeSpider:
			case kingSpider:
				setSpider();
				break;
			case pig:
				setPig();
				break;
			case mooshroom:
			case cow:
				setCow();
				break;
			case sheep:
				setSheep();
				break;
			case wolf:
			case mistWolf:
				setWolf();
				break;
			case villager:
				setVillager(64);
				break;
			case chicken:
				setChicken();
				break;
			case witch:
				setWitch();
				break;
			case zombieVillager:
				setZombieVillager();
				break;
			case ironGolem:
				setVillager(128);
				break;
			case squid:
				setSquid();
				break;
			case wither:
				return new ModelHead(64).hideOverlay();
			case bunny:
				setBunny();
				break;
			case penguin:
				setPenguin();
				break;
			case bighorn:
				setBighorn();
				break;
			case wildDeer:
				setDeer();
				break;
			case wildBoar:
				setBoar();
				break;
			case redcap:
				setRedcap();
				break;
			case ghast:
			case miniGhast:
			case guardGhast:
				setGhast();
				break;
			case lich:
				setLich();
				break;
			case kobold:
			case slimeBeetle:
			case fireBeetle:
			case pinchBeetle:
			case towerGolem:
				break;
			case enderDragon:
				setEnderDragon();
			default:
				break;
		}
		return this;
	}

	public void render(float rotationX) {
		render(rotationX, 0.0F);
	}

	public void render(float rotationX, float rotationY) {
		head.rotateAngleY = rotationX / (180F / (float) Math.PI);
		head.rotateAngleX = rotationY / (180F / (float) Math.PI);
		head.render(0.0625F);
		if (renderOverlay)
			renderOverlay(rotationX, rotationY);
	}

	public void renderOverlay(float rotationX) {
		renderOverlay(rotationX, 0.0F);
	}

	public void renderOverlay(float rotationX, float rotationY) {
		overlay.rotateAngleY = rotationX / (180F / (float) Math.PI);
		overlay.rotateAngleX = rotationY / (180F / (float) Math.PI);
		overlay.render(0.0625F);
	}
}
