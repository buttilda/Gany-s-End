package ganymedes01.ganysend.items;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.ModMaterials;
import ganymedes01.ganysend.lib.Strings;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class ReinforcedEnderScythe extends EnderScythe {

	public ReinforcedEnderScythe() {
		super(ModMaterials.REIN_ENDIUM_TOOLS);
		setCreativeTab(GanysEnd.enableScythe ? GanysEnd.endTab : null);
		setTextureName(Utils.getItemTexture(Strings.REINFORCED_ENDER_SCYTHE_NAME));
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.REINFORCED_ENDER_SCYTHE_NAME));
	}
}