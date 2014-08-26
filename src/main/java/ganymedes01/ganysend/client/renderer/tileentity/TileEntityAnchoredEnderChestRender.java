package ganymedes01.ganysend.client.renderer.tileentity;

import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.tileentities.TileEntityAnchoredEnderChest;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
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
public class TileEntityAnchoredEnderChestRender extends TileEntitySpecialRenderer {

	private static final ResourceLocation TEXTURE_ON = Utils.getResource(Utils.getEntityTexture(Strings.ANCHORED_ENDER_CHEST_NAME + "_on"));
	private static final ResourceLocation TEXTURE_OFF = Utils.getResource(Utils.getEntityTexture(Strings.ANCHORED_ENDER_CHEST_NAME + "_off"));

	private final ModelChest MODEL = new ModelChest();

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTick) {
		TileEntityAnchoredEnderChest chest = (TileEntityAnchoredEnderChest) tile;
		int meta = chest.hasWorldObj() ? chest.getBlockMetadata() : 0;

		bindTexture(chest.isConnected() ? TEXTURE_ON : TEXTURE_OFF);
		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float) x, (float) y + 1.0F, (float) z + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);

		short rotation = 0;
		if (meta == 2)
			rotation = 180;
		if (meta == 4)
			rotation = 90;
		if (meta == 5)
			rotation = -90;

		GL11.glRotatef(rotation, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		float angle = chest.prevLidAngle + (chest.lidAngle - chest.prevLidAngle) * partialTick;
		angle = 1.0F - angle;
		angle = 1.0F - angle * angle * angle;
		MODEL.chestLid.rotateAngleX = -(angle * (float) Math.PI / 2.0F);
		MODEL.renderAll();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}
}