package ganymedes01.ganysend.items;

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

public class ReinforcedEndiumAxe extends EndiumAxe {

	public ReinforcedEndiumAxe() {
		super(ModMaterials.REIN_ENDIUM_TOOLS);
		setHarvestLevel("axe", 3);
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.REINFORCED_ENDIUM_AXE_NAME));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.RARE;
	}
}