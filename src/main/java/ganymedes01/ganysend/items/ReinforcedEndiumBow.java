package ganymedes01.ganysend.items;

import java.util.Arrays;
import java.util.List;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.ModMaterials;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.REINFORCED_ENDIUM_BOW_NAME));
	}

	@Override
	public int getItemEnchantability() {
		return ModMaterials.REIN_ENDIUM_TOOLS.getEnchantability();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.RARE;
	}

	@Override
	public List<String> getModels() {
		return Arrays.asList("reinforced_endium_bow", "reinforced_endium_bow_pulling_0", "reinforced_endium_bow_pulling_1", "reinforced_endium_bow_pulling_2");
	}
}