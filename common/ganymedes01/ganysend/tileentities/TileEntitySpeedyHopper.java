package ganymedes01.ganysend.tileentities;

import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.item.ItemStack;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class TileEntitySpeedyHopper extends TileEntityFilteringHopper {

	@Override
	public String getLine1() {
		return "";
	}

	@Override
	public String getLine2() {
		return "";
	}

	@Override
	public int getMaxCoolDown() {
		return 1;
	}

	@Override
	public String getInvName() {
		return Utils.getConainerName(Strings.SPEEDY_HOPPER_NAME);
	}

	@Override
	public boolean isFilter() {
		return false;
	}

	@Override
	protected boolean shouldPull(ItemStack stack) {
		return true;
	}
}