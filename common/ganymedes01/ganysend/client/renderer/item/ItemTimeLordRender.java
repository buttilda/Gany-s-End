package ganymedes01.ganysend.client.renderer.item;

import ganymedes01.ganysend.client.model.ModelTimeLord;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Reference;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

@SideOnly(Side.CLIENT)
public class ItemTimeLordRender implements IItemRenderer {

	private ModelTimeLord top;
	private ModelTimeLord bottom;

	public ItemTimeLordRender() {
		top = new ModelTimeLord(4);
		bottom = new ModelTimeLord(0);
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return type != ItemRenderType.FIRST_PERSON_HOLDING && type != ItemRenderType.FIRST_PERSON_MAP;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		switch (type) {
			case ENTITY: {
				renderTimeLord(0.5F, 0.5F, 0.5F);
				break;
			}
			case EQUIPPED: {
				renderTimeLord(1.0F, 1.0F, 1.0F);
				break;
			}
			case EQUIPPED_FIRST_PERSON: {
				renderTimeLord(1.0F, 1.0F, 1.0F);
				break;
			}
			case INVENTORY: {
				renderTimeLord(0.0F, 0.075F, 0.0F);
				break;
			}
			default:
				break;
		}
	}

	private void renderTimeLord(float x, float y, float z) {
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(new ResourceLocation(Reference.MOD_ID, Utils.getEntityItemTexture(Strings.TIME_MANIPULATOR_NAME + "_top")));
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, z);
		GL11.glRotatef(180, 1, 0, 0);
		GL11.glRotatef(-90, 0, 1, 0);
		GL11.glScaled(0.5D, 0.5D, 0.5D);
		top.renderAll();
		GL11.glPopMatrix();

		FMLClientHandler.instance().getClient().renderEngine.bindTexture(new ResourceLocation(Reference.MOD_ID, Utils.getEntityItemTexture(Strings.TIME_MANIPULATOR_NAME + "_bottom")));
		GL11.glPushMatrix();
		GL11.glTranslatef(x, (y - 0.5F), z);
		GL11.glRotatef(180, 1, 0, 0);
		GL11.glRotatef(-90, 0, 1, 0);
		GL11.glScaled(0.5D, 0.5D, 0.5D);
		bottom.renderAll();
		GL11.glPopMatrix();
	}
}
