package ganymedes01.ganysend.items;

import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EndiumBoots extends EndiumArmour {

	private int coolDown;

	public EndiumBoots(int id) {
		super(id, 3);
		coolDown = maxCoolDown;
		setTextureName(Utils.getItemTexture(Strings.ENDIUM_BOOTS_NAME));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.ENDIUM_BOOTS_NAME));
	}

	@Override
	public void onArmorTickUpdate(World world, EntityPlayer player, ItemStack itemStack) {
		if (getDamage(itemStack) >= this.getMaxDamage()) {
			itemStack.stackSize = 0;
			player.renderBrokenItemStack(itemStack);
			player.setCurrentItemOrArmor(1, null);
			return;
		}
		if (world.isRaining()) {
			int xCoord = MathHelper.floor_double(player.posX);
			int yCoord = MathHelper.floor_double(player.posY) + 1;
			int zCoord = MathHelper.floor_double(player.posZ);
			if (world.canBlockSeeTheSky(xCoord, yCoord, zCoord))
				coolDown--;
			if (coolDown == 0) {
				itemStack.damageItem(1, player);
				coolDown = maxCoolDown;
			}
		}
		if (player.isInWater()) {
			itemStack.damageItem(1, player);
			player.attackEntityFrom(DamageSource.generic, 1F);
		} else
			player.fallDistance = 0.0F;
	}
}
