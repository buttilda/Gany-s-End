package ganymedes01.ganysend.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.ModMaterials;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class ReinforcedEndiumBow extends EndiumBow {

	public ReinforcedEndiumBow() {
		setMaxDamage(ModMaterials.REIN_ENDIUM_TOOLS.getMaxUses());
		setCreativeTab(GanysEnd.enableEndiumTools ? GanysEnd.endTab : null);
		setTextureName(Utils.getItemTexture(Strings.REINFORCED_ENDIUM_BOW_NAME));
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.REINFORCED_ENDIUM_BOW_NAME));
	}

	@Override
	public int getItemEnchantability() {
		return ModMaterials.REIN_ENDIUM_TOOLS.getEnchantability();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.rare;
	}
}