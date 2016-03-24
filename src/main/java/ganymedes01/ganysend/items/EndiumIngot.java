package ganymedes01.ganysend.items;

import java.util.List;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.IConfigurable;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSimpleFoiled;
import net.minecraft.item.ItemStack;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class EndiumIngot extends ItemSimpleFoiled implements IConfigurable {

	public EndiumIngot() {
		setMaxDamage(0);
		setHasSubtypes(true);
		setCreativeTab(GanysEnd.enableEndium ? GanysEnd.endTab : null);
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.ENDIUM_INGOT_NAME));
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getSubItems(Item id, CreativeTabs tab, List list) {
		list.add(new ItemStack(id, 1, 0));
		list.add(new ItemStack(id, 1, 1));
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int meta = stack.getItemDamage();

		if (meta < 0 || meta >= 2)
			meta = 0;

		return "item." + (meta == 0 ? Utils.getUnlocalisedName(Strings.ENDIUM_INGOT_NAME) : Utils.getUnlocalisedName(Strings.ENDIUM_NUGGET_NAME));
	}

	@Override
	public boolean isEnabled() {
		return GanysEnd.enableEndium;
	}
}