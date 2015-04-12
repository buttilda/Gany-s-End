package ganymedes01.ganysend.client.renderer.item;

import ganymedes01.ganysend.client.OpenGLHelper;
import ganymedes01.ganysend.client.renderer.tileentity.TileEntityBlockSkullRender;
import ganymedes01.ganysend.lib.SkullTypes;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTUtil;
import net.minecraftforge.client.IItemRenderer;

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
public class AnchoredEnderChestRender implements IItemRenderer {

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return type != ItemRenderType.FIRST_PERSON_MAP;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
		int skullType = stack.getItemDamage();
		GameProfile profile = null;

		boolean isVanilla = stack.getItem() == Items.skull;
		if (!isVanilla) {
			if (stack.hasTagCompound() && stack.getTagCompound().hasKey("SkullOwner"))
				profile = NBTUtil.func_152459_a(stack.getTagCompound().getCompoundTag("SkullOwner"));

			switch (SkullTypes.values()[skullType]) {
				case witch:
				case wildDeer:
				case horseBlack:
				case horseBrown:
				case horseChestnut:
				case horseCreamy:
				case horseDarkBrown:
				case horseGrey:
				case horseWhite:
				case donkey:
				case mule:
				case horseUndead:
				case horseSkeleton:
					OpenGLHelper.scale(0.75, 0.75, 0.75);
					OpenGLHelper.translate(0, -0.45, 0);
					break;
				case enderDragon:
				case pinchBeetle:
					OpenGLHelper.scale(0.75, 0.75, 0.75);
					break;
				default:
					break;
			}
		}

		switch (type) {
			case ENTITY:
				if (!stack.isOnItemFrame())
					OpenGLHelper.scale(0.5, 0.5, 0.5);
				renderSkull(-0.25F, -0.5F, -0.5F, skullType, profile, isVanilla);
				break;
			case EQUIPPED:
				renderSkull(0.5F, 0.0F, 0.0F, skullType, profile, isVanilla);
				break;
			case EQUIPPED_FIRST_PERSON:
				renderSkull(0.75F, 0.25F, 0.4F, skullType, profile, isVanilla);
				break;
			case INVENTORY:
				OpenGLHelper.scale(1.5, 1.5, 1.5);
				renderSkull(0.75F, 0.30F, 0.5F, skullType, profile, isVanilla);
				break;
			default:
				break;
		}
	}

	private void renderSkull(float x, float y, float z, int type, GameProfile name, boolean isVanilla) {
		OpenGLHelper.pushMatrix();
		OpenGLHelper.translate(x, y, z);
		if (isVanilla)
			TileEntitySkullRenderer.field_147536_b.func_152674_a(0, 0, 0, 0, 0, type, name);
		else
			TileEntityBlockSkullRender.instance.renderHead(0, 0, 0, 0, 0, type, name);
		OpenGLHelper.popMatrix();
	}
}