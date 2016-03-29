package ganymedes01.ganysend.entities;

import ganymedes01.ganysend.ModBlocks;
import ganymedes01.ganysend.ModItems;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class EntityAnchoredEnderChestMinecart extends EntityMinecartChest {

	public EntityAnchoredEnderChestMinecart(World world) {
		super(world);
	}

	public EntityAnchoredEnderChestMinecart(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	public boolean isConnected() {
		String playerName = getPlayerName();
		return StringUtils.isNullOrEmpty(playerName) ? false : worldObj.getPlayerEntityByName(playerName) != null;
	}

	private IInventory getPlayerInventory() {
		String playerName = getPlayerName();
		EntityPlayer player = playerName != null ? worldObj.getPlayerEntityByName(playerName) : null;
		return player != null ? player.getInventoryEnderChest() : null;
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(23, "");
	}

	private String getPlayerName() {
		return dataWatcher.getWatchableObjectString(23);
	}

	public void setPlayerName(String name) {
		dataWatcher.updateObject(23, name);
	}

	@Override
	public ItemStack getCartItem() {
		return new ItemStack(ModItems.anchoredEnderChestMinecart);
	}

	@Override
	public Block func_145817_o() {
		return ModBlocks.anchored_ender_chest;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		IInventory inventory = getPlayerInventory();
		return inventory != null ? inventory.getStackInSlot(slot) : null;
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		IInventory inventory = getPlayerInventory();
		return inventory != null ? inventory.decrStackSize(slot, amount) : null;
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
	public void markDirty() {
		IInventory inventory = getPlayerInventory();
		if (inventory != null)
			inventory.markDirty();
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
		return inventory != null ? inventory.isItemValidForSlot(slot, stack) : false;
	}

	@Override
	public String getInventoryName() {
		IInventory inventory = getPlayerInventory();
		return inventory != null ? inventory.getInventoryName() : null;
	}

	@Override
	public int getInventoryStackLimit() {
		IInventory inventory = getPlayerInventory();
		return inventory != null ? inventory.getInventoryStackLimit() : 0;
	}

	@Override
	public int getSizeInventory() {
		IInventory inventory = getPlayerInventory();
		return inventory != null ? inventory.getSizeInventory() : 0;
	}

	@Override
	public void setDead() {
		isDead = true;
	}

	@Override
	public void killMinecart(DamageSource src) {
		setDead();
		entityDropItem(new ItemStack(Items.minecart), 0.0F);
		func_145778_a(Item.getItemFromBlock(func_145817_o()), 1, 0.0F);
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setString("PlayerName", getPlayerName());
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		setPlayerName(nbt.getString("PlayerName"));
	}

	@Override
	public boolean interactFirst(EntityPlayer player) {
		return isConnected() && super.interactFirst(player);
	}
}