package ganymedes01.ganysend.tileentities;

import ganymedes01.ganysend.network.IPacketHandlingTile;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import com.mojang.authlib.GameProfile;

import cpw.mods.fml.common.network.ByteBufUtils;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class TileEntityInventoryBinder extends TileEntity implements IInventory, IPacketHandlingTile {

	private String playerName;
	private GameProfile profile;

	public TileEntityInventoryBinder() {
		this(null);
	}

	public TileEntityInventoryBinder(String name) {
		playerName = name;
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setString("playerName", playerName);

		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		NBTTagCompound nbt = packet.func_148857_g();
		if (packet.func_148853_f() == 0)
			playerName = nbt.getString("playerName");
	}

	@Override
	public void readPacketData(ByteBuf buffer) {
		playerName = ByteBufUtils.readUTF8String(buffer);
	}

	public GameProfile getProfile() {
		if (profile == null)
			profile = new GameProfile(null, playerName);
		return profile;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String name) {
		playerName = name;
	}

	private InventoryPlayer getPlayerInventory() {
		return worldObj.getPlayerEntityByName(playerName) != null ? worldObj.getPlayerEntityByName(playerName).inventory : null;
	}

	private boolean isConnected() {
		return worldObj.getPlayerEntityByName(playerName) != null;
	}

	@Override
	public int getSizeInventory() {
		if (isConnected())
			return getPlayerInventory().getSizeInventory() - 4;
		else
			return 0;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		if (isConnected())
			return getPlayerInventory().getStackInSlot(slot);
		else
			return null;
	}

	@Override
	public ItemStack decrStackSize(int slot, int size) {
		if (isConnected())
			return getPlayerInventory().decrStackSize(slot, size);
		else
			return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if (isConnected())
			return getPlayerInventory().getStackInSlotOnClosing(slot);
		else
			return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		if (isConnected())
			getPlayerInventory().setInventorySlotContents(slot, stack);
	}

	@Override
	public String getInventoryName() {
		if (isConnected())
			return getPlayerInventory().getInventoryName();
		else
			return null;
	}

	@Override
	public boolean hasCustomInventoryName() {
		if (isConnected())
			return getPlayerInventory().hasCustomInventoryName();
		else
			return false;
	}

	@Override
	public int getInventoryStackLimit() {
		if (isConnected())
			return getPlayerInventory().getInventoryStackLimit();
		else
			return 0;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		if (isConnected())
			return getPlayerInventory().isUseableByPlayer(player);
		else
			return false;
	}

	@Override
	public void openInventory() {
		if (isConnected())
			getPlayerInventory().openInventory();
	}

	@Override
	public void closeInventory() {
		if (isConnected())
			getPlayerInventory().closeInventory();
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if (isConnected())
			return getPlayerInventory().isItemValidForSlot(slot, stack);
		else
			return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		super.readFromNBT(data);
		playerName = data.getString("playerName");
	}

	@Override
	public void writeToNBT(NBTTagCompound data) {
		super.writeToNBT(data);
		data.setString("playerName", playerName);
	}

	@Override
	public boolean canUpdate() {
		return false;
	}
}