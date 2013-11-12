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
	public void renderHelmetEvent(RenderPlayerEvent.Specials.Post event) {
		if (event.entityPlayer != null) {
			ModelBiped model = null;
			try {
				Field f = event.renderer.getClass().getDeclaredField("field_77109_a");
				f.setAccessible(true);
				model = (ModelBiped) f.get(event.renderer);
			} catch (Exception e) {
				try {
					Field f = event.renderer.getClass().getDeclaredField("modelBipedMain");
					f.setAccessible(true);
					model = (ModelBiped) f.get(event.renderer);
				} catch (Exception e2) {
				}
			}
			if (model == null)
				return;

			ItemStack head = event.entityPlayer.inventory.armorItemInSlot(3);
			if (head != null && head.getItem() == ModItems.itemNewSkull) {
				GL11.glColor3f(1.0F, 1.0F, 1.0F);

				if (model.bipedHead.isHidden) {
					model.bipedHeadwear.isHidden = false;
					model.bipedHead.isHidden = false;
				}
				model.bipedHead.postRender(0.0625F);

				GL11.glPushMatrix();
				GL11.glScalef(1.0F, -1.0F, -1.0F);

				float offset = 0.0F;
				switch (head.getItemDamage()) {
					case 7:
						offset = 1.0F;
						break;
					case 10:
					case 12:
						offset = 2.0F;
						break;
				}

				TileEntityBlockNewSkullRender.instance.renderHead(-0.5F, 0.0F, -0.5F + offset * 0.0625F, 1, 180.0F, head.getItemDamage(), head.hasTagCompound() ? head.getTagCompound().getString("SkullOwner") : null);
				GL11.glPopMatrix();

				if (!model.bipedHead.isHidden) {
					model.bipedHeadwear.isHidden = true;
					model.bipedHead.isHidden = true;
				}
			} else if (model.bipedHead.isHidden) {
				model.bipedHeadwear.isHidden = false;
				model.bipedHead.isHidden = false;
			}
		}
	}
}