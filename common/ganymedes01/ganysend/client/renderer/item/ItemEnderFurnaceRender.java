package ganymedes01.ganysend.client.renderer.item;

import ganymedes01.ganysend.blocks.ModBlocks;
import net.minecraft.client.renderer.RenderBlocks;
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
public class ItemEnderFurnaceRender implements IItemRenderer {

	private final RenderBlocks blockRenderer = new RenderBlocks();

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
		GL11.glPushMatrix();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		blockRenderer.renderBlockAsItem(ModBlocks.enderFurnace_off, 3, 1.0F);
		GL11.glPopMatrix();
	}
}
