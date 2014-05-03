package ganymedes01.ganysend.tileentities;

import ganymedes01.ganysend.network.IPacketHandlingTile;
import ganymedes01.ganysend.network.PacketHandler;
import ganymedes01.ganysend.network.packet.PacketTileEntity;
import ganymedes01.ganysend.network.packet.PacketTileEntity.TileData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.ByteBufUtils;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class TileEntityInventoryBinder extends TileEntity implements IInventory, IPacketHandlingTile {

	private String playerName;

	public TileEntityInventoryBinder() {
		this(null);
	}

	public TileEntityInventoryBinder(String name) {
		playerName = name;
	}

	@Override
	public Packet getDescriptionPacket() {
		return PacketHandler.toPacket(new PacketTileEntity(this, new TileData() {

			@Override
			public void writeData(ByteBuf buffer) {
				ByteBufUtils.writeUTF8String(buffer, playerName);
			}

		}));
	}

	@Override
	public void readPacketData(ByteBuf buffer) {
		playerName = ByteBufUtils.readUTF8String(buffer);
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