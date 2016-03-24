package ganymedes01.ganysend.tileentities;

import com.google.common.collect.Iterables;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StringUtils;
import net.minecraftforge.common.util.Constants;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class TileEntityInventoryBinder extends TileEntity implements IInventory {

	protected GameProfile profile;

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		NBTUtil.func_152460_a(nbt, profile);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		NBTTagCompound nbt = packet.func_148857_g();
		if (packet.func_148853_f() == 0)
			profile = NBTUtil.func_152459_a(nbt);
	}

	public GameProfile getProfile() {
		return profile;
	}

	public void setPlayerProfile(GameProfile profile) {
		this.profile = profile;
		markDirty();
	}

	public IInventory getPlayerInventory() {
		if (profile == null || StringUtils.isNullOrEmpty(profile.getName()))
			return null;
		EntityPlayer player = worldObj.getPlayerEntityByName(profile.getName());
		return player != null ? player.inventory : null;
	}

	@Override
	public int getSizeInventory() {
		IInventory inventory = getPlayerInventory();
		return inventory != null ? getPlayerInventory().getSizeInventory() - 4 : 0;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		IInventory inventory = getPlayerInventory();
		return inventory != null ? inventory.getStackInSlot(slot) : null;
	}

	@Override
	public ItemStack decrStackSize(int slot, int size) {
		IInventory inventory = getPlayerInventory();
		return inventory != null ? inventory.decrStackSize(slot, size) : null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		IInventory inventory = getPlayerInventory();
		return inventory != null ? inventory.getStackInSlotOnClosing(slot) : null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		IInventory inventory = getPlayerInventory();
		if (inventory != null)
			inventory.setInventorySlotContents(slot, stack);
	}

	@Override
	public String getInventoryName() {
		IInventory inventory = getPlayerInventory();
		return inventory != null ? inventory.getInventoryName() : null;
	}

	@Override
	public boolean hasCustomInventoryName() {
		IInventory inventory = getPlayerInventory();
		return inventory != null && inventory.hasCustomInventoryName();
	}

	@Override
	public int getInventoryStackLimit() {
		IInventory inventory = getPlayerInventory();
		return inventory != null ? inventory.getInventoryStackLimit() : 0;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		IInventory inventory = getPlayerInventory();
		return inventory != null && inventory.isUseableByPlayer(player);
	}

	@Override
	public void openInventory() {
		IInventory inventory = getPlayerInventory();
		if (inventory != null)
			inventory.openInventory();
	}

	@Override
	public void closeInventory() {
		IInventory inventory = getPlayerInventory();
		if (inventory != null)
			inventory.closeInventory();
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		IInventory inventory = getPlayerInventory();
		return inventory != null && inventory.isItemValidForSlot(slot, stack);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		if (nbt.hasKey("playerName", Constants.NBT.TAG_STRING))
			profile = new GameProfile(null, nbt.getString("playerName"));
		else
			profile = NBTUtil.func_152459_a(nbt);
		updateProfile();
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		if (profile != null)
			NBTUtil.func_152460_a(nbt, profile);
	}

	protected void updateProfile() {
		if (profile != null && !StringUtils.isNullOrEmpty(profile.getName()))
			if (!profile.isComplete() || !profile.getProperties().containsKey("textures")) {
				GameProfile gameprofile = MinecraftServer.getServer().func_152358_ax().func_152655_a(profile.getName());
				if (gameprofile != null) {
					Property property = (Property) Iterables.getFirst(gameprofile.getProperties().get("textures"), (Object) null);
					if (property == null)
						gameprofile = MinecraftServer.getServer().func_147130_as().fillProfileProperties(gameprofile, true);
					profile = gameprofile;
				}
			}
	}
}