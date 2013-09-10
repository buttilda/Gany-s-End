package ganymedes01.ganysend.core.handlers;

import ganymedes01.ganysend.core.utils.CustomDamageSources;
import ganymedes01.ganysend.items.ModItems;

import java.util.Random;

import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class MobDeathEvent {

	@ForgeSubscribe
	public void silverfishDeath(LivingDeathEvent event) {
		if (!event.entity.worldObj.isRemote)
			if (event.source != CustomDamageSources.beheading) {
				Random rand = new Random();
				if (rand.nextInt(100) == 50)
					if (event.entity instanceof EntityMob) {
						if (event.entity instanceof EntityPigZombie)
							event.entity.entityDropItem(new ItemStack(ModItems.itemNewSkull, 1, 2), 0.0F);
						else if (event.entity instanceof EntityEnderman)
							event.entity.entityDropItem(new ItemStack(ModItems.itemNewSkull, 1, 1), 0.0F);
						else if (event.entity instanceof EntityBlaze)
							event.entity.entityDropItem(new ItemStack(ModItems.itemNewSkull, 1, 0), 0.0F);
						else if (event.entity instanceof EntitySpider)
							if (event.entity instanceof EntityCaveSpider)
								event.entity.entityDropItem(new ItemStack(ModItems.itemNewSkull, 1, 5), 0.0F);
							else
								event.entity.entityDropItem(new ItemStack(ModItems.itemNewSkull, 1, 4), 0.0F);
					} else if (event.entity instanceof EntityAnimal) {
						if (!((EntityAnimal) event.entity).isChild())
							if (event.entity instanceof EntityPig)
								event.entity.entityDropItem(new ItemStack(ModItems.itemNewSkull, 1, 6), 0.0F);
							else if (event.entity instanceof EntityCow)
								if (event.entity instanceof EntityMooshroom)
									event.entity.entityDropItem(new ItemStack(ModItems.itemNewSkull, 1, 8), 0.0F);
								else
									event.entity.entityDropItem(new ItemStack(ModItems.itemNewSkull, 1, 7), 0.0F);
							else if (event.entity instanceof EntitySheep)
								event.entity.entityDropItem(new ItemStack(ModItems.itemNewSkull, 1, 9), 0.0F);
							else if (event.entity instanceof EntityWolf)
								event.entity.entityDropItem(new ItemStack(ModItems.itemNewSkull, 1, 10), 0.0F);
							else if (event.entity instanceof EntityChicken)
								event.entity.entityDropItem(new ItemStack(ModItems.itemNewSkull, 1, 12), 0.0F);
					} else if (event.entity instanceof EntityVillager)
						if (!((EntityVillager) event.entity).isChild())
							event.entity.entityDropItem(new ItemStack(ModItems.itemNewSkull, 1, 11), 0.0F);
			}
	}
}
