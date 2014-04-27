package ganymedes01.ganysend.core.handlers;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.BeheadingDamage;
import ganymedes01.ganysend.core.utils.HeadsHelper;

import java.util.Random;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class EntityDeathEvent {

	@SubscribeEvent
	public void modDeathEvent(LivingDeathEvent event) {
		if (event.entityLiving.worldObj.isRemote)
			return;
		Random rand = event.entityLiving.worldObj.rand;
		boolean isScythe = event.source instanceof BeheadingDamage;
		if (isScythe || shouldDoRandomDrop(rand, lootingLevel(event.source)))
			if (checkDamSource(event.source)) {
				ItemStack stack = HeadsHelper.getHeadfromEntity(event.entityLiving);
				if (stack != null) {
					if (!isScythe && stack.getItem() == Items.skull && stack.getItemDamage() == 1)
						return;
					event.entityLiving.entityDropItem(stack, rand.nextFloat());
					if (!(event.entityLiving instanceof EntityPlayer))
						if (event.isCancelable())
							event.setCanceled(true);
				}
			}
	}

	private int lootingLevel(DamageSource source) {
		if (source instanceof EntityDamageSource) {
			Entity entity = ((EntityDamageSource) source).getEntity();
			if (entity instanceof EntityPlayer) {
				ItemStack stack = ((EntityPlayer) entity).getCurrentEquippedItem();
				if (stack != null) {
					NBTTagList list = stack.getEnchantmentTagList();
					if (list != null)
						for (int i = 0; i < list.tagCount(); i++)
							if (list.getCompoundTagAt(i).getShort("id") == Enchantment.looting.effectId)
								return list.getCompoundTagAt(i).getShort("lvl");
				}
			}
		}
		return 0;
	}

	private boolean shouldDoRandomDrop(Random rand, int looting) {
		return GanysEnd.enableRandomHeadDrop && rand.nextInt(Math.max(200 - 50 * looting, 50)) == 0;
	}

	private boolean checkDamSource(DamageSource source) {
		return source != DamageSource.fall && source != DamageSource.inFire && source != DamageSource.onFire && source != DamageSource.cactus && source != DamageSource.lava && source != DamageSource.inWall && source != DamageSource.drown && source != DamageSource.starve && source != DamageSource.wither && source != DamageSource.anvil && source != DamageSource.fallingBlock;
	}
}