package ganymedes01.ganysend.items;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.ModItems;
import ganymedes01.ganysend.core.utils.BeheadingDamage;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.ModMaterials;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.stats.AchievementList;
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
		super(ModMaterials.ENDIUM_TOOLS);
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
		if (!target.canAttackWithItem())
			return true;

		if (target.hitByEntity(player))
			return true;

		float damage = (float) player.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
		int knockback = 0;
		float magic = 0.0F;

		if (target instanceof EntityLivingBase) {
			magic = EnchantmentHelper.getEnchantmentModifierLiving(player, (EntityLivingBase) target);
			knockback += EnchantmentHelper.getKnockbackModifier(player, (EntityLivingBase) target);
		}

		if (player.isSprinting())
			knockback++;

		if (damage > 0.0F || magic > 0.0F) {
			boolean canCrit = player.fallDistance > 0.0F && !player.onGround && !player.isOnLadder() && !player.isInWater() && !player.isPotionActive(Potion.blindness) && player.ridingEntity == null && target instanceof EntityLivingBase;

			if (canCrit && damage > 0.0F)
				damage *= 1.5F;

			damage += magic;
			boolean fireAspect = false;
			int fire = EnchantmentHelper.getFireAspectModifier(player);

			if (target instanceof EntityLivingBase && fire > 0 && !target.isBurning()) {
				fireAspect = true;
				target.setFire(1);
			}

			boolean damaged = target.attackEntityFrom(BeheadingDamage.create(player), damage);

			if (damaged) {
				if (knockback > 0) {
					target.addVelocity(-MathHelper.sin(player.rotationYaw * (float) Math.PI / 180.0F) * knockback * 0.5F, 0.1D, MathHelper.cos(player.rotationYaw * (float) Math.PI / 180.0F) * knockback * 0.5F);
					player.motionX *= 0.6D;
					player.motionZ *= 0.6D;
					player.setSprinting(false);
				}

				if (canCrit)
					player.onCriticalHit(target);

				if (magic > 0.0F)
					player.onEnchantmentCritical(target);

				if (damage >= 18.0F)
					player.triggerAchievement(AchievementList.overkill);

				player.setLastAttacker(target);

				if (target instanceof EntityLivingBase)
					EnchantmentHelper.func_151384_a((EntityLivingBase) target, player);

				EnchantmentHelper.func_151385_b(player, target);
				ItemStack itemstack = player.getCurrentEquippedItem();
				Object object = target;

				if (target instanceof EntityDragonPart) {
					IEntityMultiPart ientitymultipart = ((EntityDragonPart) target).entityDragonObj;

					if (ientitymultipart != null && ientitymultipart instanceof EntityLivingBase)
						object = ientitymultipart;
				}

				if (itemstack != null && object instanceof EntityLivingBase) {
					itemstack.hitEntity((EntityLivingBase) object, player);

					if (itemstack.stackSize <= 0)
						player.destroyCurrentEquippedItem();
				}

				if (target instanceof EntityLivingBase) {
					player.addStat(StatList.damageDealtStat, Math.round(damage * 10.0F));

					if (fire > 0)
						target.setFire(fire * 4);
				}

				player.addExhaustion(0.3F);
			} else if (fireAspect)
				target.extinguish();
		}

		return true;
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