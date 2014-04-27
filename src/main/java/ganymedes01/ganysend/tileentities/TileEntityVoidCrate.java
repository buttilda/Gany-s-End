package ganymedes01.ganysend.tileentities;

import ganymedes01.ganysend.lib.Strings;

public class TileEntityVoidCrate extends GanysInventory {

	public TileEntityVoidCrate() {
		super(65, Strings.VOID_CRATE_NAME);
	}

	@Override
	public void updateEntity() {
		if (worldObj.isRemote)
			return;

		if (inventory[64] != null)
			inventory[64] = null;
	}
}