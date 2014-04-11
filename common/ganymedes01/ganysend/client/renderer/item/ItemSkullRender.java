package ganymedes01.ganysend.client.renderer.item;

import ganymedes01.ganysend.client.renderer.tileentity.TileEntityBlockNewSkullRender;
import ganymedes01.ganysend.lib.SkullTypes;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

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
public class ItemSkullRender implements IItemRenderer {

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
		String name = null;
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("SkullOwner"))
			name = stack.getTagCompound().getString("SkullOwner");
		boolean isVanilla = stack.itemID == Item.skull.itemID;
		if (!isVanilla)
			if (skullType == SkullTypes.witch.ordinal() || skullType == SkullTypes.wildDeer.ordinal() || skullType == SkullTypes.witch.ordinal()) {
				GL11.glScaled(0.75, 0.75, 0.75);
				GL11.glTranslated(0, -0.45, 0);
			} else if (skullType == SkullTypes.enderDragon.ordinal())
				GL11.glScaled(0.75, 0.75, 0.75);

		switch (type) {
			case ENTITY: {
				if (isVanilla)
					GL11.glScaled(2, 2, 2);
				renderSkull(-0.25F, -0.5F, -0.5F, skullType, name, isVanilla);
				break;
			}
			case EQUIPPED: {
				renderSkull(0.5F, 0.0F, 0.5F, skullType, name, isVanilla);
				break;
			}
			case EQUIPPED_FIRST_PERSON: {
				renderSkull(0.75F, 0.25F, 0.4F, skullType, name, isVanilla);
				break;
			}
			case INVENTORY: {
				GL11.glScaled(1.5, 1.5, 1.5);
				renderSkull(0.75F, 0.30F, 0.5F, skullType, name, isVanilla);
				break;
			}
			default:
				break;
		}
	}

	private void renderSkull(float x, float y, float z, int type, String name, boolean isVanilla) {
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, z);
		if (isVanilla)
			TileEntitySkullRenderer.skullRenderer.func_82393_a(0, 0, 0, 0, 0, type, name);
		else
			TileEntityBlockNewSkullRender.instance.renderHead(0, 0, 0, 0, 0, type, name);
		GL11.glPopMatrix();
	}
}