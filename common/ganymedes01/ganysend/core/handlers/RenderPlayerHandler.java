package ganymedes01.ganysend.core.handlers;

import ganymedes01.ganysend.client.renderer.tileentity.TileEntityBlockNewSkullRender;
import ganymedes01.ganysend.items.ModItems;

import java.lang.reflect.Field;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.ForgeSubscribe;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class RenderPlayerHandler {

	@ForgeSubscribe
	@SideOnly(Side.CLIENT)
	public void renderHelmetEvent(RenderPlayerEvent.Specials.Pre event) {
		try {
			if (event.entityPlayer != null) {
				Field f = event.renderer.getClass().getDeclaredField("modelBipedMain");
				f.setAccessible(true);
				ModelBiped model = (ModelBiped) f.get(event.renderer);

				ItemStack helmet = event.entityPlayer.inventory.armorItemInSlot(3);
				if (helmet != null && helmet.getItem() == ModItems.itemNewSkull) {
					GL11.glColor3f(1.0F, 1.0F, 1.0F);

					if (model.bipedHead.isHidden)
						model.bipedHead.isHidden = false;
					model.bipedHead.postRender(0.0625F);

					GL11.glPushMatrix();
					GL11.glScalef(1.0F, -1.0F, -1.0F);
					TileEntityBlockNewSkullRender.instance.renderHead(-0.5F, 0.0F, -0.5F, 1, 180.0F, helmet.getItemDamage(), helmet.hasTagCompound() ? helmet.getTagCompound().getString("SkullOwner") : null);
					GL11.glPopMatrix();
					event.renderHelmet = false;
					if (!model.bipedHead.isHidden)
						model.bipedHead.isHidden = true;
				} else if (model.bipedHead.isHidden)
					model.bipedHead.isHidden = false;
			}
		} catch (Exception e) {
		}
	}
}