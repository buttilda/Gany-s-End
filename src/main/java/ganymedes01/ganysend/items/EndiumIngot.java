package ganymedes01.ganysend.items;

import java.util.Arrays;
import java.util.List;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.IConfigurable;
import ganymedes01.ganysend.ModItems.ISubItemsItem;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSimpleFoiled;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class EndiumIngot extends ItemSimpleFoiled implements IConfigurable, ISubItemsItem {

	private static final String[] TYPES = new String[] { Strings.ENDIUM_INGOT_NAME, Strings.ENDIUM_NUGGET_NAME };

	public EndiumIngot() {
		setMaxDamage(0);
		setHasSubtypes(true);
		setCreativeTab(GanysEnd.enableEndium ? GanysEnd.endTab : null);
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.ENDIUM_INGOT_NAME));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> list) {
		for (int i = 0; i < TYPES.length; i++)
			list.add(new ItemStack(item, 1, i));
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "item." + Utils.getUnlocalisedName(TYPES[Math.min(Math.max(stack.getItemDamage(), 0), TYPES.length - 1)]);
	}

	@Override
	public boolean isEnabled() {
		return GanysEnd.enableEndium;
	}

	@Override
	public List<String> getModels() {
		return Arrays.asList(TYPES);
	}
}