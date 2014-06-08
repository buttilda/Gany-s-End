package ganymedes01.ganysend.tileentities;

import ganymedes01.ganysend.network.IPacketHandlingTile;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
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
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setBoolean("revertTime", revertTime);
		nbt.setBoolean("advanceTime", advanceTime);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		NBTTagCompound nbt = pkt.func_148857_g();
		if (pkt.func_148853_f() == 0) {
			revertTime = nbt.getBoolean("revertTime");
			advanceTime = nbt.getBoolean("advanceTime");
		}
	}

	@Override
	public boolean receiveClientEvent(int eventId, int eventData) {
		switch (eventId) {
			case 0:
				revertTime = eventData == 1;
				return true;
			case 1:
				advanceTime = eventData == 1;
				return true;
			default:
				return false;
		}
	}

	public void sendUpdates() {
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, getBlockType(), 0, revertTime ? 1 : 0);
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, getBlockType(), 1, advanceTime ? 1 : 0);
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