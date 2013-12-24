package ganymedes01.ganysend.core.utils;

import ganymedes01.ganysend.items.ModItems;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityIronGolem;
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
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class HeadsHelper {

	public static boolean useTwilightForrestMobs = false;
	public static String[] skullTypes = new String[] { "blaze", "enderman", "pigman", "player", "spider", "caveSpider", "pig", "cow", "mooshroom", "sheep", "wolf", "villager", "chicken", "witch", "zombieVillager", "ironGolem", "squid", "wither", "bunny", "penguin", "bighorn", "wildDeer",
	"wildBoar", "redcap", "druid", "hedgeSpider", "ghast" };

	public static final ItemStack getHeadfromEntity(EntityLivingBase target) {
		if (target.isChild())
			return null;

		if (useTwilightForrestMobs) {
			ItemStack head = getTFMobHead(EntityList.getEntityString(target));
			if (head != null)
				return head;
		}

		if (target instanceof EntityMob) {
			if (target instanceof EntityCreeper)
				return new ItemStack(Item.skull.itemID, 1, 4);
			else if (target instanceof EntitySkeleton) {
				int type = ((EntitySkeleton) target).getSkeletonType();
				if (type == 1) // Wither
					return new ItemStack(Item.skull, 1, 1);
				else if (type == 0) // Normal
					return new ItemStack(Item.skull, 1, 0);
			} else if (target instanceof EntityZombie) {
				if (target instanceof EntityPigZombie)
					return new ItemStack(ModItems.itemNewSkull, 1, 2);
				else if (((EntityZombie) target).isVillager())
					return new ItemStack(ModItems.itemNewSkull, 1, 14);
				else
					return new ItemStack(Item.skull, 1, 2);
			} else if (target instanceof EntityEnderman)
				return new ItemStack(ModItems.itemNewSkull, 1, 1);
			else if (target instanceof EntityBlaze)
				return new ItemStack(ModItems.itemNewSkull, 1, 0);
			else if (target instanceof EntitySpider) {
				if (target instanceof EntityCaveSpider)
					return new ItemStack(ModItems.itemNewSkull, 1, 5);
				else
					return new ItemStack(ModItems.itemNewSkull, 1, 4);
			} else if (target instanceof EntityWitch)
				return new ItemStack(ModItems.itemNewSkull, 1, 13);
			else if (target instanceof EntityWither)
				return new ItemStack(ModItems.itemNewSkull, 1, 17);
		} else if (target instanceof EntityPlayer) {
			ItemStack stack = new ItemStack(ModItems.itemNewSkull, 1, 3);
			stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setString("SkullOwner", ((EntityPlayer) target).username);
			return stack;
		} else if (target instanceof EntityAnimal) {
			if (target instanceof EntityPig)
				return new ItemStack(ModItems.itemNewSkull, 1, 6);
			else if (target instanceof EntityCow) {
				if (target instanceof EntityMooshroom)
					return new ItemStack(ModItems.itemNewSkull, 1, 8);
				else
					return new ItemStack(ModItems.itemNewSkull, 1, 7);
			} else if (target instanceof EntitySheep)
				return new ItemStack(ModItems.itemNewSkull, 1, 9);
			else if (target instanceof EntityWolf)
				return new ItemStack(ModItems.itemNewSkull, 1, 10);
			else if (target instanceof EntityChicken)
				return new ItemStack(ModItems.itemNewSkull, 1, 12);
		} else if (target instanceof EntityVillager)
			return new ItemStack(ModItems.itemNewSkull, 1, 11);
		else if (target instanceof EntityIronGolem)
			return new ItemStack(ModItems.itemNewSkull, 1, 15);
		else if (target instanceof EntitySquid)
			return new ItemStack(ModItems.itemNewSkull, 1, 16);
		else if (target instanceof EntityGhast)
			return new ItemStack(ModItems.itemNewSkull, 1, 26);

		return null;
	}

	private static ItemStack getTFMobHead(String mobName) {
		if (mobName.equals("TwilightForest.Forest Bunny"))
			return new ItemStack(ModItems.itemNewSkull, 1, 18);
		else if (mobName.equals("TwilightForest.Penguin"))
			return new ItemStack(ModItems.itemNewSkull, 1, 19);
		else if (mobName.equals("TwilightForest.Bighorn Sheep"))
			return new ItemStack(ModItems.itemNewSkull, 1, 20);
		else if (mobName.equals("TwilightForest.Wild Deer"))
			return new ItemStack(ModItems.itemNewSkull, 1, 21);
		else if (mobName.equals("TwilightForest.Wild Boar"))
			return new ItemStack(ModItems.itemNewSkull, 1, 22);
		else if (mobName.equals("TwilightForest.Redcap") || mobName.equals("TwilightForest.Redcap Sapper"))
			return new ItemStack(ModItems.itemNewSkull, 1, 23);
		else if (mobName.equals("TwilightForest.Skeleton Druid"))
			return new ItemStack(ModItems.itemNewSkull, 1, 24);
		else if (mobName.equals("TwilightForest.Hedge Spider"))
			return new ItemStack(ModItems.itemNewSkull, 1, 25);

		return null;
	}
}