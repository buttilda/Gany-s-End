package ganymedes01.ganysend.client.gui.inventory;

import ganymedes01.ganysend.client.OpenGLHelper;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.inventory.ContainerFilteringHopper;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.tileentities.TileEntityFilteringHopper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

@SideOnly(Side.CLIENT)
public class GuiBasicFilteringHopper extends GuiContainer {

	private final ResourceLocation TEXTURE;
	private final TileEntityFilteringHopper hopper;

	public GuiBasicFilteringHopper(InventoryPlayer inventory, TileEntityFilteringHopper tile) {
		super(new ContainerFilteringHopper(inventory, tile));
		hopper = tile;
		if (tile.isFilter())
			TEXTURE = new ResourceLocation(Utils.getGUITexture(Strings.BASIC_FILTERING_HOPPER_NAME));
		else {
			ySize = 133;
			TEXTURE = new ResourceLocation("textures/gui/container/hopper.png");
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		String invtName = StatCollector.translateToLocal(Strings.BASIC_FILTERING_HOPPER_NAME);
		fontRendererObj.drawString(invtName, xSize / 2 - fontRendererObj.getStringWidth(invtName) / 2, 6, 4210752);
		fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);

		fontRendererObj.drawString(hopper.getLine(), 76 - fontRendererObj.getStringWidth(hopper.getLine()), 57, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		OpenGLHelper.colour(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(TEXTURE);
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		drawTexturedModalRect(j, k, 0, 0, xSize, ySize);
	}
}