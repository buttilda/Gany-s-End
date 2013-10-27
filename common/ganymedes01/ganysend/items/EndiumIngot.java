package ganymedes01.ganysend.items;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.ModIDs;
import ganymedes01.ganysend.lib.Strings;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSimpleFoiled;
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

public class EndiumIngot extends ItemSimpleFoiled {

	@SideOnly(Side.CLIENT)
	private Icon[] icons;

	public EndiumIngot() {
		super(ModIDs.ENDIUM_INGOT_ID);
		setMaxDamage(0);
		setHasSubtypes(true);
		setCreativeTab(GanysEnd.endTab);
		setTextureName(Utils.getItemTexture(Strings.ENDIUM_INGOT_NAME));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.ENDIUM_INGOT_NAME));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs tab, List list) {
		list.add(new ItemStack(id, 1, 0));
		list.add(new ItemStack(id, 1, 1));
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int meta) {
		if (meta < 0 || meta >= 2)
			meta = 0;

		return icons[meta];
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int meta = stack.getItemDamage();

		if (meta < 0 || meta >= 2)
			meta = 0;

		return "item." + (meta == 0 ? Utils.getUnlocalizedName(Strings.ENDIUM_INGOT_NAME) : Utils.getUnlocalizedName(Strings.ENDIUM_NUGGET_NAME));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		icons = new Icon[2];

		icons[0] = reg.registerIcon(Utils.getItemTexture(Strings.ENDIUM_INGOT_NAME));
		icons[1] = reg.registerIcon(Utils.getItemTexture(Strings.ENDIUM_NUGGET_NAME));
	}
}