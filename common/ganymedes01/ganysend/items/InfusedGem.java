package ganymedes01.ganysend.items;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Reference;
import ganymedes01.ganysend.lib.Strings;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class InfusedGem extends Item {

	private static final String[] types = new String[] { "night", "day" };
	@SideOnly(Side.CLIENT)
	private Icon[] icons;

	public InfusedGem(int id) {
		super(id);
		setMaxDamage(0);
		setMaxStackSize(16);
		setHasSubtypes(true);
		setCreativeTab(GanysEnd.endTab);
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.INFUSED_GEM_NAME));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs tab, List list) {
		for (int j = 0; j < types.length; ++j)
			list.add(new ItemStack(id, 1, j));
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int meta) {
		if (meta < 0 || meta >= types.length)
			meta = 0;

		return icons[meta];
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int i = stack.getItemDamage();

		if (i < 0 || i >= types.length)
			i = 0;

		return "item." + Utils.getUnlocalizedName(Strings.INFUSED_GEM_NAME) + "_" + types[i];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		icons = new Icon[types.length];

		for (int i = 0; i < types.length; ++i)
			icons[i] = reg.registerIcon(Reference.ITEM_BLOCK_TEXTURE_PATH + Strings.INFUSED_GEM_NAME + "_" + types[i]);
	}
}
