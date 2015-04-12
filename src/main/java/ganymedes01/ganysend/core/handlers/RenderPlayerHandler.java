package ganymedes01.ganysend.core.handlers;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.ModItems;
import ganymedes01.ganysend.client.OpenGLHelper;
import ganymedes01.ganysend.client.renderer.tileentity.TileEntityBlockSkullRender;
import ganymedes01.ganysend.lib.SkullTypes;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTUtil;
import net.minecraftforge.client.event.RenderPlayerEvent;

import com.mojang.authlib.GameProfile;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
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

	@SubscribeEvent
	public void renderPlayerEvent(RenderPlayerEvent.Pre event) {
		if (!GanysEnd.enableSkulls)
			return;

		if (event.entityPlayer != null) {
			ModelBiped model = event.renderer.modelBipedMain;
			if (model == null)
				return;

			ItemStack head = event.entityPlayer.inventory.armorItemInSlot(3);
			if (head != null && head.getItem() == ModItems.skull)
				setHiddenState(model, true);
			else if (head == null || !isHeadcrumbsHead(head.getItem()))
				setHiddenState(model, false);
		}
	}

	private boolean isHeadcrumbsHead(Item item) {
		return GanysEnd.headcrumbsHead != null && GanysEnd.headcrumbsHead == item;
	}

	@SubscribeEvent
	public void renderHelmetEvent(RenderPlayerEvent.Specials.Post event) {
		if (!GanysEnd.enableSkulls)
			return;

		if (event.entityPlayer != null) {
			ModelBiped model = event.renderer.modelBipedMain;

			ItemStack head = event.entityPlayer.inventory.armorItemInSlot(3);
			if (head != null && head.getItem() == ModItems.skull) {
				OpenGLHelper.colour(1.0F, 1.0F, 1.0F);

				setHiddenState(model, false);
				model.bipedHead.postRender(0.0625F);
				setHiddenState(model, true);

				OpenGLHelper.pushMatrix();
				OpenGLHelper.scale(1.0F, -1.0F, -1.0F);

				float offset;
				switch (SkullTypes.values()[head.getItemDamage()]) {
					case cow:
					case mooshroom:
					case slimeBeetle:
					case fireBeetle:
					case pinchBeetle:
					case towerGolem:
						offset = 1.0F;
						break;
					case wolf:
					case chicken:
					case wildDeer:
					case ocelot:
					case ocelotBlack:
					case ocelotRed:
					case ocelotSiamese:
					case silverfish:
					case mistWolf:
					case winterWolf:
					case towerwoodBorer:
						offset = 2.0F;
						break;
					case bunnyDutch:
					case bunnyBrown:
					case bunnyWhite:
					case bat:
					case squirrel:
						offset = 3.0F;
						break;
					default:
						offset = 0.0F;
						break;
				}

				GameProfile profile = null;
				if (head.hasTagCompound())
					if (head.getTagCompound().hasKey("SkullOwner", 10))
						profile = NBTUtil.func_152459_a(head.getTagCompound().getCompoundTag("SkullOwner"));
					else if (head.getTagCompound().hasKey("SkullOwner", 8)) {
						String username = head.getTagCompound().getString("SkullOwner");
						profile = new GameProfile(null, username);
					}
				TileEntityBlockSkullRender.instance.renderHead(-0.5F, 0.0F, -0.5F + offset * 0.0625F, 1, 180.0F, head.getItemDamage(), profile);
				OpenGLHelper.popMatrix();
			}
		}
	}

	private void setHiddenState(ModelBiped model, boolean isHidden) {
		model.bipedHead.isHidden = isHidden;
		model.bipedHeadwear.isHidden = isHidden;
	}
}