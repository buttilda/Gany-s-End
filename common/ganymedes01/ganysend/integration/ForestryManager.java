package ganymedes01.ganysend.integration;

import ganymedes01.ganysend.blocks.ModBlocks;
import ganymedes01.ganysend.items.ModItems;
import cpw.mods.fml.common.event.FMLInterModComms;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class ForestryManager extends Integration {

	@Override
	public void init() {
		StringBuffer message = new StringBuffer();
		message.append(ModBlocks.endstoneBrick.blockID + ";");
		message.append(ModBlocks.enderpearlBlock.blockID + ":1;");
		FMLInterModComms.sendMessage(getModID(), "add-backpack-items", "builder@" + message.toString());

		message = new StringBuffer();
		message.append(ModBlocks.rawEndium.blockID + ";");
		message.append(ModItems.endiumIngot.itemID + ":0;");
		FMLInterModComms.sendMessage(getModID(), "add-backpack-items", "miner@" + message.toString());

		message = new StringBuffer();
		message.append(ModBlocks.enderFlower.blockID + ";");
		FMLInterModComms.sendMessage(getModID(), "add-backpack-items", "forester@" + message.toString());
	}

	@Override
	public void postInit() {
	}

	@Override
	public String getModID() {
		return "Forestry";
	}
}