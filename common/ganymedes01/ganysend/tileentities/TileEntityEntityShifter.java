package ganymedes01.ganysend.tileentities;

import java.util.Iterator;
import java.util.List;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;

public class TileEntityEntityShifter extends TileEntityBlockShifter {

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
							EnderTeleportEvent event = new EnderTeleportEvent((EntityLivingBase) entity, recX + 0.5D, recY, recZ + 0.5D, 0.0F);
							if (!MinecraftForge.EVENT_BUS.post(event)) {
								if (entity.isRiding())
									entity.mountEntity((Entity) null);
								((EntityLivingBase) entity).setPositionAndUpdate(event.targetX, event.targetY, event.targetZ);
								entity.fallDistance = 0.0F;
								worldObj.playSoundEffect(telX, telY, telZ, "mob.endermen.portal", 1.0F, 1.0F);
								return;
							}
						} else if (entity instanceof EntityItem) {
							EntityItem newEntity = new EntityItem(worldObj, recX + 0.5D, recY, recZ + 0.5D, ((EntityItem) entity).getEntityItem());
							worldObj.spawnEntityInWorld(newEntity);
							entity.setDead();
							worldObj.playSoundEffect(telX, telY, telZ, "mob.endermen.portal", 1.0F, 1.0F);
							return;
						}
				}
			}
		}
	}
}
