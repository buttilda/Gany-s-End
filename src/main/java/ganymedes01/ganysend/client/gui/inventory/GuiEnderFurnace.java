package ganymedes01.ganysend.client.gui.inventory;

import ganymedes01.ganysend.client.OpenGLHelper;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.inventory.ContainerEnderFurnace;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.tileentities.TileEntityEnderFurnace;
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
public class GuiEnderFurnace extends GuiContainer {

	private static final ResourceLocation TEXTURE = Utils.getResource(Utils.getGUITexture(Strings.ENDER_FURNACE_NAME));
	private final TileEntityEnderFurnace furnace;

	public GuiEnderFurnace(InventoryPlayer inventory, TileEntityEnderFurnace tile) {
		super(new ContainerEnderFurnace(inventory, tile));
		furnace = tile;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		String name = Strings.ENDER_FURNACE_NAME;
		fontRendererObj.drawString(StatCollector.translateToLocal(name), xSize / 2 - fontRendererObj.getStringWidth(StatCollector.translateToLocal(name)) / 2, 6, 4210752);
		fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		OpenGLHelper.colour(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(TEXTURE);
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		drawTexturedModalRect(j, k, 0, 0, xSize, ySize);

		int burnTime = furnace.getBurnTimeScaled(12);
		if (burnTime > 0)
			drawTexturedModalRect(guiLeft + 14, guiTop + 28 + 12 - burnTime, 176, 12 - burnTime, 14, burnTime + 2);
		int cookTime = furnace.getCookTimeScaled(24);
		drawTexturedModalRect(guiLeft + 92, guiTop + 35, 176, 14, cookTime + 1, 16);
	}
}