package ganymedes01.ganysend.tileentities;

import ganymedes01.ganysend.network.PacketTypeHandler;
import ganymedes01.ganysend.network.packet.PacketPlayerInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class TileEntityPlayerInventory extends TileEntity implements IInventory {

	private String playerName;

	public TileEntityPlayerInventory() {
		this(null);
	}

	public TileEntityPlayerInventory(String name) {
		playerName = name;
	}

	@Override
	public Packet getDescriptionPacket() {
		return PacketTypeHandler.populatePacket(new PacketPlayerInventory(xCoord, yCoord, zCoord, playerName));
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
	public String getInvName() {
		if (isConnected())
			return getPlayerInventory().getInvName();
		else
			return null;
	}

	@Override
	public boolean isInvNameLocalized() {
		if (isConnected())
			return getPlayerInventory().isInvNameLocalized();
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
	public void openChest() {
		if (isConnected())
			getPlayerInventory().openChest();
	}

	@Override
	public void closeChest() {
		if (isConnected())
			getPlayerInventory().closeChest();
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
}
