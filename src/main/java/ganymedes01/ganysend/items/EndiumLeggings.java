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

public class EndiumLeggings extends EndiumArmour {

	public EndiumLeggings() {
		super(2);
		setTextureName(Utils.getItemTexture(Strings.ENDIUM_LEGGINGS_NAME));
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.ENDIUM_LEGGINGS_NAME));
	}

	@Override
	protected void handleInWater(EntityPlayer player, ItemStack stack, boolean isWaterproof) {
		if (!isWaterproof && player.isInWater()) {
			stack.damageItem(1, player);
			player.attackEntityFrom(DamageSource.generic, 1F);
		} else if (player.isSprinting()) {
			player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 0, 10));
			player.addPotionEffect(new PotionEffect(Potion.jump.id, 0, 10));
		}
	}
}