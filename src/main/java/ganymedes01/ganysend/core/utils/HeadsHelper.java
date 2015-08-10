package ganymedes01.ganysend.core.utils;

import ganymedes01.ganysend.GanysEnd;

import java.lang.reflect.Method;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;

import com.mojang.authlib.GameProfile;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class HeadsHelper {

	public static ItemStack getHeadfromEntity(EntityLivingBase target) {
		if (target.isChild())
			return null;

		if (GanysEnd.isHeadcrumbsLoaded)
			try {
				Class<?> cls = Class.forName("ganymedes01.headcrumbs.utils.HeadUtils");
				Method m = cls.getMethod("getHeadfromEntity", EntityLivingBase.class);
				return (ItemStack) m.invoke(null, target);
			} catch (Exception e) {
				return null;
			}

		if (target instanceof EntityCreeper)
			return new ItemStack(Items.skull, 1, 4);
		if (target instanceof EntitySkeleton) {
			int type = ((EntitySkeleton) target).getSkeletonType();
			if (type == 1) // Wither
				return new ItemStack(Items.skull, 1, 1);
			else if (type == 0) // Normal
				return new ItemStack(Items.skull, 1, 0);
		}
		if (target instanceof EntityZombie && !(target instanceof EntityPigZombie))
			return new ItemStack(Items.skull, 1, 2);
		if (target instanceof EntityPlayer)
			return createHeadFor((EntityPlayer) target);

		return null;
	}

	public static ItemStack createHeadFor(EntityPlayer player) {
		return createHeadFor(player.getGameProfile());
	}

	public static ItemStack createHeadFor(GameProfile profile) {
		ItemStack stack = new ItemStack(Items.skull, 1, 3);
		stack.setTagCompound(new NBTTagCompound());
		NBTTagCompound profileData = new NBTTagCompound();
		NBTUtil.func_152460_a(profileData, profile);
		stack.getTagCompound().setTag("SkullOwner", profileData);

		return stack;
	}
}