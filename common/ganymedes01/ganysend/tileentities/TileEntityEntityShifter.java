package ganymedes01.ganysend.tileentities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityMinecartMobSpawner;
import net.minecraft.entity.effect.EntityWeatherEffect;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.entity.item.EntityMinecartContainer;
import net.minecraft.entity.item.EntityMinecartFurnace;
import net.minecraft.entity.item.EntityMinecartHopper;
import net.minecraft.entity.item.EntityMinecartTNT;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

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
						if (entity instanceof EntityLivingBase) {
							teleportEntityLiving((EntityLivingBase) entity, recX, recY, recZ);
							worldObj.playSoundEffect(telX, telY, telZ, "mob.endermen.portal", 1.0F, 1.0F);
						} else if (entity instanceof EntityItem) {
							teleportEntityItem((EntityItem) entity, recX, recY, recZ);
							worldObj.playSoundEffect(telX, telY, telZ, "mob.endermen.portal", 1.0F, 1.0F);
						} else if (entity instanceof EntityMinecart) {
							teleportEntityMinecart((EntityMinecart) entity, recX, recY, recZ);
							worldObj.playSoundEffect(telX, telY, telZ, "mob.endermen.portal", 1.0F, 1.0F);
						} else
							try {
								for (Class<? extends Entity> clazz : blackListedEntities)
									if (clazz.isAssignableFrom(entity.getClass()))
										return;

								Entity newEntity = entity.getClass().getConstructor(World.class).newInstance(worldObj);

								NBTTagCompound data = new NBTTagCompound();
								entity.writeToNBT(data);
								newEntity.readFromNBT(data);

								newEntity.setPosition(recX, recY + 1.0F, recZ);

								worldObj.spawnEntityInWorld(newEntity);
								entity.setDead();

								worldObj.playSoundEffect(telX, telY, telZ, "mob.endermen.portal", 1.0F, 1.0F);
							} catch (Exception e) {
								blackListedEntities.add(entity.getClass());
							}
				}
			}
		}
	}

	private void teleportEntityLiving(EntityLivingBase living, int recX, int recY, int recZ) {
		if (living.isRiding())
			living.mountEntity((Entity) null);
		living.setPositionAndUpdate(recX + 0.5D, recY, recZ + 0.5D);
		living.fallDistance = 0.0F;
	}

	private void teleportEntityItem(EntityItem item, int recX, int recY, int recZ) {
		EntityItem newEntity = new EntityItem(worldObj, recX + 0.5D, recY, recZ + 0.5D, item.getEntityItem());
		worldObj.spawnEntityInWorld(newEntity);
		item.setDead();
	}

	private void teleportEntityMinecart(EntityMinecart minecart, int recX, int recY, int recZ) {
		NBTTagCompound data = new NBTTagCompound();
		minecart.writeToNBT(data);
		EntityMinecart newMinecart = EntityMinecart.createMinecart(worldObj, recX, recY, recZ, getMinecartType(minecart));
		newMinecart.readFromNBT(data);
		newMinecart.setPosition(recX, recY, recZ);
		worldObj.spawnEntityInWorld(newMinecart);

		if (minecart instanceof EntityMinecartContainer)
			emptyMinecartContainer((EntityMinecartContainer) minecart);
		if (minecart.riddenByEntity != null && minecart.riddenByEntity instanceof EntityLivingBase)
			teleportEntityLiving((EntityLivingBase) minecart.riddenByEntity, recX, recY, recZ);

		minecart.setDead();
	}

	private int getMinecartType(Entity minecart) {
		if (minecart instanceof EntityMinecartChest)
			return 1;
		else if (minecart instanceof EntityMinecartFurnace)
			return 2;
		else if (minecart instanceof EntityMinecartTNT)
			return 3;
		else if (minecart instanceof EntityMinecartMobSpawner)
			return 4;
		else if (minecart instanceof EntityMinecartHopper)
			return 5;
		else
			return 0;
	}

	private void emptyMinecartContainer(EntityMinecartContainer minecart) {
		for (int i = 0; i < minecart.getSizeInventory(); i++)
			minecart.setInventorySlotContents(i, null);
	}
}
