package ganymedes01.ganysend.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class ByteBufHelper {

	public static void writeFluidStack(FluidStack fluid, ByteBuf buffer) {
		if (fluid == null)
			buffer.writeInt(-1);
		else {
			buffer.writeInt(fluid.getFluidID());
			buffer.writeInt(fluid.amount);
			ByteBufUtils.writeTag(buffer, fluid.tag);
		}
	}

	public static FluidStack readFluidStack(ByteBuf buffer) {
		FluidStack fluid = null;

		int fluidID = buffer.readInt();
		if (fluidID > -1)
			fluid = new FluidStack(FluidRegistry.getFluid(fluidID), buffer.readInt(), ByteBufUtils.readTag(buffer));

		return fluid;
	}
}