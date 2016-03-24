package ganymedes01.ganysend.items;

import java.util.List;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.IConfigurable;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class InfusedGem extends Item implements IConfigurable {

	private static final String[] types = new String[] { "night", "day" };

	public InfusedGem() {
		setMaxDamage(0);
		setMaxStackSize(16);
		setHasSubtypes(true);
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.INFUSED_GEM_NAME));
		setCreativeTab(GanysEnd.enableTimeManipulator ? GanysEnd.endTab : null);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> list) {
		for (int i = 0; i < types.length; i++)
			list.add(new ItemStack(item, 1, i));
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int meta = stack.getItemDamage();

		if (meta < 0 || meta >= types.length)
			meta = 0;

		return "item." + Utils.getUnlocalisedName(Strings.INFUSED_GEM_NAME) + "_" + types[meta];
	}

	@Override
	public boolean isEnabled() {
		return GanysEnd.enableTimeManipulator;
	}
}