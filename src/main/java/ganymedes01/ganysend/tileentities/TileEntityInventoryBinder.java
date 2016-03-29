package ganymedes01.ganysend.tileentities;

import com.mojang.authlib.GameProfile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.IChatComponent;
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
	public Packet<?> getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		if (profile != null)
			NBTUtil.writeGameProfile(nbt, profile);
		return new S35PacketUpdateTileEntity(pos, 0, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		NBTTagCompound nbt = packet.getNbtCompound();
		if (packet.getTileEntityType() == 0)
			profile = NBTUtil.readGameProfileFromNBT(nbt);
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
	public ItemStack removeStackFromSlot(int slot) {
		IInventory inventory = getPlayerInventory();
		return inventory != null ? inventory.removeStackFromSlot(slot) : null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		IInventory inventory = getPlayerInventory();
		if (inventory != null)
			inventory.setInventorySlotContents(slot, stack);
	}

	@Override
	public String getName() {
		IInventory inventory = getPlayerInventory();
		return inventory != null ? inventory.getName() : null;
	}

	@Override
	public boolean hasCustomName() {
		IInventory inventory = getPlayerInventory();
		return inventory != null && inventory.hasCustomName();
	}

	@Override
	public IChatComponent getDisplayName() {
		IInventory inventory = getPlayerInventory();
		return inventory != null ? inventory.getDisplayName() : null;
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
	public void openInventory(EntityPlayer player) {
		IInventory inventory = getPlayerInventory();
		if (inventory != null)
			inventory.openInventory(player);
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		IInventory inventory = getPlayerInventory();
		if (inventory != null)
			inventory.closeInventory(player);
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
			profile = NBTUtil.readGameProfileFromNBT(nbt);
		updateProfile();
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		if (profile != null)
			NBTUtil.writeGameProfile(nbt, profile);
	}

	protected void updateProfile() {
		TileEntitySkull.updateGameprofile(getProfile());
	}

	@Override
	public int getField(int id) {
		IInventory inventory = getPlayerInventory();
		return inventory != null ? inventory.getField(id) : 0;
	}

	@Override
	public void setField(int id, int value) {
		IInventory inventory = getPlayerInventory();
		if (inventory != null)
			inventory.setField(id, value);
	}

	@Override
	public int getFieldCount() {
		IInventory inventory = getPlayerInventory();
		return inventory != null ? inventory.getFieldCount() : 0;
	}

	@Override
	public void clear() {
		IInventory inventory = getPlayerInventory();
		if (inventory != null)
			inventory.clear();
	}
}