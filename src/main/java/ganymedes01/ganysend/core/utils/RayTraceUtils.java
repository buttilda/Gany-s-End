package ganymedes01.ganysend.core.utils;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class RayTraceUtils {

	public static MovingObjectPosition getRayTraceFromBlock(World world, int x, int y, int z, EntityPlayer player, AxisAlignedBB... boxes) {
		double reach = 0;
		if (player instanceof EntityPlayerMP)
			reach = ((EntityPlayerMP) player).theItemInWorldManager.getBlockReachDistance();
		else
			reach = Minecraft.getMinecraft().playerController.getBlockReachDistance();

		Vec3 origin = Vec3.createVectorHelper(player.posX, player.posY, player.posZ);
		if (player.worldObj.isRemote)
			origin.yCoord += player.getEyeHeight() - player.getDefaultEyeHeight();
		else {
			origin.yCoord += player.getEyeHeight();
			if (player instanceof EntityPlayerMP && player.isSneaking())
				origin.yCoord -= 0.08;
		}

		Vec3 lookVec = player.getLook(1.0F);
		Vec3 direction = origin.addVector(lookVec.xCoord * reach, lookVec.yCoord * reach, lookVec.zCoord * reach);

		return getRayTraceFromBlock(world, x, y, z, origin, direction, boxes);
	}

	public static MovingObjectPosition getRayTraceFromBlock(World world, int x, int y, int z, Vec3 origin, Vec3 direction, AxisAlignedBB... boxes) {
		ArrayList<MovingObjectPosition> mops = new ArrayList<MovingObjectPosition>();

		for (int i = 0; i < boxes.length; i++) {
			MovingObjectPosition mop = getRayTraceFromBox(world, x, y, z, origin, direction, boxes[i]);
			if (mop != null) {
				mop.subHit = i;
				mops.add(mop);
			}
		}

		double minLengthSquared = Double.POSITIVE_INFINITY;
		int minIndex = -1;

		for (int i = 0; i < mops.size(); i++) {
			MovingObjectPosition hit = mops.get(i);
			if (hit == null)
				continue;

			double lengthSquared = hit.hitVec.squareDistanceTo(origin);
			if (lengthSquared < minLengthSquared) {
				minLengthSquared = lengthSquared;
				minIndex = i;
			}
		}

		return minIndex < 0 ? null : mops.get(minIndex);
	}

	public static MovingObjectPosition getRayTraceFromBox(World world, int x, int y, int z, Vec3 origin, Vec3 direction, AxisAlignedBB box) {
		if (box == null)
			return null;

		direction = direction.addVector(-x, -y, -z);
		origin = origin.addVector(-x, -y, -z);

		Vec3 vec32 = origin.getIntermediateWithXValue(direction, box.minX);
		Vec3 vec33 = origin.getIntermediateWithXValue(direction, box.maxX);
		Vec3 vec34 = origin.getIntermediateWithYValue(direction, box.minY);
		Vec3 vec35 = origin.getIntermediateWithYValue(direction, box.maxY);
		Vec3 vec36 = origin.getIntermediateWithZValue(direction, box.minZ);
		Vec3 vec37 = origin.getIntermediateWithZValue(direction, box.maxZ);

		if (!isVecInsideYZBoundsOfBox(vec32, box))
			vec32 = null;
		if (!isVecInsideYZBoundsOfBox(vec33, box))
			vec33 = null;
		if (!isVecInsideXZBoundsOfBox(vec34, box))
			vec34 = null;
		if (!isVecInsideXZBoundsOfBox(vec35, box))
			vec35 = null;
		if (!isVecInsideXYBoundsOfBox(vec36, box))
			vec36 = null;
		if (!isVecInsideXYBoundsOfBox(vec37, box))
			vec37 = null;

		Vec3 vec38 = null;

		if (vec32 != null && (vec38 == null || origin.squareDistanceTo(vec32) < origin.squareDistanceTo(vec38)))
			vec38 = vec32;
		if (vec33 != null && (vec38 == null || origin.squareDistanceTo(vec33) < origin.squareDistanceTo(vec38)))
			vec38 = vec33;
		if (vec34 != null && (vec38 == null || origin.squareDistanceTo(vec34) < origin.squareDistanceTo(vec38)))
			vec38 = vec34;
		if (vec35 != null && (vec38 == null || origin.squareDistanceTo(vec35) < origin.squareDistanceTo(vec38)))
			vec38 = vec35;
		if (vec36 != null && (vec38 == null || origin.squareDistanceTo(vec36) < origin.squareDistanceTo(vec38)))
			vec38 = vec36;
		if (vec37 != null && (vec38 == null || origin.squareDistanceTo(vec37) < origin.squareDistanceTo(vec38)))
			vec38 = vec37;

		if (vec38 == null)
			return null;
		else {
			byte b0 = -1;

			if (vec38 == vec32)
				b0 = 4;

			if (vec38 == vec33)
				b0 = 5;

			if (vec38 == vec34)
				b0 = 0;

			if (vec38 == vec35)
				b0 = 1;

			if (vec38 == vec36)
				b0 = 2;

			if (vec38 == vec37)
				b0 = 3;

			return new MovingObjectPosition(x, y, z, b0, vec38.addVector(x, y, z));
		}
	}

	private static boolean isVecInsideYZBoundsOfBox(Vec3 vec, AxisAlignedBB box) {
		if (vec == null)
			return false;
		return vec.yCoord >= box.minY && vec.yCoord <= box.maxY && vec.zCoord >= box.minZ && vec.zCoord <= box.maxZ;
	}

	private static boolean isVecInsideXZBoundsOfBox(Vec3 vec, AxisAlignedBB box) {
		if (vec == null)
			return false;
		return vec.xCoord >= box.minX && vec.xCoord <= box.maxX && vec.zCoord >= box.minZ && vec.zCoord <= box.maxZ;
	}

	private static boolean isVecInsideXYBoundsOfBox(Vec3 vec, AxisAlignedBB box) {
		if (vec == null)
			return false;
		return vec.xCoord >= box.minX && vec.xCoord <= box.maxX && vec.yCoord >= box.minY && vec.yCoord <= box.maxY;
	}
}