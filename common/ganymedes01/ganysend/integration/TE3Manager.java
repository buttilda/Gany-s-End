package ganymedes01.ganysend.integration;

import ganymedes01.ganysend.blocks.ModBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.common.event.FMLInterModComms;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class TE3Manager extends Integration {

	@Override
	public void init() {
		addMagmaCruicibleRecipe(2000, new ItemStack(ModBlocks.enderFlower), new FluidStack(FluidRegistry.getFluid("ender"), 64));
	}

	@Override
	public void postInit() {
	}

	@Override
	public String getModID() {
		return "ThermalExpansion";
	}

	private void addMagmaCruicibleRecipe(int energy, ItemStack input, FluidStack output) {
		NBTTagCompound data = new NBTTagCompound();

		data.setInteger("energy", energy);

		NBTTagCompound inputCompound = new NBTTagCompound();
		input.writeToNBT(inputCompound);
		data.setCompoundTag("input", inputCompound);

		NBTTagCompound outputCompound = new NBTTagCompound();
		output.writeToNBT(outputCompound);
		data.setCompoundTag("output", outputCompound);

		FMLInterModComms.sendMessage(getModID(), "CrucibleRecipe", data);
	}
}