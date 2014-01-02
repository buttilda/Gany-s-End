package ganymedes01.ganysend.core.handlers;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.CustomDamageSources;
import ganymedes01.ganysend.core.utils.HeadsHelper;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class EntityDeathEvent {

	@ForgeSubscribe
	public void modDeathEvent(LivingDeathEvent event) {
		if (event.entityLiving.worldObj.isRemote)
			return;
		Random rand = new Random();
		ItemStack stack = HeadsHelper.getHeadfromEntity(event.entityLiving);
		if (stack != null)
			if (checkDamSource(event.source))
				if (event.source == CustomDamageSources.beheading || shouldDoRandomDrop(rand)) {
					event.entityLiving.entityDropItem(stack, rand.nextFloat());
					if (!(event.entityLiving instanceof EntityPlayer))
						if (event.isCancelable())
							event.setCanceled(true);
				}
	}

	private boolean shouldDoRandomDrop(Random rand) {
		return GanysEnd.enableRandomHeadDrop && rand.nextInt(150) == 75;
	}

	private boolean checkDamSource(DamageSource source) {
		return source != DamageSource.fall && source != DamageSource.inFire && source != DamageSource.onFire && source != DamageSource.cactus && source != DamageSource.lava && source != DamageSource.inWall && source != DamageSource.drown && source != DamageSource.starve &&
		source != DamageSource.wither && source != DamageSource.anvil && source != DamageSource.fallingBlock;
	}
}