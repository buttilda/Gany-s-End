package ganymedes01.ganysend.integration;

import ganymedes01.ganysend.blocks.ModBlocks;
import ganymedes01.ganysend.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.event.FMLInterModComms;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class EE3Manager extends Integration {

	@Override
	public void init() {
		addEMCValueToBlock(ModBlocks.enderFlower, 128);
		addEMCValueToItem(ModItems.endiumIngot, 2052);
		addEMCValueToItem(ModItems.infiniteBucket, 8196);
		addEMCValue(new ItemStack(ModItems.itemNewSkull, 1, OreDictionary.WILDCARD_VALUE), 2048);
		addEMCValue(new ItemStack(ModItems.itemNewSkull, 1, 17), 49152);
	}

	private void addEMCValueToBlock(Block block, float value) {
		addEMCValue(new ItemStack(block), value);
	}

	private void addEMCValueToItem(Item item, float value) {
		addEMCValue(new ItemStack(item), value);
	}

	private void addEMCValue(Object obj, float value) {
		NBTTagCompound data = new NBTTagCompound();

		data.setFloat("emcValue", value);

		if (obj instanceof ItemStack) {
			NBTTagCompound stackCompound = new NBTTagCompound();
			((ItemStack) obj).writeToNBT(stackCompound);
			data.setCompoundTag("itemStack", stackCompound);
		} else if (obj instanceof String)
			data.setString("oreName", (String) obj);

		FMLInterModComms.sendMessage(getModID(), "emc-assign-value-pre", data);
	}

	private void addPostEMCValue(Object obj, float value) {
		NBTTagCompound data = new NBTTagCompound();

		data.setFloat("emcValue", value);

		if (obj instanceof ItemStack) {
			NBTTagCompound stackCompound = new NBTTagCompound();
			((ItemStack) obj).writeToNBT(stackCompound);
			data.setCompoundTag("itemStack", stackCompound);
		} else if (obj instanceof String)
			data.setString("oreName", (String) obj);

		FMLInterModComms.sendMessage(getModID(), "emc-assign-value-post", data);
	}

	@Override
	public void postInit() {
	}

	@Override
	public String getModID() {
		return "EE3";
	}
}