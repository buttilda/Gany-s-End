package ganymedes01.ganysend.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.AxisAlignedBB;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

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
	public void func_145905_a(int type, String playerName) {
		skullType = type;
		extraType = playerName;
	}

	@Override
	public int func_145904_a() {
		return skullType;
	}

	@Override
	public void func_145903_a(int rotation) {
		skullRotation = rotation;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int func_145906_b() {
		return skullRotation;
	}

	@Override
	public String func_145907_c() {
		return extraType;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord - 1, zCoord - 1, xCoord + 2, yCoord + 2, zCoord + 2);
	}
}