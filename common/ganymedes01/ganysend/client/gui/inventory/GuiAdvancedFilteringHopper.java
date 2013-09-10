package ganymedes01.ganysend.client.gui.inventory;

import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.inventory.ContainerAdvancedFilteringHopper;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.tileentities.TileEntityAdvancedFilteringHopper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

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
public class GuiAdvancedFilteringHopper extends GuiContainer {

	private TileEntityAdvancedFilteringHopper hopper;

	public GuiAdvancedFilteringHopper(InventoryPlayer inventory, TileEntityAdvancedFilteringHopper tile) {
		super(new ContainerAdvancedFilteringHopper(inventory, tile));
		ySize = 151;
		hopper = tile;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		fontRenderer.drawString(StatCollector.translateToLocal(hopper.getInvName()), 8, 6, 4210752);
		fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
		fontRenderer.drawString(hopper.getLine(), 10 - hopper.getLine().length() / 2, 42, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(new ResourceLocation(Utils.getGUITexture(Strings.ADVANCED_FILTERING_HOPPER_NAME)));
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		drawTexturedModalRect(j, k, 0, 0, xSize, ySize);
	}
}
