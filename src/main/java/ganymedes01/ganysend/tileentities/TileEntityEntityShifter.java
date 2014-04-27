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
	@SuppressWarnings("rawtypes")
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
				else
					teleportEntityGeneral(entity, toX, toY, toZ);
				worldObj.playSoundEffect(fromX, fromY, fromZ, "mob.endermen.portal", 1.0F, 1.0F);
			}
		}
	}

	private void teleportEntityLiving(EntityLivingBase living, int toX, int toY, int toZ) {
		living.setPositionAndUpdate(toX + 0.5D, toY, toZ + 0.5D);
		living.fallDistance = 0.0F;
	}

	private void teleportEntityItem(EntityItem item, int toX, int toY, int toZ) {
		EntityItem newEntity = new EntityItem(worldObj, toX + 0.5D, toY, toZ + 0.5D, item.getEntityItem());
		newEntity.copyDataFrom(item, true);
		newEntity.setLocationAndAngles(toX, toY, toZ, item.rotationYaw, item.rotationPitch);
		worldObj.spawnEntityInWorld(newEntity);
		item.setDead();
	}

	private void teleportEntityGeneral(Entity entity, int toX, int toY, int toZ) {
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
}