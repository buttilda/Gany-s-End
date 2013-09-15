package ganymedes01.ganysend.client.renderer.item;

import ganymedes01.ganysend.client.model.ModelHead;
import ganymedes01.ganysend.client.model.ModelPlayerInventory;
import ganymedes01.ganysend.client.model.ModelPlayerInventoryInside;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

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
public class ItemPlayerInventoryRender implements IItemRenderer {

	private ModelPlayerInventory outside;
	private ModelPlayerInventoryInside inside;
	private ModelHead head;

	public ItemPlayerInventoryRender() {
		outside = new ModelPlayerInventory();
		inside = new ModelPlayerInventoryInside();
		head = new ModelHead();
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
				renderPlayerInventory(0.5F, 0.5F, 0.5F);
				break;
			}
			case EQUIPPED: {
				renderPlayerInventory(1.0F, 1.0F, 1.0F);
				break;
			}
			case EQUIPPED_FIRST_PERSON: {
				renderPlayerInventory(1.0F, 1.0F, 1.0F);
				break;
			}
			case INVENTORY: {
				renderPlayerInventory(0.0F, 0.075F, 0.0F);
				break;
			}
			default:
				break;
		}
	}

	private void renderPlayerInventory(float x, float y, float z) {
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(new ResourceLocation(Utils.getEntityTexture(Strings.PLAYER_INVENTORY_NAME)));
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, z);
		GL11.glRotatef(180, 1, 0, 0);
		GL11.glRotatef(-90, 0, 1, 0);
		GL11.glScaled(1.0D, 1.0D, 1.0D);
		outside.render();
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(new ResourceLocation(Utils.getEntityTexture(Strings.PLAYER_INVENTORY_NAME + "1")));
		inside.render();
		GL11.glPopMatrix();

		FMLClientHandler.instance().getClient().renderEngine.bindTexture(AbstractClientPlayer.locationStevePng);
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glTranslatef(x - 0.5F, y - 0.75F, z - 0.5F);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glScalef(-1.0F, -1.0F, 1.0F);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glRotatef(360.0F, 0, 1, 0);
		head.render((Entity) null, 0.0F, 0.0F, 0.0F, -90.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();
	}
}
