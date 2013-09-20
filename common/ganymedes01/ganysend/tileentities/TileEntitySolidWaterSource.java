package ganymedes01.ganysend.tileentities;

import java.util.ArrayList;

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
	private final ArrayList<ForgeDirection> directions;

	public TileEntitySolidWaterSource() {
		tank.setFluid(new FluidStack(FluidRegistry.WATER, FluidContainerRegistry.BUCKET_VOLUME));
		directions = new ArrayList<ForgeDirection>();
		directions.add(ForgeDirection.UP);
		directions.add(ForgeDirection.DOWN);
		directions.add(ForgeDirection.EAST);
		directions.add(ForgeDirection.WEST);
		directions.add(ForgeDirection.SOUTH);
		directions.add(ForgeDirection.NORTH);
	}

	@Override
	public void updateEntity() {
		ArrayList<TileEntity> list = getSurroundingTiles();
		for (int i = 0; i < 6; i++) {
			TileEntity tile = list.get(i);
			if (tile != null && tile instanceof IFluidHandler)
				fillTank((IFluidHandler) tile, directions.get(i));
			else
				continue;
		}
	}

	private void fillTank(IFluidHandler tank, ForgeDirection from) {
		if (tank.canFill(from.getOpposite(), FluidRegistry.WATER))
			tank.fill(from.getOpposite(), new FluidStack(FluidRegistry.WATER, 40), true);
	}

	private ArrayList<TileEntity> getSurroundingTiles() {
		ArrayList<TileEntity> list = new ArrayList<TileEntity>();
		list.add(worldObj.getBlockTileEntity(xCoord, yCoord + 1, zCoord));
		list.add(worldObj.getBlockTileEntity(xCoord, yCoord - 1, zCoord));
		list.add(worldObj.getBlockTileEntity(xCoord + 1, yCoord, zCoord));
		list.add(worldObj.getBlockTileEntity(xCoord - 1, yCoord, zCoord));
		list.add(worldObj.getBlockTileEntity(xCoord, yCoord, zCoord + 1));
		list.add(worldObj.getBlockTileEntity(xCoord, yCoord, zCoord - 1));
		return list;
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
