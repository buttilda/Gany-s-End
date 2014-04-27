package ganymedes01.ganysend.enchantment;

import ganymedes01.ganysend.lib.ModIDs;
import ganymedes01.ganysend.lib.ModMaterials;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class ImperviousnessEnchantment extends Enchantment {

	public ImperviousnessEnchantment() {
		super(ModIDs.IMPERVIOUSNESS_ID, 5, EnumEnchantmentType.armor);
		addToBookList(this);
		setName("imperviousness");
	}

	@Override
	public int getMinEnchantability(int lvl) {
		return 25;
	}

	@Override
	public int getMaxEnchantability(int lvl) {
		return 40;
	}

	@Override
	public boolean isAllowedOnBooks() {
		return false;
	}

	@Override
	public boolean canApply(ItemStack stack) {
		if (stack != null && stack.getItem() instanceof ItemArmor) {
			String materialName = ((ItemArmor) stack.getItem()).getArmorMaterial().name();
			return materialName.equals(ModMaterials.ENDIUM_ARMOUR.name()) || materialName.compareToIgnoreCase("BLAZE") == 0;
		}
		return false;
	}
}