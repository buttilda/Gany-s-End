package ganymedes01.ganysend.integration;

import ganymedes01.ganysend.ModBlocks;
import ganymedes01.ganysend.ModItems;
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

public class GanysNetherManager extends Integration {

	@Override
	public void init() {
		addMagmaticCentrifugeRecipe(new ItemStack(ModBlocks.rawEndium), new ItemStack(ModBlocks.rawEndium), new ItemStack(ModItems.endiumIngot, 3), new ItemStack(ModItems.endiumIngot, 1, 1));

		ItemStack head = new ItemStack(ModItems.skull, 1, 3);
		head.setTagCompound(new NBTTagCompound());
		head.getTagCompound().setString("SkullOwner", "ganymedes01");
		addStackToUndertakers(head, 50);
	}

	@Override
	public void postInit() {
	}

	@Override
	public String getModID() {
		return "ganysnether";
	}

	private void addMagmaticCentrifugeRecipe(ItemStack material1, ItemStack material2, ItemStack... result) {
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