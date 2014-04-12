package ganymedes01.ganysend.core.handlers;

import ganymedes01.ganysend.lib.IMCKeys;
import ganymedes01.ganysend.recipes.EnderFurnaceRecipe;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.event.FMLInterModComms.IMCEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCMessage;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class InterModComms {

	public static void processIMC(IMCEvent event) {
		for (IMCMessage message : event.getMessages())
			if (message.key.equals(IMCKeys.ENDER_FURNACE_RECIPE))
				addEnderFurnaceRecipe(message);
			else if (message.key.equals(IMCKeys.ENDER_FURNACE_FUEL))
				addEnderFurnaceFuel(message);
	}

	private static void addEnderFurnaceRecipe(IMCMessage message) {
		NBTTagCompound nbt = message.getNBTValue();
		if (nbt == null)
			return;

		if (!nbt.hasKey("output") || !nbt.hasKey("input" + 0))
			return;

		ItemStack output = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("output"));
		List inputs = new ArrayList();

		for (int i = 0; i < 4; i++)
			if (nbt.hasKey("input" + i)) {
				Object input = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("input" + i));
				if (input == null)
					input = nbt.getString("input" + i);
				inputs.add(input);
			}

		EnderFurnaceRecipe.addRecipe(output, inputs.toArray());
	}

	private static void addEnderFurnaceFuel(IMCMessage message) {
		NBTTagCompound nbt = message.getNBTValue();
		if (nbt == null)
			return;

		if (!nbt.hasKey("fuel") || !nbt.hasKey("burnTime"))
			return;

		Object fuel = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("fuel"));
		if (fuel == null)
			fuel = nbt.getString("fuel");

		int burnTime = nbt.getInteger("burnTime");

		EnderFurnaceRecipe.addFuel(fuel, burnTime);
	}
}