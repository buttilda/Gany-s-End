package ganymedes01.ganysend.integration.nei;

import ganymedes01.ganysend.client.gui.inventory.GuiEnderFurnace;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Reference;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.recipes.EnderFurnaceRecipe;

import java.awt.Rectangle;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;

import org.lwjgl.opengl.GL11;

import codechicken.core.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class EnderFurnaceRecipeHandler extends TemplateRecipeHandler {

	private static final Random rand = new Random();

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
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
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
			for (EnderFurnaceRecipe recipe : EnderFurnaceRecipe.recipes)
				arecipes.add(new CachedEnderFurnaceRecipe(recipe));
		else
			super.loadCraftingRecipes(outputId, results);
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		for (EnderFurnaceRecipe recipe : EnderFurnaceRecipe.recipes)
			if (EnderFurnaceRecipe.doStacksMatch(recipe.getOutput(), result))
				arecipes.add(new CachedEnderFurnaceRecipe(recipe));
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		label: for (EnderFurnaceRecipe recipe : EnderFurnaceRecipe.recipes)
			for (Object stack : recipe.getInput())
				if (EnderFurnaceRecipe.doStacksMatch(stack, ingredient)) {
					arecipes.add(new CachedEnderFurnaceRecipe(recipe));
					continue label;
				}
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
						this.input[index] = new PositionedStack(getStack(input[index]), 46 + i * 18, 16 + j * 18);
				}
		}

		private Object getStack(Object obj) {
			if (obj instanceof String)
				return OreDictionary.getOres((String) obj);
			return obj;
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
			return new PositionedStack(new ItemStack(Item.enderPearl), 8, 34);
		}
	}
}