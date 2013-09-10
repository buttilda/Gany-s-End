package ganymedes01.ganysend.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntitySkull;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityBlockNewSkull extends TileEntitySkull {
	private int skullType;
	private int skullRotation;
	private String extraType = "";

	@Override
	public void writeToNBT(NBTTagCompound data) {
		super.writeToNBT(data);
		data.setInteger("skullType", skullType);
		data.setInteger("skullRotation", skullRotation);
		data.setString("extraType", extraType);
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		super.readFromNBT(data);
		skullType = data.getInteger("skullType");
		skullRotation = data.getInteger("skullRotation");
		extraType = data.getString("extraType");
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound data = new NBTTagCompound();
		writeToNBT(data);
		return new Packet132TileEntityData(xCoord, yCoord, zCoord, 4, data);
	}

	@Override
	public void setSkullType(int type, String playerName) {
		skullType = type;
		extraType = playerName;
	}

	@Override
	public int getSkullType() {
		return skullType;
	}

	@Override
	public void setSkullRotation(int rotation) {
		skullRotation = rotation;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int func_82119_b() {
		return skullRotation;
	}

	@Override
	public String getExtraType() {
		return extraType;
	}
}
