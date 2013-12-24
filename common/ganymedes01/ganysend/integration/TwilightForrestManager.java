package ganymedes01.ganysend.integration;

import ganymedes01.ganysend.core.utils.HeadsHelper;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class TwilightForrestManager extends Integration {

	@Override
	public void init() {
		HeadsHelper.useTwilightForrestMobs = true;
	}

	@Override
	public void postInit() {
	}

	@Override
	public String getModID() {
		return "TwilightForest";
	}
}