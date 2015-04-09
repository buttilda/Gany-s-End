package ganymedes01.ganysend.items;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.item.Item;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class EndstoneRod extends Item {

	public EndstoneRod() {
		setFull3D();
		setTextureName(Utils.getItemTexture(Strings.ENDSTONE_ROD_NAME));
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.ENDSTONE_ROD_NAME));
		setCreativeTab(GanysEnd.enableEndiumTools || GanysEnd.enableScythe ? GanysEnd.endTab : null);
	}
}