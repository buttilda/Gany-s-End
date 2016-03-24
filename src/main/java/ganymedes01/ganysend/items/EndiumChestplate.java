package ganymedes01.ganysend.items;

import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class EndiumChestplate extends EndiumArmour {

	public EndiumChestplate() {
		super(1);
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.ENDIUM_CHESTPLATE_NAME));
	}

	@Override
	protected void handleInWater(EntityPlayer player, ItemStack stack, boolean isWaterproof) {
		if (!isWaterproof && player.isInWater()) {
			stack.damageItem(1, player);
			player.attackEntityFrom(DamageSource.generic, 1F);
		}
	}
}