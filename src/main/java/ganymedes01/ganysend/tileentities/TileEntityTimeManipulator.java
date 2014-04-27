package ganymedes01.ganysend.tileentities;

import ganymedes01.ganysend.lib.IPacketHandlingTile;
import ganymedes01.ganysend.network.PacketHandler;
import ganymedes01.ganysend.network.packet.CustomPacket;
import ganymedes01.ganysend.network.packet.PacketTileEntity;
import ganymedes01.ganysend.network.packet.PacketTileEntity.TileData;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class TileEntityTimeManipulator extends TileEntity implements IPacketHandlingTile {

	public boolean revertTime, advanceTime;

	@Override
	public void updateEntity() {
		if (worldObj.provider.dimensionId != 0)
			return;
		if (revertTime && !advanceTime)
			if (worldObj.provider.getWorldTime() >= 50)
				worldObj.provider.setWorldTime(worldObj.provider.getWorldTime() - 50);
			else {
				worldObj.provider.setWorldTime(0);
				revertTime = false;
			}

		if (advanceTime && !revertTime)
			if (worldObj.provider.getWorldTime() <= 12500 - 50)
				worldObj.provider.setWorldTime(worldObj.provider.getWorldTime() + 50);
			else {
				worldObj.provider.setWorldTime(12500);
				advanceTime = false;
			}
	}

	@Override
	public Packet getDescriptionPacket() {
		return PacketHandler.toPacket(getPacket());
	}

	public CustomPacket getPacket() {
		return new PacketTileEntity(this, new TileData() {

			@Override
			public void writeData(ByteBuf buffer) {
				buffer.writeBoolean(revertTime);
				buffer.writeBoolean(advanceTime);
			}

		});
	}

	@Override
	public void readPacketData(ByteBuf buffer) {
		revertTime = buffer.readBoolean();
		advanceTime = buffer.readBoolean();
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		super.readFromNBT(data);
		revertTime = data.getBoolean("revertTime");
		advanceTime = data.getBoolean("advanceTime");
	}

	@Override
	public void writeToNBT(NBTTagCompound data) {
		super.writeToNBT(data);
		data.setBoolean("revertTime", revertTime);
		data.setBoolean("advanceTime", advanceTime);
	}
}