package ganymedes01.ganysend.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class ByteBufHelper {

	public static void writeFluidStack(FluidStack fluid, ByteBuf buffer) {
		if (fluid == null)
			buffer.writeByte(0);
		else {
			buffer.writeByte(1);
			NBTTagCompound nbt = fluid.writeToNBT(new NBTTagCompound());
			ByteBufUtils.writeTag(buffer, nbt);
		}
	}

	public static FluidStack readFluidStack(ByteBuf buffer) {
		FluidStack fluid = null;

		byte saved = buffer.readByte();
		if (saved > 0)
			fluid = FluidStack.loadFluidStackFromNBT(ByteBufUtils.readTag(buffer));

		return fluid;
	}
}