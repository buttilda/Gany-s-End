package ganymedes01.ganysend.core.utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class BeheadingDamage extends DamageSource {

	private final EntityPlayer player;

	protected BeheadingDamage(EntityPlayer player) {
		super("ganysEndBehead");
		this.player = player;
		setDamageBypassesArmor();
	}

	@Override
	public Entity getEntity() {
		return player;
	}

	public static BeheadingDamage create(EntityPlayer player) {
		return new BeheadingDamage(player);
	}
}