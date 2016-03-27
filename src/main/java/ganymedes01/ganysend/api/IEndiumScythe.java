package ganymedes01.ganysend.api;

import net.minecraft.item.ItemStack;

public interface IEndiumScythe {

	int getCharges(ItemStack stack);

	void reduceCharge(ItemStack stack);

	void addCharge(ItemStack stack);
}