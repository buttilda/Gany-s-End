package ganymedes01.ganysend.client.renderer.tileentity;

import ganymedes01.ganysend.client.OpenGLHelper;
import ganymedes01.ganysend.client.model.ModelHead;
import ganymedes01.ganysend.lib.SkullTypes;
import ganymedes01.ganysend.tileentities.TileEntityBlockSkull;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.mojang.authlib.GameProfile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

@SideOnly(Side.CLIENT)
public class TileEntityBlockSkullRender extends TileEntitySpecialRenderer {

	private ModelHead model;
	private final RenderBlocks renderer = new RenderBlocks();
	public static TileEntityBlockSkullRender instance;

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTicks) {
		TileEntityBlockSkull tileSkull = (TileEntityBlockSkull) tile;
		renderHead((float) x, (float) y, (float) z, tileSkull.getBlockMetadata() & 7, tileSkull.func_145906_b() * 360 / 16.0F, tileSkull.func_145904_a(), tileSkull.func_152108_a());
	}

	@Override
	public void func_147497_a(TileEntityRendererDispatcher renderer) {
		super.func_147497_a(renderer);
		instance = this;
	}

	public void renderHead(float x, float y, float z, int meta, float skullRotation, int skullType, GameProfile playerName) {
		SkullTypes type = SkullTypes.values()[skullType];
		bindTexture(type.getTexture(playerName));

		OpenGLHelper.pushMatrix();
		OpenGLHelper.disableCull();
		OpenGLHelper.enableRescaleNormal();
		OpenGLHelper.enableAlpha();

		translateHead(x, y, z, meta, skullRotation);
		skullRotation = adjustRotation(meta, skullRotation);

		OpenGLHelper.scale(-1.0F, -1.0F, 1.0F);
		model = ModelHead.getHead(skullType);
		model.render(skullRotation);
		renderSpecial(type, skullRotation);

		OpenGLHelper.disableBlend();
		OpenGLHelper.popMatrix();
	}

	private void renderSpecial(SkullTypes skullType, float skullRotation) {
		ResourceLocation secondTex = skullType.getSecondTexture();

		if (secondTex != null) {
			bindTexture(secondTex);
			switch (skullType) {
				case sheep:
				case bighorn:
					int c = 12;
					if (skullType == SkullTypes.bighorn)
						OpenGLHelper.colour(EntitySheep.fleeceColorTable[c][0], EntitySheep.fleeceColorTable[c][1], EntitySheep.fleeceColorTable[c][2]);
					model.renderOverlay(skullRotation);
					return;
				case mooshroom:
					OpenGLHelper.scale(1, -1, 1);
					OpenGLHelper.translate(0, 1, 0);
					OpenGLHelper.enableCull();
					renderer.renderBlockAsItem(Blocks.red_mushroom, 0, 1.0F);
					OpenGLHelper.disableCull();
					return;
				case spider:
				case caveSpider:
				case hedgeSpider:
				case kingSpider:
				case enderDragon:
				case enderman:
				case swarmSpider:
				case towerBroodling:
				case heatscarSpider:
					OpenGLHelper.enableBlend();
					OpenGLHelper.disableAlpha();
					OpenGLHelper.blendFunc(GL11.GL_ONE, GL11.GL_ONE);
					OpenGLHelper.depthMask(true);
					char c0 = 61680;
					int j = c0 % 65536;
					int k = c0 / 65536;
					OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
					model.render(skullRotation);
					OpenGLHelper.disableBlend();
					OpenGLHelper.enableAlpha();
					return;
				default:
					return;
			}
		}
	}

	private void translateHead(float x, float y, float z, int meta, float skullRotation) {
		switch (meta) {
			case 1:
				OpenGLHelper.translate(x + 0.5F, y, z + 0.5F);
				break;
			case 2:
				OpenGLHelper.translate(x + 0.5F, y + 0.25F, z + 0.74F);
				break;
			case 3:
				OpenGLHelper.translate(x + 0.5F, y + 0.25F, z + 0.26F);
				break;
			case 4:
				OpenGLHelper.translate(x + 0.74F, y + 0.25F, z + 0.5F);
				break;
			default:
				OpenGLHelper.translate(x + 0.26F, y + 0.25F, z + 0.5F);
				break;
		}
	}

	protected float adjustRotation(int meta, float rotation) {
		switch (meta) {
			case 1:
			case 2:
				return rotation;
			case 3:
				return 180.0F;
			case 4:
				return 270.0F;
			default:
				return 90.0F;
		}
	}
}