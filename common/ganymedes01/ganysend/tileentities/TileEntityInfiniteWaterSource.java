package ganymedes01.ganysend.tileentities;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
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

	private FluidStack water = new FluidStack(FluidRegistry.WATER, FluidContainerRegistry.BUCKET_VOLUME);
	private boolean skip = false;

	@Override
	public void updateEntity() {
		if (worldObj.isRemote)
			return;

		// Skips every-other tick
		if (!skip) {
			ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[worldObj.rand.nextInt(ForgeDirection.VALID_DIRECTIONS.length)];
			if (worldObj.blockHasTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ)) {
				TileEntity neighbourTile = worldObj.getBlockTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
				if (neighbourTile instanceof IFluidHandler)
					fillTank((IFluidHandler) neighbourTile, dir);
			}
			skip = true;
			return;
		}

		if (skip)
			skip = false;
	}

	private void fillTank(IFluidHandler tank, ForgeDirection from) {
		if (tank != null)
			if (tank.canFill(from.getOpposite(), FluidRegistry.WATER))
				tank.fill(from.getOpposite(), water.copy(), true);
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