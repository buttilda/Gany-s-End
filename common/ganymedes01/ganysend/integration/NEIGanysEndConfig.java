package ganymedes01.ganysend.integration;

import ganymedes01.ganysend.blocks.ModBlocks;
import ganymedes01.ganysend.items.ModItems;
import ganymedes01.ganysend.lib.Reference;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class NEIGanysEndConfig implements IConfigureNEI {

	@Override
	public void loadConfig() {
		API.hideItem(ModBlocks.enderToggler_air.blockID);
		API.hideItem(ModBlocks.blockNewSkull.blockID);

		ItemStack stack = new ItemStack(ModItems.itemNewSkull, 1, 3);
		stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setString("SkullOwner", "ganymedes01");
		API.addNBTItem(stack);
	}

	@Override
	public String getName() {
		return Reference.MOD_NAME;
	}

	@Override
	public String getVersion() {
		return Reference.VERSION_NUMBER;
	}
}