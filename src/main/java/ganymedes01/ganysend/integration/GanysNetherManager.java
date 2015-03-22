package ganymedes01.ganysend.integration;

import ganymedes01.ganysend.core.utils.HeadsHelper;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class GanysNetherManager extends Integration {

	@Override
	public void init() {
		ChestGenHooks info = ChestGenHooks.getInfo("ganysnether.undertaker");
		info.addItem(new WeightedRandomChestContent(HeadsHelper.createHeadFor("ganymedes01"), 0, 1, 5));
	}

	@Override
	public void postInit() {
	}

	@Override
	public String getModID() {
		return "ganysnether";
	}
}