package ganymedes01.ganysend.tileentities;

import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class TileEntityAdvancedFilteringHopper extends TileEntityFilteringHopper {

	public TileEntityAdvancedFilteringHopper() {
		super(5);
	}

	@Override
	public void setBasic() {
		super.setBasic();
		MAX_COOL_DOWN = 2;
		name = Utils.getConainerName(Strings.ADVANCED_FILTERING_HOPPER_NAME);
	}

	@Override
	public void setExclusive() {
		super.setExclusive();
		MAX_COOL_DOWN = 2;
		name = Utils.getConainerName(Strings.ADVANCED_EXCLUSIVE_FILTERING_HOPPER_NAME);
	}
}