package ganymedes01.ganysend.core.utils;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class InventoryUtils {

	public static void addToPlayerInventory(EntityPlayer player, ItemStack stack, double x, double y, double z) {
		if (!player.worldObj.isRemote && stack != null) {
			EntityItem entity = new EntityItem(player.worldObj, x + 0.5, y, z + 0.5, stack);
			entity.motionX = 0;
			entity.motionY = 0;
			entity.motionZ = 0;
			entity.setDefaultPickupDelay();
			player.worldObj.spawnEntityInWorld(entity);

			entity.onCollideWithPlayer(player);
		}
	}

	public static void dropStack(World world, BlockPos pos, ItemStack stack) {
		if (!world.isRemote && stack != null) {
			float f = 0.7F;
			double d0 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
			double d1 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
			double d2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
			EntityItem entityItem = new EntityItem(world, pos.getX() + d0, pos.getY() + d1, pos.getZ() + d2, stack);
			entityItem.setDefaultPickupDelay();
			world.spawnEntityInWorld(entityItem);
		}
	}

	public static void dropStackNoRandom(World world, int x, int y, int z, ItemStack stack) {
		if (!world.isRemote && stack != null) {
			EntityItem entityItem = new EntityItem(world, x + 0.5, y, z + 0.5, stack);
			entityItem.motionX = 0;
			entityItem.motionY = 0;
			entityItem.motionZ = 0;
			entityItem.setDefaultPickupDelay();
			world.spawnEntityInWorld(entityItem);
		}
	}

	public static boolean areStacksSameOre(ItemStack stack1, ItemStack stack2) {
		if (stack1 == null || stack2 == null)
			return false;
		if (InventoryUtils.areStacksTheSame(stack1, stack2, false))
			return true;

		List<String> ores1 = getOreNames(stack1);
		List<String> ores2 = getOreNames(stack2);

		if (ores1.isEmpty() || ores2.isEmpty())
			return false;
		for (String ore2 : ores2)
			if (ores1.contains(ore2))
				return isIntercheageableOreName(ore2);
		return false;
	}

	private static final String[] orePrefixes = new String[] { "dust", "ingot", "ore", "block", "gem", "nugget", "shard", "plate", "gear", "stickWood" };

	private static boolean isIntercheageableOreName(String name) {
		for (String prefix : orePrefixes)
			if (name.startsWith(prefix))
				return true;
		return false;
	}

	public static List<String> getOreNames(ItemStack stack) {
		List<String> list = new ArrayList<String>();
		for (int id : OreDictionary.getOreIDs(stack))
			list.add(OreDictionary.getOreName(id));

		return list;
	}

	public static boolean isItemOre(ItemStack stack, String ore) {
		int oreID = OreDictionary.getOreID(ore);
		for (int id : OreDictionary.getOreIDs(stack))
			if (id == oreID)
				return true;
		return false;
	}

	public static boolean areStacksTheSame(ItemStack stack1, ItemStack stack2, boolean matchSize) {
		if (stack1 == null || stack2 == null)
			return false;

		if (stack1.getItem() == stack2.getItem())
			if (stack1.getItemDamage() == stack2.getItemDamage() || isWildcard(stack1.getItemDamage()) || isWildcard(stack2.getItemDamage()))
				if (!matchSize || stack1.stackSize == stack2.stackSize) {
					if (stack1.hasTagCompound() && stack2.hasTagCompound())
						return stack1.getTagCompound().equals(stack2.getTagCompound());
					return stack1.hasTagCompound() == stack2.hasTagCompound();
				}
		return false;
	}

	private static boolean isWildcard(int meta) {
		return meta == OreDictionary.WILDCARD_VALUE;
	}
}