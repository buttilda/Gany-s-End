package ganymedes01.ganysend.tileentities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityWeatherEffect;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.util.AxisAlignedBB;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class TileEntityEntityShifter extends TileEntityBlockShifter {

	private static ArrayList<Class<? extends Entity>> blackListedEntities = new ArrayList<Class<? extends Entity>>();

	static {
		blackListedEntities.add(EntityItemFrame.class);
		blackListedEntities.add(EntityFishHook.class);
		blackListedEntities.add(EntityFireball.class);
		blackListedEntities.add(EntityWeatherEffect.class);
	}

	@Override
	protected void teleportFromTo(int fromX, int fromY, int fromZ, int toX, int toY, int toZ) {
		List list = worldObj.selectEntitiesWithinAABB(Entity.class, AxisAlignedBB.getAABBPool().getAABB(fromX, fromY, fromZ, fromX + 1.0D, fromY + 1.0D, fromZ + 1.0D), IEntitySelector.selectAnything);
		if (!list.isEmpty()) {
			Iterator iterator = list.iterator();
			while (iterator.hasNext()) {
				Entity entity = (Entity) iterator.next();
				if (entity.worldObj == worldObj)
					for (Class<? extends Entity> clazz : blackListedEntities)
						if (clazz.isAssignableFrom(entity.getClass()))
							return;
				if (entity instanceof EntityLivingBase)
					teleportEntityLiving((EntityLivingBase) entity, toX, toY, toZ);
				else if (entity instanceof EntityItem)
					teleportEntityItem((EntityItem) entity, toX, toY, toZ);
				else {
					entity.setLocationAndAngles(toX, toY, toZ, entity.rotationYaw, entity.rotationPitch);
					entity.prevPosX = toX;
					entity.prevPosY = toY;
					entity.prevPosZ = toZ;
					if (entity.isRiding()) {
						Entity ridden = entity.ridingEntity;
						ridden.setLocationAndAngles(toX, toY, toZ, entity.ridingEntity.rotationYaw, entity.ridingEntity.rotationPitch);
						ridden.prevPosX = toX;
						ridden.prevPosY = toY;
						ridden.prevPosZ = toZ;
					}
				}
				worldObj.playSoundEffect(fromX, fromY, fromZ, "mob.endermen.portal", 1.0F, 1.0F);
			}
		}
	}

	private void teleportEntityLiving(EntityLivingBase living, int recX, int recY, int recZ) {
		living.setPositionAndUpdate(recX + 0.5D, recY, recZ + 0.5D);
		living.fallDistance = 0.0F;
	}

	private void teleportEntityItem(EntityItem item, int recX, int recY, int recZ) {
		EntityItem newEntity = new EntityItem(worldObj, recX + 0.5D, recY, recZ + 0.5D, item.getEntityItem());
		newEntity.copyDataFrom(item, true);
		newEntity.setLocationAndAngles(recX, recY, recZ, item.rotationYaw, item.rotationPitch);
		worldObj.spawnEntityInWorld(newEntity);
		item.setDead();
	}
}