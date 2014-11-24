package ganymedes01.ganysend.integration;

import ganymedes01.ganysend.ModBlocks;
import ganymedes01.ganysend.ModItems;
import ganymedes01.ganysend.core.handlers.HandlerEvents;
import ganymedes01.ganysend.lib.ModMaterials;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import tconstruct.TConstruct;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.crafting.Smeltery;
import tconstruct.library.util.IPattern;
import tconstruct.smeltery.TinkerSmeltery;
import tconstruct.tools.TinkerTools;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class TinkersConstructManager extends Integration {

	public static int ENDIUM_MATERIAL_ID = 666;

	@Override
	public void init() {
		int materialID = ENDIUM_MATERIAL_ID;
		String materialName = "Endium";
		int level = ModMaterials.ENDIUM_TOOLS.getHarvestLevel();
		int durability = ModMaterials.ENDIUM_TOOLS.getMaxUses();
		int miningspeed = (int) (ModMaterials.ENDIUM_TOOLS.getEfficiencyOnProperMaterial() * 100);
		int handleModifier = 2;
		int primaryColor = 0x2A233E;

		TConstructRegistry.addToolMaterial(materialID, materialName, level, durability, miningspeed, 1, handleModifier, 0, 0, "", primaryColor);
		TConstructRegistry.addBowMaterial(materialID, durability, 30, 1);

		TConstructRegistry.addDefaultToolPartMaterial(materialID);
		TConstructRegistry.addDefaultShardMaterial(materialID);

		Fluid endium = new Fluid("endium") {

			@Override
			public IIcon getStillIcon() {
				return HandlerEvents.endium_still;
			}

			@Override
			public IIcon getFlowingIcon() {
				return HandlerEvents.endium_flow;
			}
		};
		FluidRegistry.registerFluid(endium);

		Smeltery.addMelting(new ItemStack(ModItems.endiumIngot, 1, 0), ModBlocks.endiumBlock, 0, 100, new FluidStack(endium, TConstruct.ingotLiquidValue));
		Smeltery.addMelting(new ItemStack(ModItems.endiumIngot, 1, 1), ModBlocks.endiumBlock, 0, 100, new FluidStack(endium, TConstruct.nuggetLiquidValue));
		Smeltery.addMelting(ModBlocks.endiumBlock, 0, 100, new FluidStack(endium, TConstruct.blockLiquidValue));
		Smeltery.addMelting(ModBlocks.rawEndium, 0, 1000, new FluidStack(endium, TConstruct.oreLiquidValue));
		TConstructRegistry.getBasinCasting().addCastingRecipe(new ItemStack(ModBlocks.endiumBlock), new FluidStack(endium, TConstruct.blockLiquidValue), 50);

		for (int i = 0; i < TinkerTools.patternOutputs.length; i++)
			if (TinkerTools.patternOutputs[i] != null) {
				ItemStack cast = new ItemStack(TinkerSmeltery.metalPattern, 1, i + 1);

				TConstructRegistry.getTableCasting().addCastingRecipe(cast, new FluidStack(TinkerSmeltery.moltenAlubrassFluid, TConstruct.ingotLiquidValue), new ItemStack(TinkerTools.patternOutputs[i], 1, Short.MAX_VALUE), false, 50);
				TConstructRegistry.getTableCasting().addCastingRecipe(cast, new FluidStack(TinkerSmeltery.moltenGoldFluid, TConstruct.ingotLiquidValue * 2), new ItemStack(TinkerTools.patternOutputs[i], 1, Short.MAX_VALUE), false, 50);

				int amount = ((IPattern) TinkerSmeltery.metalPattern).getPatternCost(cast) * TConstruct.ingotLiquidValue / 2;
				ItemStack metalCast = new ItemStack(TinkerTools.patternOutputs[i], 1, materialID);
				TConstructRegistry.getTableCasting().addCastingRecipe(metalCast, new FluidStack(endium, amount), cast, 50);
				Smeltery.addMelting(metalCast, ModBlocks.endiumBlock, 0, 100, new FluidStack(endium, amount));
			}
	}

	@Override
	public void postInit() {
	}

	@Override
	public String getModID() {
		return "TConstruct";
	}
}