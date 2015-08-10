package ganymedes01.ganysend.client.renderer.tileentity;

import ganymedes01.ganysend.client.OpenGLHelper;
import ganymedes01.ganysend.tileentities.TileEntityInventoryBinder;

import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

@SideOnly(Side.CLIENT)
public class TileEntityInventoryBinderRender extends TileEntitySpecialRenderer {

	private final ModelBiped biped = new ModelBiped(0.0F);

	@Override
	@SuppressWarnings("unchecked")
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTicks) {
		TileEntityInventoryBinder tilePlayerInv = (TileEntityInventoryBinder) tile;
		GameProfile profile = tilePlayerInv.getProfile();
		if (profile == null)
			return;

		EntityPlayer player = tilePlayerInv.getWorldObj().getPlayerEntityByName(profile.getName());
		OpenGLHelper.pushMatrix();
		OpenGLHelper.translate(x + 0.5, y + 0.75, z + 0.5);
		OpenGLHelper.scale(0.4, 0.4, 0.4);
		if (player != null) {
			OpenGLHelper.rotate(180, 0, 1, 0);
			RenderManager.instance.renderEntitySimple(player, partialTicks);
		} else {
			biped.isChild = false;
			OpenGLHelper.colour(0xFFFFFF);
			OpenGLHelper.enableRescaleNormal();
			OpenGLHelper.translate(0, -0.25, 0);
			OpenGLHelper.scale(1, -1, -1);
			OpenGLHelper.scale(0.95, 0.95, 0.95);
			OpenGLHelper.rotate((float) (720.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL), 0, 1, 0);

			ResourceLocation texture = AbstractClientPlayer.locationStevePng;

			if (profile != null) {
				Minecraft minecraft = Minecraft.getMinecraft();
				Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = minecraft.func_152342_ad().func_152788_a(profile);
				if (map.containsKey(Type.SKIN))
					texture = minecraft.func_152342_ad().func_152792_a(map.get(Type.SKIN), Type.SKIN);
			}

			bindTexture(texture);
			biped.render(null, 0, 0, 0, 0, 0, 1F / 16F);
		}
		OpenGLHelper.popMatrix();
	}
}