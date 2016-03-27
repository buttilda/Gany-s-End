package ganymedes01.ganysend.tileentities;

import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.network.ByteBufHelper;
import ganymedes01.ganysend.network.IPacketHandlingTile;
import ganymedes01.ganysend.network.PacketHandler;
import ganymedes01.ganysend.network.packet.PacketTileEntity;
import ganymedes01.ganysend.network.packet.PacketTileEntity.TileData;
import io.netty.buffer.ByteBuf;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class TileEntityInfiniteWaterSource extends TileEntity implements IFluidHandler, IPacketHandlingTile, ITickable {

	private FluidStack fluid = new FluidStack(FluidRegistry.WATER, FluidContainerRegistry.BUCKET_VOLUME);

	public void setFluid(FluidStack fluid) {
		if (fluid == null)
			this.fluid = null;
		else {
			this.fluid = fluid.copy();
			this.fluid.amount = FluidContainerRegistry.BUCKET_VOLUME;
		}
		if (!worldObj.isRemote)
			sendPacket();
	}

	public FluidStack getFluid() {
		if (fluid == null)
			return null;
		return fluid.copy();
	}

	@SideOnly(Side.CLIENT)
	public FluidStack getFluidForRendering() {
		return fluid;
	}

	@Override
	public void update() {
		if (worldObj.isRemote)
			return;
		if (fluid == null)
			return;

		for (EnumFacing side : EnumFacing.VALUES) {
			BlockPos sidePos = pos.offset(side);
			TileEntity tile = Utils.getTileEntity(worldObj, sidePos, TileEntity.class);
			if (tile instanceof IFluidHandler)
				((IFluidHandler) tile).fill(side.getOpposite(), fluid.copy(), true);
			else if (worldObj.getBlockState(sidePos).getBlock() == Blocks.cauldron)
				Blocks.cauldron.setWaterLevel(worldObj, pos, worldObj.getBlockState(pos), 3);
		}
	}

	@Override
	public int fill(EnumFacing from, FluidStack resource, boolean doFill) {
		return 0;
	}

	@Override
	public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain) {
		return resource != null && resource.isFluidEqual(fluid) ? drain(from, resource.amount, doDrain) : null;
	}

	@Override
	public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain) {
		FluidStack f = getFluid();
		if (f != null)
			f.amount = Math.min(f.amount, maxDrain);
		return f;
	}

	@Override
	public boolean canFill(EnumFacing from, Fluid fluid) {
		return false;
	}

	@Override
	public boolean canDrain(EnumFacing from, Fluid fluid) {
		return true;
	}

	@Override
	public FluidTankInfo[] getTankInfo(EnumFacing from) {
		return new FluidTankInfo[] { new FluidTankInfo(fluid, FluidContainerRegistry.BUCKET_VOLUME) };
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		if (nbt.getBoolean("hasFluid"))
			fluid = FluidStack.loadFluidStackFromNBT(nbt);
		else
			fluid = null;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("hasFluid", fluid != null);
		if (fluid != null)
			fluid.writeToNBT(nbt);
	}

	@Override
	public Packet<?> getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);

		return new S35PacketUpdateTileEntity(pos, 0, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		NBTTagCompound nbt = packet.getNbtCompound();
		if (packet.getTileEntityType() == 0)
			readFromNBT(nbt);
	}

	@Override
	public void readPacketData(ByteBuf buffer) {
		fluid = ByteBufHelper.readFluidStack(buffer);
	}

	public void sendPacket() {
		PacketHandler.sendToAll(new PacketTileEntity(this, new TileData() {

			@Override
			public void writeData(ByteBuf buffer) {
				ByteBufHelper.writeFluidStack(fluid, buffer);
			}
		}));
	}
}