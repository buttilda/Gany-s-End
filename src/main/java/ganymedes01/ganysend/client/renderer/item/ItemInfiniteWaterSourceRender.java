package ganymedes01.ganysend.client.renderer.item;

import ganymedes01.ganysend.blocks.ModBlocks;
import ganymedes01.ganysend.client.model.ModelInfiniteWaterSource;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.fluids.FluidRegistry;

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

	private final ModelInfiniteWaterSource model = new ModelInfiniteWaterSource();

	public static final ItemInfiniteWaterSourceRender INSTANCE = new ItemInfiniteWaterSourceRender();

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
		boolean isCreative = item.getItem() == Item.getItemFromBlock(ModBlocks.creativeInfiniteFluidSource);
		switch (type) {
			case ENTITY: {
				renderInfiniteWaterSource(0.0F, 0.0F, 0.0F, isCreative);
				break;
			}
			case EQUIPPED: {
				renderInfiniteWaterSource(0.5F, 0.5F, 0.5F, isCreative);
				break;
			}
			case EQUIPPED_FIRST_PERSON: {
				renderInfiniteWaterSource(0.5F, 0.5F, 0.5F, isCreative);
				break;
			}
			case INVENTORY: {
				renderInfiniteWaterSource(0.5F, 0.5F, 0.5F, isCreative);
				break;
			}
			default:
				break;
		}
	}

	private void renderInfiniteWaterSource(float x, float y, float z, boolean isCreative) {
		GL11.glPushMatrix();
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glTranslatef(x - 0.5F, y + 0.5F, z + 0.5F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);

		FMLClientHandler.instance().getClient().renderEngine.bindTexture(Utils.getResource(Utils.getEntityTexture(Strings.INFINITE_WATER_SOURCE_NAME)));
		model.renderAxis();
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(TextureMap.locationBlocksTexture);

		if (!isCreative)
			model.renderCore(FluidRegistry.WATER.getStillIcon(), ModBlocks.infiniteWaterSource.getIcon(0, 0), 0xFFFFFF);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}
}