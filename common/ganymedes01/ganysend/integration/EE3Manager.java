package ganymedes01.ganysend.integration;

import ganymedes01.ganysend.blocks.ModBlocks;
import ganymedes01.ganysend.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.pahimar.ee3.addon.AddonHandler;
import com.pahimar.ee3.api.OreStack;
import com.pahimar.ee3.emc.EmcValue;

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
		addEMCValueToStack(new ItemStack(ModItems.itemNewSkull, 1, OreDictionary.WILDCARD_VALUE), 2048);
		addEMCValueToStack(new ItemStack(ModItems.itemNewSkull, 1, 17), 49152);
	}

	private void addEMCValueToBlock(Block block, float value) {
		addEMCValueToStack(new ItemStack(block), value);
	}

	private void addEMCValueToItem(Item item, float value) {
		addEMCValueToStack(new ItemStack(item), value);
	}

	private void addEMCValueToStack(ItemStack stack, float value) {
		AddonHandler.sendPreValueAssignment(stack, new EmcValue(value));
	}

	private void addEMCValueToOre(String ore, float value) {
		AddonHandler.sendPreValueAssignment(new OreStack(ore), new EmcValue(value));
	}

	private void addPostEMCValueToStack(ItemStack stack, float value) {
		AddonHandler.sendPostValueAssignment(stack, new EmcValue(value));
	}

	@Override
	public void postInit() {
	}

	@Override
	public String getModID() {
		return "EE3";
	}
}