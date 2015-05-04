package ganymedes01.ganysend.items;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.IConfigurable;
import ganymedes01.ganysend.api.IEndiumScythe;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.ModMaterials;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class EnderScythe extends ItemSword implements IEndiumScythe, IConfigurable {

	@SideOnly(Side.CLIENT)
	private IIcon overlay;

	public EnderScythe() {
		this(ModMaterials.ENDIUM_TOOLS);
	}

	public EnderScythe(ToolMaterial material) {
		super(material);
		setCreativeTab(GanysEnd.enableScythe ? GanysEnd.endTab : null);
		setTextureName(Utils.getItemTexture(Strings.ENDER_SCYTHE_NAME));
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.ENDER_SCYTHE_NAME));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.epic;
	}

	@Override
	public boolean getIsRepairable(ItemStack stack, ItemStack material) {
		return Utils.isStackOre(material, "ingotEndium");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack, int pass) {
		return pass == 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		super.registerIcons(reg);
		overlay = reg.registerIcon(getIconString() + "_overlay");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int meta, int pass) {
		return pass == 0 ? itemIcon : overlay;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return GanysEnd.enableScythe;
	}
}