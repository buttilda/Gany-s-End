package ganymedes01.ganysend.tileentities;

import java.util.UUID;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.StringUtils;

import com.google.common.collect.Iterables;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

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
	private GameProfile extraType = null;

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("skullType", skullType);
		nbt.setInteger("skullRotation", skullRotation);

		if (extraType != null) {
			NBTTagCompound nbt2 = new NBTTagCompound();
			NBTUtil.func_152460_a(nbt2, extraType);
			nbt.setTag("Owner", nbt2);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		super.readFromNBT(data);
		skullType = data.getInteger("skullType");
		skullRotation = data.getInteger("skullRotation");

		if (data.hasKey("Owner", 10))
			extraType = NBTUtil.func_152459_a(data.getCompoundTag("Owner"));
		else if (data.hasKey("ExtraType", 8) && !StringUtils.isNullOrEmpty(data.getString("ExtraType"))) {
			extraType = new GameProfile((UUID) null, data.getString("ExtraType"));
			loadTexture();
		}
	}

	private void loadTexture() {
		if (extraType != null && !StringUtils.isNullOrEmpty(extraType.getName()))
			if (!extraType.isComplete() || !extraType.getProperties().containsKey("textures")) {
				GameProfile gameprofile = MinecraftServer.getServer().func_152358_ax().func_152655_a(extraType.getName());

				if (gameprofile != null) {
					Property property = (Property) Iterables.getFirst(gameprofile.getProperties().get("textures"), (Object) null);

					if (property == null)
						gameprofile = MinecraftServer.getServer().func_147130_as().fillProfileProperties(gameprofile, true);

					extraType = gameprofile;
					markDirty();
				}
			}
	}

	public void setType(int type, GameProfile extra) {
		skullType = type;
		extraType = extra;
		if (extraType != null)
			loadTexture();
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
	public GameProfile func_152108_a() {
		return extraType;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord - 1, zCoord - 1, xCoord + 2, yCoord + 2, zCoord + 2);
	}
}