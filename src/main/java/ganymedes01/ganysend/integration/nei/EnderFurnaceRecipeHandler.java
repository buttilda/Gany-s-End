package ganymedes01.ganysend.integration.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import ganymedes01.ganysend.client.OpenGLHelper;
import ganymedes01.ganysend.client.gui.inventory.GuiEnderFurnace;
import ganymedes01.ganysend.core.utils.InventoryUtils;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Reference;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.recipes.EnderFurnaceFuelsRegistry;
import ganymedes01.ganysend.recipes.EnderFurnaceFuelsRegistry.FuelEntry;
import ganymedes01.ganysend.recipes.EnderFurnaceRecipe;
import ganymedes01.ganysend.recipes.EnderFurnaceRegistry;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class EnderFurnaceRecipeHandler extends TemplateRecipeHandler {

	private static ArrayList<PositionedStack> fuels;

	public EnderFurnaceRecipeHandler() {
		if (fuels == null) {
			fuels = new ArrayList<PositionedStack>();

			for (FuelEntry entry : EnderFurnaceFuelsRegistry.INSTANCE.getRecipes()) {
				Object obj = entry.getFuel();
				if (obj instanceof String)
					obj = OreDictionary.getOres((String) obj);
				fuels.add(new PositionedStack(obj, 8, 34));
			}
		}
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GuiEnderFurnace.class;
	}

	@Override
	public String getRecipeName() {
		return StatCollector.translateToLocal(Utils.getConainerName(Strings.ENDER_FURNACE_NAME));
	}

	public String getRecipeId() {
		return Reference.MOD_ID + "." + Strings.ENDER_FURNACE_NAME;
	}

	@Override
	public String getGuiTexture() {
		return Utils.getGUITexture(Strings.ENDER_FURNACE_NAME);
	}

	@Override
	public String getOverlayIdentifier() {
		return Strings.ENDER_FURNACE_NAME;
	}

	@Override
	public void loadTransferRects() {
		transferRects.add(new RecipeTransferRect(new Rectangle(87, 25, 25, 16), getRecipeId()));
	}

	@Override
	public void drawBackground(int recipe) {
		OpenGLHelper.colour(1.0F, 1.0F, 1.0F, 1.0F);
		GuiDraw.changeTexture(getGuiTexture());
		GuiDraw.drawTexturedModalRect(0, 0, 5, 10, 160, 65);
	}

	@Override
	public void drawExtras(int recipe) {
		drawProgressBar(9, 17, 176, 0, 14, 14, 48, 7);
		drawProgressBar(87, 25, 176, 14, 24, 16, 48, 0);
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId.equals(getRecipeId()))
			for (EnderFurnaceRecipe recipe : EnderFurnaceRegistry.INSTANCE.getRecipes())
				arecipes.add(new CachedEnderFurnaceRecipe(recipe));
		else
			super.loadCraftingRecipes(outputId, results);
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		for (EnderFurnaceRecipe recipe : EnderFurnaceRegistry.INSTANCE.getRecipes())
			if (InventoryUtils.areStacksTheSame(recipe.getOutput(), result, false))
				arecipes.add(new CachedEnderFurnaceRecipe(recipe));
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		for (EnderFurnaceRecipe recipe : EnderFurnaceRegistry.INSTANCE.getRecipes())
			if (recipe.isPartOfInput(ingredient))
				arecipes.add(new CachedEnderFurnaceRecipe(recipe));
	}

	class CachedEnderFurnaceRecipe extends CachedRecipe {

		private final PositionedStack[] input;
		private final PositionedStack result;

		CachedEnderFurnaceRecipe(EnderFurnaceRecipe recipe) {
			result = new PositionedStack(recipe.getOutput(), 124, 25);

			Object[] input = recipe.getInput();
			this.input = new PositionedStack[input.length];
			for (int i = 0; i < 2; i++)
				for (int j = 0; j < 2; j++) {
					int index = i + j * 2;
					if (index < input.length && input[index] != null)
						this.input[index] = makePosStack(input[index], 46 + i * 18, 16 + j * 18);
				}
		}

		private PositionedStack makePosStack(Object obj, int x, int y) {
			if (obj instanceof String)
				return new PositionedStack(OreDictionary.getOres((String) obj), x, y);
			else
				return new PositionedStack(obj, x, y);
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(cycleticks / 20, Arrays.asList(input));
		}

		@Override
		public PositionedStack getResult() {
			return result;
		}

		@Override
		public PositionedStack getOtherStack() {
			return fuels.get(cycleticks / 48 % fuels.size());
		}
	}
}