package ganymedes01.ganysend.integration;

import ganymedes01.ganysend.ModBlocks;
import ganymedes01.ganysend.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
		addEMCValueToBlock(ModBlocks.rawEndium, 2052);
		addEMCValueToItem(ModItems.endiumIngot, 2052);
		addEMCValueToItem(ModItems.infiniteBucket, 8196);
		addEMCValue(new ItemStack(ModItems.skull, 1, OreDictionary.WILDCARD_VALUE), 2048);
		addEMCValue(new ItemStack(ModItems.skull, 1, 17), 49152);
	}

	private void addEMCValueToBlock(Block block, float value) {
		addEMCValue(new ItemStack(block), value);
	}

	private void addEMCValueToItem(Item item, float value) {
		addEMCValue(new ItemStack(item), value);
	}

	private void addEMCValue(Object obj, float value) {
		String string = "{\"wrappedStack\":{\"className\":\"%s\",\"stackSize\":1,\"wrappedStack\":{%s}},\"emcValue\":{\"OMNI\":0.0,\"CORPOREAL\":%f,\"KINETIC\":0.0,\"TEMPORAL\":0.0,\"ESSENTIA\":0.0,\"AMORPHOUS\":0.0,\"VOID\":0.0}}";

		String stack = null;
		String className = null;
		if (obj instanceof ItemStack) {
			ItemStack s = (ItemStack) obj;
			stack = "\"stackSize\":" + s.stackSize + ",\"itemID\":" + s.getItem() + ",\"itemDamage\":" + s.getItemDamage();
			className = "ItemStack";
		} else if (obj instanceof String) {
			stack = "\"oreName\":\"" + (String) obj + "\",\"stackSize\":1";
			className = "OreStack";
		}

		if (stack != null && className != null)
			FMLInterModComms.sendMessage(getModID(), "emc-assign-value-pre", String.format(string, className, stack, value));
	}

	@Override
	public void postInit() {
	}

	@Override
	public String getModID() {
		return "EE3";
	}
}