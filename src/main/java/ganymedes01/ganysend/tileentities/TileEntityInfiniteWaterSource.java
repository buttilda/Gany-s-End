package ganymedes01.ganysend.tileentities;

import ganymedes01.ganysend.core.utils.Utils;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class TileEntityInfiniteWaterSource extends TileEntity implements IFluidHandler {

	private final FluidStack water = new FluidStack(FluidRegistry.WATER, FluidContainerRegistry.BUCKET_VOLUME);

	@Override
	public void updateEntity() {
		if (worldObj.isRemote)
			return;

		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			IFluidHandler tile = Utils.getTileEntity(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, IFluidHandler.class);
			if (tile != null)
				tile.fill(dir.getOpposite(), water.copy(), true);
		}
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return drain(from, resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return water;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[] { new FluidTankInfo(water, water.amount) };
	}
}