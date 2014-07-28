package ganymedes01.ganysend.core.handlers;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.BeheadingDamage;
import ganymedes01.ganysend.core.utils.HeadsHelper;
import ganymedes01.ganysend.core.utils.InventoryUtils;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.IEndiumTool;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

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

		// Drop heads
		boolean isScythe = event.source instanceof BeheadingDamage;
		if (isScythe || shouldDoRandomDrop(event.entityLiving.worldObj.rand, event.lootingLevel))
			if (checkDamSource(event.source)) {
				ItemStack stack = HeadsHelper.getHeadfromEntity(event.entityLiving);
				if (stack != null)
					if (isScythe || !isWitherSkull(stack))
						addDrop(stack, event.entityLiving, event.drops);
			}

		// Collect drops
		ItemStack weapon = getWeapon(event.source);
		if (weapon != null)
			if (weapon.getItem() instanceof IEndiumTool)
				if (weapon.stackTagCompound != null)
					if (weapon.getTagCompound().getBoolean("Tagged")) {
						NBTTagCompound data = weapon.getTagCompound();
						int x = data.getIntArray("Position")[0];
						int y = data.getIntArray("Position")[1];
						int z = data.getIntArray("Position")[2];
						int dim = data.getInteger("Dimension");

						if (event.entityLiving.worldObj.provider.dimensionId == dim) {
							IInventory tile = Utils.getTileEntity(event.entityLiving.worldObj, x, y, z, IInventory.class);
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

	private boolean isWitherSkull(ItemStack stack) {
		return stack.getItem() == Items.skull && stack.getItemDamage() == 1;
	}

	private boolean shouldDoRandomDrop(Random rand, int looting) {
		return GanysEnd.enableRandomHeadDrop && rand.nextInt(Math.max(200 - 50 * looting, 50)) == 0;
	}

	private boolean checkDamSource(DamageSource source) {
		return source != DamageSource.fall && source != DamageSource.inFire && source != DamageSource.onFire && source != DamageSource.cactus && source != DamageSource.lava && source != DamageSource.inWall && source != DamageSource.drown && source != DamageSource.starve && source != DamageSource.wither && source != DamageSource.anvil && source != DamageSource.fallingBlock;
	}

	private void addDrop(ItemStack stack, EntityLivingBase entity, List<EntityItem> list) {
		if (stack.stackSize <= 0)
			return;

		EntityItem entityItem = new EntityItem(entity.worldObj, entity.posX, entity.posY, entity.posZ, stack);
		entityItem.delayBeforeCanPickup = 10;
		list.add(entityItem);
	}
}