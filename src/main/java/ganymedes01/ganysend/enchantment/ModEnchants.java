package ganymedes01.ganysend.enchantment;

import ganymedes01.ganysend.GanysEnd;
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
		if (GanysEnd.enableEndiumArmour) {
			imperviousness = new ImperviousnessEnchantment();
			Enchantment.addToBookList(imperviousness);
		}
	}
}