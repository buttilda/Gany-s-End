package ganymedes01.ganysend.tileentities;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.network.ByteBufHelper;
import ganymedes01.ganysend.network.IPacketHandlingTile;
import ganymedes01.ganysend.network.PacketHandler;
import ganymedes01.ganysend.network.packet.PacketTileEntity;
import ganymedes01.ganysend.network.packet.PacketTileEntity.TileData;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class TileEntityInfiniteWaterSource extends TileEntity implements IFluidHandler, IPacketHandlingTile {

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
	public void updateEntity() {
		if (worldObj.isRemote)
			return;
		if (fluid == null)
			return;

		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			TileEntity tile = Utils.getTileEntity(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, TileEntity.class);
			if (tile instanceof IFluidHandler)
				((IFluidHandler) tile).fill(dir.getOpposite(), fluid.copy(), true);
			else if (GanysEnd.isBotaniaLoaded && fluid.getFluid() == FluidRegistry.WATER)
				try {
					Class<?> IPetalApothecary = Class.forName("vazkii.botania.api.item.IPetalApothecary");
					if (IPetalApothecary.isInstance(tile))
						if (!(Boolean) IPetalApothecary.getMethod("hasWater").invoke(tile))
							IPetalApothecary.getMethod("setWater", boolean.class).invoke(tile, true);
				} catch (Exception e) {
					GanysEnd.isBotaniaLoaded = false;
				}
		}
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return resource != null && resource.isFluidEqual(fluid) ? drain(from, resource.amount, doDrain) : null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		FluidStack f = getFluid();
		if (f != null)
			f.amount = Math.min(f.amount, maxDrain);
		return f;
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
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);

		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		NBTTagCompound nbt = packet.func_148857_g();
		if (packet.func_148853_f() == 0)
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