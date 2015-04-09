package ganymedes01.ganysend.items;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
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

public class EndiumIngot extends Item {

	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	public EndiumIngot() {
		setMaxDamage(0);
		setHasSubtypes(true);
		setCreativeTab(GanysEnd.enableEndium ? GanysEnd.endTab : null);
		setTextureName(Utils.getItemTexture(Strings.ENDIUM_INGOT_NAME));
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.ENDIUM_INGOT_NAME));
	}

	@Override
	@SideOnly(Side.CLIENT)
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
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		if (meta < 0 || meta >= 2)
			meta = 0;

		return icons[meta];
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int meta = stack.getItemDamage();

		if (meta < 0 || meta >= 2)
			meta = 0;

		return "item." + (meta == 0 ? Utils.getUnlocalisedName(Strings.ENDIUM_INGOT_NAME) : Utils.getUnlocalisedName(Strings.ENDIUM_NUGGET_NAME));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		icons = new IIcon[2];

		icons[0] = reg.registerIcon(Utils.getItemTexture(Strings.ENDIUM_INGOT_NAME));
		icons[1] = reg.registerIcon(Utils.getItemTexture(Strings.ENDIUM_NUGGET_NAME));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack, int pass) {
		return true;
	}
}