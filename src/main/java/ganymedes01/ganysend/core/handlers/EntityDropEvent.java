package ganymedes01.ganysend.core.handlers;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.api.IEndiumScythe;
import ganymedes01.ganysend.api.IEndiumTool;
import ganymedes01.ganysend.core.utils.HeadsHelper;
import ganymedes01.ganysend.core.utils.InventoryUtils;
import ganymedes01.ganysend.core.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class EntityDropEvent {

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void dropEvent(LivingDropsEvent event) {
		if (event.entityLiving.worldObj.isRemote)
			return;
		if (event.entityLiving.getHealth() > 0.0F)
			return;

		ItemStack weapon = getWeapon(event.source);

		// Drop heads
		if (!GanysEnd.isHeadcrumbsLoaded)
			if (shouldDoRandomDrop(event.entityLiving.worldObj.rand, event.lootingLevel)) {
				ItemStack stack = HeadsHelper.getHeadfromEntity(event.entityLiving);
				if (stack != null)
					if (canDropThisHead(stack))
						addDrop(stack, event.entityLiving, event.drops);
			}

		// Scythe drop
		if (GanysEnd.enableScythe && weapon != null && weapon.getItem() instanceof IEndiumScythe) {
			ItemStack stack = HeadsHelper.getHeadfromEntity(event.entityLiving);
			if (stack != null)
				addDrop(stack, event.entityLiving, event.drops);
		}

		// Collect drops
		if (!GanysEnd.enableEndiumTools)
			return;
		if (weapon != null && isEndiumTool(weapon))
			if (weapon.hasTagCompound())
				if (weapon.getTagCompound().getBoolean("Tagged")) {
					NBTTagCompound data = weapon.getTagCompound();
					int x = data.getIntArray("Position")[0];
					int y = data.getIntArray("Position")[1];
					int z = data.getIntArray("Position")[2];
					int dim = data.getInteger("Dimension");

					if (event.entityLiving.worldObj.provider.getDimensionId() == dim) {
						IInventory tile = Utils.getTileEntity(event.entityLiving.worldObj, new BlockPos(x, y, z), IInventory.class);
						if (tile != null) {
							List<EntityItem> deads = new LinkedList<EntityItem>();
							for (EntityItem entityitem : event.drops) {
								InventoryUtils.addEntitytoInventory(tile, entityitem);
								if (entityitem.isDead)
									deads.add(entityitem);
							}
							for (EntityItem entityitem : deads)
								event.drops.remove(entityitem);
						}
					}
				}
	}

	private ItemStack getWeapon(DamageSource source) {
		if (source instanceof EntityDamageSource) {
			Entity entity = ((EntityDamageSource) source).getEntity();
			if (entity instanceof EntityPlayer)
				return ((EntityPlayer) entity).getCurrentEquippedItem();
		}
		return null;
	}

	private boolean canDropThisHead(ItemStack stack) {
		return stack.getItem() == Items.skull ? stack.getItemDamage() != 1 && GanysEnd.enableVanillaHeadsDrop : true;
	}

	private boolean shouldDoRandomDrop(Random rand, int looting) {
		return GanysEnd.enableRandomHeadDrop && rand.nextInt(Math.max(200 - 50 * looting, 50)) == 0;
	}

	private void addDrop(ItemStack stack, EntityLivingBase entity, List<EntityItem> list) {
		if (stack.stackSize <= 0)
			return;

		EntityItem entityItem = new EntityItem(entity.worldObj, entity.posX, entity.posY, entity.posZ, stack);
		entityItem.setDefaultPickupDelay();
		list.add(entityItem);
	}

	public static boolean isEndiumTool(ItemStack stack) {
		return isTinkersEndiumTool(stack) || stack.getItem() instanceof IEndiumTool;
	}

	public static boolean isTinkersEndiumTool(ItemStack stack) {
		return stack.hasTagCompound() && stack.getTagCompound().getBoolean("EndiumTool");
	}
}