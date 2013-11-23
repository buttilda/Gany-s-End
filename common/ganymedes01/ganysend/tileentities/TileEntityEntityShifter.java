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

	public TileEntityEntityShifter() {
		super();
	}

	@Override
	public void updateEntity() {
		if (worldObj.isRemote)
			return;
		if (tagged) {
			int telX, telY, telZ, telDim, recX, recY, recZ, recDim;
			if (worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) {
				telX = xCoord;
				telY = yCoord + 1;
				telZ = zCoord;
				telDim = worldObj.provider.dimensionId;

				recX = receiverX;
				recY = receiverY + 1;
				recZ = receiverZ;
				recDim = receiverDim;
			} else {
				telX = receiverX;
				telY = receiverY + 1;
				telZ = receiverZ;
				telDim = receiverDim;

				recX = xCoord;
				recY = yCoord + 1;
				recZ = zCoord;
				recDim = worldObj.provider.dimensionId;
			}
			if (recDim != telDim)
				return;
			List list = worldObj.selectEntitiesWithinAABB(Entity.class, AxisAlignedBB.getAABBPool().getAABB(telX, telY, telZ, telX + 1.0D, telY + 1.0D, telZ + 1.0D), IEntitySelector.selectAnything);
			if (!list.isEmpty()) {
				Iterator iterator = list.iterator();
				while (iterator.hasNext()) {
					Entity entity = (Entity) iterator.next();
					if (entity.worldObj == worldObj)
						for (Class<? extends Entity> clazz : blackListedEntities)
							if (clazz.isAssignableFrom(entity.getClass()))
								return;
					if (entity instanceof EntityLivingBase)
						teleportEntityLiving((EntityLivingBase) entity, recX, recY, recZ);
					else if (entity instanceof EntityItem)
						teleportEntityItem((EntityItem) entity, recX, recY, recZ);
					else {
						entity.setLocationAndAngles(recX, recY, recZ, entity.rotationYaw, entity.rotationPitch);
						entity.prevPosX = recX;
						entity.prevPosY = recY;
						entity.prevPosZ = recZ;
						if (entity.isRiding()) {
							Entity ridden = entity.ridingEntity;
							ridden.setLocationAndAngles(recX, recY, recZ, entity.ridingEntity.rotationYaw, entity.ridingEntity.rotationPitch);
							ridden.prevPosX = recX;
							ridden.prevPosY = recY;
							ridden.prevPosZ = recZ;
						}
					}
					worldObj.playSoundEffect(telX, telY, telZ, "mob.endermen.portal", 1.0F, 1.0F);
				}
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