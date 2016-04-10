package ganymedes01.ganysend.core.handlers;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.ModItems;
import ganymedes01.ganysend.items.EndiumBow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class ArmourHandler {

	// Helmet
	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event) {
		if (!GanysEnd.enableEndiumArmour)
			return;

		if (!event.entityLiving.worldObj.isRemote)
			if (event.entityLiving instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) event.entityLiving;
				boolean isWearing;
				if (isWearing = player.getCurrentArmor(3) == null || player.getCurrentArmor(3).getItem() != ModItems.endium_helmet) {
					if (isWearing) {
						isWearing = false;
						PotionEffect NVEffect = player.getActivePotionEffect(Potion.nightVision);
						if (NVEffect != null && NVEffect.getAmplifier() == -3)
							player.removePotionEffect(Potion.nightVision.id);
					}
				} else if (player.getCurrentArmor(3).getItem() == ModItems.endium_helmet && !isWearing)
					isWearing = true;
			}
	}

	// Chestplate
	@SubscribeEvent
	public void onLivingDamage(LivingAttackEvent event) {
		if (!GanysEnd.enableEndiumArmour)
			return;

		if (!event.entityLiving.worldObj.isRemote)
			if (event.entityLiving instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) event.entityLiving;
				if (player.getCurrentArmor(2) != null && player.getCurrentArmor(2).getItem() == ModItems.endium_chestplate)
					if (event.source == DamageSource.inFire || event.source == DamageSource.lava || event.source == DamageSource.onFire)
						if (event.isCancelable())
							event.setCanceled(true);
			}
	}

	// Leggings
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onFOVChange(FOVUpdateEvent event) {
		if (!GanysEnd.enableEndiumArmour)
			return;

		EntityPlayer player = event.entity;
		if (player.getCurrentArmor(1) != null && player.getCurrentArmor(1).getItem() == ModItems.endium_leggings)
			if (event.newfov > 1.0F)
				event.newfov = 1.0F;

		// Endium Bow
		if (player.isUsingItem() && player.getItemInUse().getItem() instanceof EndiumBow) {
			int i = player.getItemInUseDuration();
			float f1 = i / 20.0F;

			if (f1 > 1.0F)
				f1 = 1.0F;
			else
				f1 *= f1;

			event.newfov = event.fov * (1.0F - f1 * 0.15F);
		}
	}
}