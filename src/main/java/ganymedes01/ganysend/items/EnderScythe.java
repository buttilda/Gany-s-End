package ganymedes01.ganysend.items;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.IConfigurable;
import ganymedes01.ganysend.api.IEndiumScythe;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.ModMaterials;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class EnderScythe extends ItemSword implements IEndiumScythe, IConfigurable {

	public EnderScythe() {
		this(ModMaterials.ENDIUM_TOOLS);
	}

	public EnderScythe(ToolMaterial material) {
		super(material);
		setCreativeTab(GanysEnd.enableScythe ? GanysEnd.endTab : null);
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.ENDER_SCYTHE_NAME));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.EPIC;
	}

	@Override
	public boolean getIsRepairable(ItemStack stack, ItemStack material) {
		return Utils.isStackOre(material, "ingotEndium");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack) {
		return getCharges(stack) > 0;
	}

	@Override
	public int getCharges(ItemStack stack) {
		if (!stack.hasTagCompound())
			return 0;
		return stack.getTagCompound().getInteger("Charges");
	}

	@Override
	public void reduceCharge(ItemStack stack) {
		int charges = getCharges(stack);
		System.out.println(charges);
		if (charges > 0)
			stack.getTagCompound().setInteger("Charges", charges - 1);
	}

	@Override
	public void addCharge(ItemStack stack) {
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setInteger("Charges", getCharges(stack) + 1);
	}

	@Override
	public boolean isEnabled() {
		return GanysEnd.enableScythe;
	}
}