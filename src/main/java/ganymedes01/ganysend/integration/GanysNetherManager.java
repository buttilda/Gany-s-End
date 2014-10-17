package ganymedes01.ganysend.integration;

import ganymedes01.ganysend.core.utils.HeadsHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.event.FMLInterModComms;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class GanysNetherManager extends Integration {

	@Override
	public void init() {
		addStackToUndertakers(HeadsHelper.createHeadFor("ganymedes01"), 50);
	}

	@Override
	public void postInit() {
	}

	@Override
	public String getModID() {
		return "ganysnether";
	}

	private void addStackToUndertakers(ItemStack stack, int weight) {
		if (stack != null && stack.stackSize > 0 && weight > 0) {
			NBTTagCompound data = new NBTTagCompound();

			data.setInteger("weight", weight);

			NBTTagCompound tagCompound = new NBTTagCompound();
			stack.writeToNBT(tagCompound);
			data.setTag("stack", tagCompound);

			FMLInterModComms.sendMessage("ganysnether", "addStackToUndertakers", data);
		}
	}
}