package ganymedes01.ganysend.tileentities;

import net.minecraft.item.ItemStack;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class TileEntitySpeedyHopper extends TileEntityFilteringHopper {

	public TileEntitySpeedyHopper() {
		super(0);
	}

	@Override
	public String getLine() {
		return "";
	}

	@Override
	public int getMaxCoolDown() {
		return 1;
	}

	@Override
	public boolean isFilter() {
		return false;
	}

	@Override
	protected boolean shouldTransfer(ItemStack stack) {
		return true;
	}
}