package ganymedes01.ganysend.tileentities;

import java.util.Iterator;
import java.util.List;

import ganymedes01.ganysend.core.utils.InventoryUtils;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Reference;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class TileEntityFilteringHopper extends TileEntity implements ITickable {

	protected final IItemHandler itemHandler = new ItemStackHandler(5);
	protected final IItemHandler filters;

	protected int transferCooldown = -1;
	protected int MAX_COOL_DOWN;
	protected boolean EXCLUSIVE;
	protected String line;
	protected String name;

	public TileEntityFilteringHopper(int filterSize) {
		filters = new ItemStackHandler(filterSize);
	}

	public IItemHandler getFilters() {
		return filters;
	}

	public void setBasic() {
		MAX_COOL_DOWN = 8;
		EXCLUSIVE = false;
		line = StatCollector.translateToLocal("string." + Reference.MOD_ID + ".pullonly");
		name = Utils.getConainerName(Strings.BASIC_FILTERING_HOPPER_NAME);
	}

	public void setExclusive() {
		MAX_COOL_DOWN = 8;
		EXCLUSIVE = true;
		line = StatCollector.translateToLocal("string." + Reference.MOD_ID + ".pullallbut");
		name = Utils.getConainerName(Strings.EXCLUSIVE_FILTERING_HOPPER_NAME);
	}

	public void setSpeedyOnly() {
		MAX_COOL_DOWN = 1;
		EXCLUSIVE = false;
		line = "";
		name = Utils.getConainerName(Strings.SPEEDY_HOPPER_NAME);
	}

	public void setSpeedy() {
		MAX_COOL_DOWN = 2;
		if (EXCLUSIVE)
			name = Utils.getConainerName(Strings.SPEEDY_EXCLUSIVE_FILTERING_HOPPER_NAME);
		else
			name = Utils.getConainerName(Strings.SPEEDY_BASIC_FILTERING_HOPPER_NAME);
	}

	public String getLine() {
		return line;
	}

	public boolean isExclusive() {
		return EXCLUSIVE;
	}

	public boolean isFilter() {
		return true;
	}

	public int getMaxCoolDown() {
		return MAX_COOL_DOWN;
	}

	protected boolean shouldTransfer(ItemStack stack) {
		if (!isFilter())
			return true;

		for (int i = 0; i < filters.getSlots(); i++) {
			ItemStack filterStack = filters.getStackInSlot(i);
			if (filterStack == null)
				continue;
			if (InventoryUtils.areStacksTheSame(stack, filterStack, false))
				return !isExclusive();
		}
		return isExclusive();
	}

	@Override
	public void update() {
		if (worldObj.isRemote)
			return;

		transferCooldown--;
		if (transferCooldown <= 0) {
			boolean suckedItems = suckEntitiesAbove() || suckItemFromInventory();
			boolean indertedItems = insertItemToInventory();
			if (suckedItems || indertedItems)
				markDirty();
			transferCooldown = MAX_COOL_DOWN;
		}
	}

	protected boolean insertItemToInventory() {
		return transferStack(itemHandler, getInventoryToInsert());
	}

	protected boolean suckItemFromInventory() {
		return transferStack(getInventoryToExtract(), itemHandler);
	}

	protected boolean suckEntitiesAbove() {
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		List<EntityItem> list = worldObj.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(x - 0.5, y - 0.5, z - 0.5, x + 0.5, y + 0.5, z + 0.5), EntitySelectors.selectAnything);
		if (!list.isEmpty()) {
			Iterator<EntityItem> iterator = list.iterator();
			while (iterator.hasNext()) {
				EntityItem entity = iterator.next();
				if (entity.worldObj == worldObj) {
					ItemStack stack = entity.getEntityItem();
					if (stack != null && shouldTransfer(stack))
						for (int i = 0; i < itemHandler.getSlots(); i++) {
							stack = itemHandler.insertItem(i, stack, false);
							if (stack == null || stack.stackSize <= 0) {
								entity.setDead();
								return true;
							}
						}
				}
			}
		}
		return false;
	}

	protected final IItemHandler getInventoryToExtract() {
		TileEntity tile = worldObj.getTileEntity(pos.up());

		if (tile != null && tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN))
			return tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
		else {
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			List<Entity> list = worldObj.getEntitiesInAABBexcluding(null, new AxisAlignedBB(x - 0.5, y + 0.5, z - 0.5, x + 0.5, y + 1.5, z + 0.5), EntitySelectors.selectInventories);
			if (list != null && list.size() > 0) {
				Entity entity = list.get(worldObj.rand.nextInt(list.size()));
				if (entity.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN))
					return entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
			}
		}
		return null;
	}

	protected IItemHandler getInventoryToInsert() {
		EnumFacing side = getSideToInsert();
		BlockPos sidePos = pos.offset(side);
		TileEntity tile = worldObj.getTileEntity(sidePos);

		if (tile != null && tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side.getOpposite()))
			return tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side.getOpposite());
		else {
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			List<Entity> list = worldObj.getEntitiesInAABBexcluding(null, new AxisAlignedBB(x - 0.5, y - 1.5, z - 0.5, x + 0.5, y - 0.5, z + 0.5), EntitySelectors.selectInventories);
			if (list != null && list.size() > 0) {
				Entity entity = list.get(worldObj.rand.nextInt(list.size()));
				if (entity.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN))
					return entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
			}
		}
		return null;
	}

	public EnumFacing getSideToInsert() {
		return EnumFacing.VALUES[getBlockMetadata()];
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		super.readFromNBT(data);

		transferCooldown = data.getInteger("TransferCooldown");
		MAX_COOL_DOWN = data.getInteger("MAX_COOL_DOWN");
		EXCLUSIVE = data.getBoolean("OPPOSITE");
	}

	@Override
	public void writeToNBT(NBTTagCompound data) {
		super.writeToNBT(data);

		data.setInteger("TransferCooldown", transferCooldown);
		data.setInteger("MAX_COOL_DOWN", MAX_COOL_DOWN);
		data.setBoolean("OPPOSITE", EXCLUSIVE);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return (T) (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? itemHandler : null);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
	}

	protected boolean transferStack(IItemHandler origin, IItemHandler destination) {
		if (origin == null || destination == null)
			return false;

		for (int i = 0; i < origin.getSlots(); i++) {
			ItemStack stack = origin.extractItem(i, 1, true);
			if (stack == null || !shouldTransfer(stack))
				continue;

			for (int j = 0; j < destination.getSlots(); j++)
				if (destination.insertItem(j, stack, true) == null) {
					destination.insertItem(j, origin.extractItem(i, 1, false), false);
					return true;
				}
		}

		return false;
	}
}