package ganymedes01.ganysend.core.handlers;

import ganymedes01.ganysend.ModItems;
import ganymedes01.ganysend.items.EndiumBow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
		if (!event.entityLiving.worldObj.isRemote)
			if (event.entityLiving instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) event.entityLiving;
				boolean isWearing;
				if (isWearing = player.getCurrentArmor(3) == null || player.getCurrentArmor(3).getItem() != ModItems.endiumHelmet) {
					if (isWearing) {
						isWearing = false;
						PotionEffect NVEffect = player.getActivePotionEffect(Potion.nightVision);
						if (NVEffect != null && NVEffect.getAmplifier() == -3)
							player.removePotionEffect(Potion.nightVision.id);
					}
				} else if (player.getCurrentArmor(3).getItem() == ModItems.endiumHelmet && !isWearing)
					isWearing = true;
			}
	}

	// Chestplate
	@SubscribeEvent
	public void onLivingDamage(LivingAttackEvent event) {
		if (!event.entityLiving.worldObj.isRemote)
			if (event.entityLiving instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) event.entityLiving;
				if (player.getCurrentArmor(2) != null && player.getCurrentArmor(2).getItem() == ModItems.endiumChestplate)
					if (event.source == DamageSource.inFire || event.source == DamageSource.lava || event.source == DamageSource.onFire)
						if (event.isCancelable())
							event.setCanceled(true);
			}
	}

	// Leggings
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onFOVChange(FOVUpdateEvent event) {
		EntityPlayer player = event.entity;
		if (player.getCurrentArmor(1) != null && player.getCurrentArmor(1).getItem() == ModItems.endiumLeggings)
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