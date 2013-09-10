package ganymedes01.ganysend.items;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.item.Item;

public class EndstoneRod extends Item {

	public EndstoneRod(int id) {
		super(id);
		setFull3D();
		setCreativeTab(GanysEnd.endTab);
		setTextureName(Utils.getItemTexture(Strings.ENDSTONE_ROD_NAME));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.ENDSTONE_ROD_NAME));
	}
}
