package ganymedes01.ganysend.items;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.IConfigurable;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Reference;
import ganymedes01.ganysend.lib.Strings;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class InfusedGem extends Item implements IConfigurable {

	private static final String[] types = new String[] { "night", "day" };
	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	public InfusedGem() {
		setMaxDamage(0);
		setMaxStackSize(16);
		setHasSubtypes(true);
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.INFUSED_GEM_NAME));
		setCreativeTab(GanysEnd.enableTimeManipulator ? GanysEnd.endTab : null);
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getSubItems(Item id, CreativeTabs tab, List list) {
		for (int i = 0; i < types.length; i++)
			list.add(new ItemStack(id, 1, i));
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		if (meta < 0 || meta >= types.length)
			meta = 0;

		return icons[meta];
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int meta = stack.getItemDamage();

		if (meta < 0 || meta >= types.length)
			meta = 0;

		return "item." + Utils.getUnlocalisedName(Strings.INFUSED_GEM_NAME) + "_" + types[meta];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		icons = new IIcon[types.length];

		for (int i = 0; i < types.length; i++)
			icons[i] = reg.registerIcon(Reference.ITEM_BLOCK_TEXTURE_PATH + Strings.INFUSED_GEM_NAME + "_" + types[i]);
	}

	@Override
	public boolean isEnabled() {
		return GanysEnd.enableTimeManipulator;
	}
}