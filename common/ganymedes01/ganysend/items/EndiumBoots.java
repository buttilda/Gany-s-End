package ganymedes01.ganysend.items;

import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.ModIDs;
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

public class EndiumBoots extends EndiumArmour {

	public EndiumBoots() {
		super(ModIDs.ENDIUM_BOOTS_ID, 3);
		setTextureName(Utils.getItemTexture(Strings.ENDIUM_BOOTS_NAME));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.ENDIUM_BOOTS_NAME));
	}

	@Override
	protected void handleInWater(EntityPlayer player, ItemStack stack, boolean isWaterproof) {
		if (!isWaterproof && player.isInWater()) {
			stack.damageItem(1, player);
			player.attackEntityFrom(DamageSource.generic, 1F);
		} else
			player.fallDistance = 0.0F;
	}
}