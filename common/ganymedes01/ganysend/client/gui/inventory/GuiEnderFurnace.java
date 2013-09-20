package ganymedes01.ganysend.client.gui.inventory;

import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.inventory.ContainerEnderFurnace;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.tileentities.TileEntityEnderFurnace;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

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
public class GuiEnderFurnace extends GuiContainer {

	private TileEntityEnderFurnace furnace;

	public GuiEnderFurnace(InventoryPlayer inventory, TileEntityEnderFurnace tile) {
		super(new ContainerEnderFurnace(inventory, tile));
		furnace = tile;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		fontRenderer.drawString(I18n.getString(furnace.getInvName()), xSize / 2 - fontRenderer.getStringWidth(I18n.getString(furnace.getInvName())) / 2, 6, 4210752);
		fontRenderer.drawString(I18n.getString("container.inventory"), 8, ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(new ResourceLocation(Utils.getGUITexture(Strings.ENDER_FURNACE_NAME)));
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, xSize, ySize);

		if (furnace.isBurning()) {
			int burnTime = furnace.getBurnTimeRemainingScaled(12);
			drawTexturedModalRect(k + 56, l + 36 + 12 - burnTime, 176, 12 - burnTime, 14, burnTime + 2);
		}

		drawTexturedModalRect(k + 79, l + 35, 176, 14, furnace.getCookProgressScaled(24) + 1, 16);
	}
}
