package ganymedes01.ganysend.core.utils;

import net.minecraft.util.DamageSource;

public class CustomDamageSources extends DamageSource {

	public static final DamageSource beheading = new CustomDamageSources().setDamageBypassesArmor();

	protected CustomDamageSources() {
		super("ganysEndBehead");
	}
}
