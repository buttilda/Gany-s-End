package ganymedes01.ganysend.integration;

import net.minecraftforge.fml.common.Loader;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public abstract class Integration {

	private boolean integrate;

	public abstract void init();

	public abstract void postInit();

	public abstract String getModID();

	public boolean shouldIntegrate() {
		return integrate && Loader.isModLoaded(getModID());
	}

	public void setShouldIntegrate(boolean integrate) {
		this.integrate = integrate;
	}

	@Override
	public String toString() {
		return getModID();
	}
}