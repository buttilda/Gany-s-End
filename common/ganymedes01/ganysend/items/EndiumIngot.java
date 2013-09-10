package ganymedes01.ganysend.items;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.item.ItemSimpleFoiled;

public class EndiumIngot extends ItemSimpleFoiled {

	public EndiumIngot(int id) {
		super(id);
		setCreativeTab(GanysEnd.endTab);
		setTextureName(Utils.getItemTexture(Strings.ENDIUM_INGOT_NAME));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.ENDIUM_INGOT_NAME));
	}
}
