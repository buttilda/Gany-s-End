package ganymedes01.ganysend.core.handlers;

import ganymedes01.ganysend.client.renderer.tileentity.TileEntityBlockNewSkullRender;
import ganymedes01.ganysend.items.ModItems;

import java.lang.reflect.Field;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.ForgeSubscribe;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

@SideOnly(Side.CLIENT)
public class RenderPlayerHandler {

	@ForgeSubscribe
	public void renderPlayerEvent(RenderPlayerEvent.Pre event) {
		if (event.entityPlayer != null) {
			ModelBiped model = getBipedModel(event.renderer);
			if (model == null)
				return;

			ItemStack head = event.entityPlayer.inventory.armorItemInSlot(3);
			if (head != null && head.getItem() == ModItems.itemNewSkull)
				setHiddenState(model, true);
			else
				setHiddenState(model, false);
		}
	}

	@ForgeSubscribe
	public void renderHelmetEvent(RenderPlayerEvent.Specials.Post event) {
		if (event.entityPlayer != null) {
			ModelBiped model = getBipedModel(event.renderer);

			if (model == null)
				return;

			ItemStack head = event.entityPlayer.inventory.armorItemInSlot(3);
			if (head != null && head.getItem() == ModItems.itemNewSkull) {
				GL11.glColor3f(1.0F, 1.0F, 1.0F);

				setHiddenState(model, false);
				model.bipedHead.postRender(0.0625F);
				setHiddenState(model, true);

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
					case 18:
						offset = 3.0F;
						break;
					case 21:
						offset = 2.0f;
						break;
				}

				TileEntityBlockNewSkullRender.instance.renderHead(-0.5F, 0.0F, -0.5F + offset * 0.0625F, 1, 180.0F, head.getItemDamage(), head.hasTagCompound() ? head.getTagCompound().getString("SkullOwner") : null);
				GL11.glPopMatrix();
			}
		}
	}

	private void setHiddenState(ModelBiped model, boolean isHidden) {
		model.bipedHead.isHidden = isHidden;
		model.bipedHeadwear.isHidden = isHidden;
	}

	private ModelBiped getBipedModel(RenderPlayer renderer) {
		try {
			Class<? extends RenderPlayer> rendererClass = renderer.getClass();
			if (rendererClass != RenderPlayer.class && RenderPlayer.class.isAssignableFrom(rendererClass))
				rendererClass = (Class<? extends RenderPlayer>) rendererClass.getSuperclass();

			Field f = ReflectionHelper.findField(rendererClass, "field_77109_a", "modelBipedMain");
			f.setAccessible(true);
			return (ModelBiped) f.get(renderer);
		} catch (Exception e) {
			return null;
		}
	}
}