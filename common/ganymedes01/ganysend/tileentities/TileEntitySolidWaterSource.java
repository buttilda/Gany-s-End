package ganymedes01.ganysend.tileentities;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class TileEntitySolidWaterSource extends TileEntity implements IFluidHandler {

	private FluidTank tank = new FluidTank(FluidContainerRegistry.BUCKET_VOLUME);

	public TileEntitySolidWaterSource() {
		tank.setFluid(new FluidStack(FluidRegistry.WATER, FluidContainerRegistry.BUCKET_VOLUME));
	}

	@Override
	public void updateEntity() {
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			TileEntity tile = worldObj.getBlockTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
			if (tile != null && tile instanceof IFluidHandler)
				fillTank((IFluidHandler) tile, dir);
		}
	}

	private void fillTank(IFluidHandler tank, ForgeDirection from) {
		if (tank.canFill(from.getOpposite(), FluidRegistry.WATER))
			tank.fill(from.getOpposite(), new FluidStack(FluidRegistry.WATER, 40), true);
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		if (resource != null && !resource.isFluidEqual(tank.getFluid()))
			return null;
		return drain(from, resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return tank.drain(maxDrain, false);
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
		return new FluidTankInfo[] { tank.getInfo() };
	}
}