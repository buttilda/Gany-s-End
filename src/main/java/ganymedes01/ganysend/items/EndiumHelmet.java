package ganymedes01.ganysend.items;

import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class EndiumHelmet extends EndiumArmour {

	public EndiumHelmet() {
		super(0);
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.ENDIUM_HELMET_NAME));
	}

	@Override
	protected void handleInWater(EntityPlayer player, ItemStack stack, boolean isWaterproof) {
		if (!isWaterproof && player.isInWater()) {
			stack.damageItem(1, player);
			player.attackEntityFrom(DamageSource.generic, 1F);
			player.removePotionEffect(Potion.nightVision.id);
		} else if (player.getActivePotionEffect(Potion.nightVision) == null)
			player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 500, -3));
		else if (player.getActivePotionEffect(Potion.nightVision).getDuration() < 210)
			player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 500, -3));
	}
}