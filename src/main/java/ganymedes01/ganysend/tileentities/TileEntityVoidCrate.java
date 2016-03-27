package ganymedes01.ganysend.tileentities;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityVoidCrate extends TileEntity implements ITickable {

	private final IItemHandlerModifiable itemHandler = new ItemStackHandler(65);

	@Override
	public void update() {
		if (worldObj.isRemote)
			return;

		itemHandler.setStackInSlot(64, null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return (T) (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? itemHandler : null);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
	}
}