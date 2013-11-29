package ganymedes01.ganysend.items;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.ModIDs;
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
		super(ModIDs.ENDSTONE_ROD_ID);
		setFull3D();
		setCreativeTab(GanysEnd.endTab);
		setTextureName(Utils.getItemTexture(Strings.ENDSTONE_ROD_NAME));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.ENDSTONE_ROD_NAME));
	}
}