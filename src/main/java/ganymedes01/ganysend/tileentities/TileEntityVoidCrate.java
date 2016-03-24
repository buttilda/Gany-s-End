package ganymedes01.ganysend.tileentities;

import ganymedes01.ganysend.lib.Strings;
import net.minecraft.util.ITickable;

public class TileEntityVoidCrate extends GanysInventory implements ITickable {

	public TileEntityVoidCrate() {
		super(65, Strings.VOID_CRATE_NAME);
	}

	@Override
	public void update() {
		if (worldObj.isRemote)
			return;

		if (inventory[64] != null)
			inventory[64] = null;
	}
}