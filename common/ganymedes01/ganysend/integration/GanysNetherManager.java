package ganymedes01.ganysend.integration;

import ganymedes01.ganysend.blocks.ModBlocks;
import ganymedes01.ganysend.items.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import cpw.mods.fml.common.event.FMLInterModComms;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class GanysNetherManager {

	public static void init() {
		addMagmaticCentrifugeRecipe(new ItemStack(ModBlocks.rawEndium), new ItemStack(ModBlocks.rawEndium), new ItemStack(ModItems.endiumIngot, 2), new ItemStack(ModItems.endiumIngot, 1, 1));
	}

	private static final void addMagmaticCentrifugeRecipe(ItemStack material1, ItemStack material2, ItemStack... result) {
		if (result.length > 4 || material1 == null || material2 == null)
			return;
		else
			for (ItemStack res : result)
				if (res == null)
					return;

		NBTTagCompound data = new NBTTagCompound();
		NBTTagList tagList = new NBTTagList();
		NBTTagCompound tagCompound;

		tagCompound = new NBTTagCompound();
		tagCompound.setByte("material1", (byte) 0);
		material1.writeToNBT(tagCompound);
		tagList.appendTag(tagCompound);

		tagCompound = new NBTTagCompound();
		tagCompound.setByte("material2", (byte) 1);
		material2.writeToNBT(tagCompound);
		tagList.appendTag(tagCompound);

		for (int i = 0; i < result.length; i++)
			if (result[i] != null) {
				tagCompound = new NBTTagCompound();
				tagCompound.setByte("result", (byte) (i + 2));
				result[i].writeToNBT(tagCompound);
				tagList.appendTag(tagCompound);
			}
		data.setTag("Recipe", tagList);

		FMLInterModComms.sendMessage("ganysnether", "addCentrifugeRecipe", data);
	}
}