package ganymedes01.ganysend.items;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.CustomDamageSources;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.ModIDs;
import ganymedes01.ganysend.lib.ModMaterials;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.StatList;
import net.minecraft.util.MathHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class EnderScythe extends ItemSword {

	public EnderScythe() {
		super(ModIDs.ENDER_SCYTHE_ID, ModMaterials.ENDIUM_TOOLS);
		setMaxStackSize(1);
		setCreativeTab(GanysEnd.endTab);
		setTextureName(Utils.getItemTexture(Strings.ENDER_SCYTHE_NAME));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.ENDER_SCYTHE_NAME));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.epic;
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity target) {
		if (!player.worldObj.isRemote && stack != null)
			if (target instanceof EntityLivingBase) {
				float dmg = 4.0F + ModMaterials.ENDIUM_TOOLS.getDamageVsEntity();
				if (shouldDamage(target) && target.canAttackWithItem() && !target.hitByEntity(player)) {
					if (target.attackEntityFrom(CustomDamageSources.beheading, 4.0F + ModMaterials.ENDIUM_TOOLS.getDamageVsEntity())) {
						target.addVelocity(-MathHelper.sin(player.rotationYaw * (float) Math.PI / 180.0F) * 0.5F, 0.5D, MathHelper.cos(player.rotationYaw * (float) Math.PI / 180.0F) * 0.5F);
						player.motionX *= 0.6D;
						player.motionZ *= 0.6D;
						player.setSprinting(false);
						player.setLastAttacker(target);
					}

					stack.damageItem(1, player);
					player.addStat(StatList.damageDealtStat, Math.round(dmg * 10.0F));
					player.addExhaustion(0.3F);
					if (stack.stackSize <= 0)
						player.destroyCurrentEquippedItem();
				}
			}
		return true;
	}

	private boolean shouldDamage(Entity target) {
		return target instanceof EntityPlayer ? MinecraftServer.getServer().isPVPEnabled() : true;
	}

	@Override
	public boolean getIsRepairable(ItemStack item, ItemStack material) {
		return material.getItem() == ModItems.endiumIngot;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack item) {
		return true;
	}
}