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

public class EndiumChestplate extends EndiumArmour {

	public EndiumChestplate() {
		super(ModIDs.ENDIUM_CHESTPLATE_ID, 1);
		setTextureName(Utils.getItemTexture(Strings.ENDIUM_CHESTPLATE_NAME));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.ENDIUM_CHESTPLATE_NAME));
	}

	@Override
	protected void handleInWater(EntityPlayer player, ItemStack stack) {
		if (player.isInWater()) {
			stack.damageItem(1, player);
			player.attackEntityFrom(DamageSource.generic, 1F);
		}
	}
}