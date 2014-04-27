package ganymedes01.ganysend.enchantment;

import net.minecraft.enchantment.Enchantment;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class ModEnchants {

	public static Enchantment imperviousness;

	public static void init() {
		imperviousness = new ImperviousnessEnchantment();
	}
}