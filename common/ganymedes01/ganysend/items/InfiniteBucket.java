package ganymedes01.ganysend.items;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.ModIDs;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemSimpleFoiled;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class InfiniteBucket extends ItemSimpleFoiled {

	public InfiniteBucket() {
		super(ModIDs.INFINITE_BUCKET_ID);
		setMaxStackSize(1);
		setCreativeTab(GanysEnd.endTab);
		setTextureName(Utils.getItemTexture(Strings.INFINITE_BUCKET_NAME));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.INFINITE_BUCKET_NAME));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.uncommon;
	}

	@Override
	public boolean hasContainerItem() {
		return true;
	}

	@Override
	public ItemStack getContainerItemStack(ItemStack stack) {
		return new ItemStack(this);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		MovingObjectPosition movingobjectposition = getMovingObjectPositionFromPlayer(world, player, false);
		if (movingobjectposition == null)
			return stack;
		else if (movingobjectposition.typeOfHit == EnumMovingObjectType.TILE) {
			int x = movingobjectposition.blockX;
			int y = movingobjectposition.blockY;
			int z = movingobjectposition.blockZ;
			if (!world.canMineBlock(player, x, y, z))
				return stack;
			else {
				switch (movingobjectposition.sideHit) {
					case 0:
						y--;
						break;
					case 1:
						y++;
						break;
					case 2:
						z--;
						break;
					case 3:
						z++;
						break;
					case 4:
						x--;
						break;
					case 5:
						x++;
						break;
				}

				tryPlaceWater(world, x, y, z);
			}
		}
		return stack;
	}

	public static void tryPlaceWater(World world, int x, int y, int z) {
		Material material = world.getBlockMaterial(x, y, z);

		if (!world.isAirBlock(x, y, z) && material.isSolid())
			return;
		else if (world.provider.dimensionId == -1) {
			world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, "random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
			for (int i = 0; i < 8; i++)
				world.spawnParticle("largesmoke", x + Math.random(), y + Math.random(), z + Math.random(), 0.0D, 0.0D, 0.0D);
		} else {
			if (!world.isRemote && !material.isSolid() && !material.isLiquid())
				world.destroyBlock(x, y, z, true);

			world.setBlock(x, y, z, Block.waterMoving.blockID, 0, 3);
		}
	}
}