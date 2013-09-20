package ganymedes01.ganysend.core.handlers;

import ganymedes01.ganysend.items.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class ArmourHandler {

	boolean isWearing = false;

	@ForgeSubscribe
	public void onLivingUpdate(LivingUpdateEvent event) {
		if (event.entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			if (player.getCurrentArmor(3) == null || player.getCurrentArmor(3).itemID != ModItems.endiumHelmet.itemID) {
				if (isWearing) {
					isWearing = false;
					player.removePotionEffect(Potion.nightVision.id);
				}
			} else if (player.getCurrentArmor(3).itemID == ModItems.endiumHelmet.itemID && !isWearing)
				isWearing = true;
		}
	}

	@ForgeSubscribe
	public void onLivingDamage(LivingAttackEvent event) {
		if (event.entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			if (event.source == DamageSource.inFire || event.source == DamageSource.lava)
				if (player.getCurrentArmor(2) != null && player.getCurrentArmor(2).itemID == ModItems.endiumChestplate.itemID)
					event.setCanceled(true);
		}
	}
}
