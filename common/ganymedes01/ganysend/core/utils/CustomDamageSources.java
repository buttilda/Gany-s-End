package ganymedes01.ganysend.core.utils;

import net.minecraft.util.DamageSource;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class CustomDamageSources extends DamageSource {

	public static final DamageSource beheading = new CustomDamageSources().setDamageBypassesArmor();

	protected CustomDamageSources() {
		super("ganysEndBehead");
	}
}
