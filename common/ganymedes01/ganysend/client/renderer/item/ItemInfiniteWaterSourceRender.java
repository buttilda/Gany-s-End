package ganymedes01.ganysend.client.renderer.item;

import ganymedes01.ganysend.client.model.ModelInfiniteWaterSource;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.item.ItemStack;
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
public class ItemInfiniteWaterSourceRender implements IItemRenderer {

	private int textureOffsetY = 0;
	private int coolDown = 10;
	private final ModelInfiniteWaterSource model = new ModelInfiniteWaterSource();

	public ItemInfiniteWaterSourceRender() {
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return type != ItemRenderType.FIRST_PERSON_MAP;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		switch (type) {
			case ENTITY: {
				renderInfiniteWaterSource(0.0F, 0.0F, 0.0F);
				break;
			}
			case EQUIPPED: {
				renderInfiniteWaterSource(0.5F, 0.5F, 0.5F);
				break;
			}
			case EQUIPPED_FIRST_PERSON: {
				renderInfiniteWaterSource(0.5F, 0.5F, 0.5F);
				break;
			}
			case INVENTORY: {
				renderInfiniteWaterSource(0.5F, 0.5F, 0.5F);
				break;
			}
			default:
				break;
		}
	}

	private void renderInfiniteWaterSource(float x, float y, float z) {
		GL11.glPushMatrix();
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glTranslatef(x - 0.5F, y + 0.5F, z + 0.5F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);

		FMLClientHandler.instance().getClient().renderEngine.bindTexture(Utils.getResource(Utils.getEntityTexture(Strings.INFINITE_WATER_SOURCE_NAME)));
		model.renderAxis();
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(Utils.getResource(Utils.getEntityTexture(Strings.INFINITE_WATER_SOURCE_NAME + "_core")));
		coolDown--;
		if (coolDown <= 0) {
			textureOffsetY += 16;
			if (textureOffsetY >= 512)
				textureOffsetY = 0;
			coolDown = 10;
		}
		model.renderCore(textureOffsetY);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}
}