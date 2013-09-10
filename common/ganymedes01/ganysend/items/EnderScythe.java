package ganymedes01.ganysend.items;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.CustomDamageSources;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.ModMaterials;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class EnderScythe extends ItemSword {

	public EnderScythe(int id) {
		super(id, ModMaterials.ENDIUM);
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

	private void damageItem(ItemStack item, EntityLivingBase player) {
		if (player instanceof EntityPlayer)
			if (((EntityPlayer) player).capabilities.isCreativeMode)
				return;
		item.damageItem(5, player);
	}

	private void behead(ItemStack item, EntityLivingBase target, EntityLivingBase player, int meta) {
		target.attackEntityFrom(CustomDamageSources.beheading, 51F);
		target.entityDropItem(new ItemStack(ModItems.itemNewSkull, 1, meta), 1F);
		damageItem(item, player);
	}

	@Override
	public boolean hitEntity(ItemStack item, EntityLivingBase target, EntityLivingBase player) {
		if (!player.worldObj.isRemote)
			if (target instanceof EntityMob) {
				if (target instanceof EntityCreeper) {
					target.entityDropItem(new ItemStack(Item.skull.itemID, 1, 4), 1.0F);
					target.attackEntityFrom(CustomDamageSources.beheading, 21F);
					damageItem(item, player);
					return true;
				} else if (target instanceof EntitySkeleton) {
					int type = ((EntitySkeleton) target).getSkeletonType();
					if (type == 1) // Wither
						target.entityDropItem(new ItemStack(Item.skull, 1, 1), 1F);
					else if (type == 0) // Normal
						target.entityDropItem(new ItemStack(Item.skull, 1, 0), 1F);
					else
						return false;
					target.attackEntityFrom(CustomDamageSources.beheading, 21F);
					damageItem(item, player);
					return true;
				} else if (target instanceof EntityZombie) {
					if (target instanceof EntityPigZombie) {
						behead(item, target, player, 2);
						return true;
					} else if (((EntityZombie) target).isVillager()) {
						behead(item, target, player, 14);
						return true;
					} else {
						target.attackEntityFrom(CustomDamageSources.beheading, 50F);
						target.entityDropItem(new ItemStack(Item.skull, 1, 2), 1F);
						damageItem(item, player);
						return true;
					}
				} else if (target instanceof EntityEnderman) {
					behead(item, target, player, 1);
					return true;
				} else if (target instanceof EntityBlaze) {
					behead(item, target, player, 0);
					return true;
				} else if (target instanceof EntitySpider) {
					if (target instanceof EntityCaveSpider)
						behead(item, target, player, 5);
					else
						behead(item, target, player, 4);
					return true;
				} else if (target instanceof EntityWitch) {
					behead(item, target, player, 13);
					return true;
				}
			} else if (target instanceof EntityPlayer) {
				target.attackEntityFrom(CustomDamageSources.beheading, 10F);
				if (target.getHealth() <= 0.0F) {
					ItemStack stack = new ItemStack(ModItems.itemNewSkull, 1, 3);
					String username = ((EntityPlayer) target).username;
					stack.setTagCompound(new NBTTagCompound());
					stack.getTagCompound().setString("SkullOwner", username);
					target.entityDropItem(stack, 1F);
				}
				damageItem(item, player);
				return true;
			} else if (target instanceof EntityAnimal) {
				if (target instanceof EntityPig) {
					behead(item, target, player, 6);
					return true;
				} else if (target instanceof EntityCow) {
					if (target instanceof EntityMooshroom)
						behead(item, target, player, 8);
					else
						behead(item, target, player, 7);
					return true;
				} else if (target instanceof EntitySheep) {
					behead(item, target, player, 9);
					return true;
				} else if (target instanceof EntityWolf) {
					behead(item, target, player, 10);
					return true;
				} else if (target instanceof EntityChicken) {
					behead(item, target, player, 12);
					return true;
				}
			} else if (target instanceof EntityVillager) {
				behead(item, target, player, 11);
				return true;
			}
		return false;
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
