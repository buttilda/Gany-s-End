package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class Emulator extends Block {

	public Emulator() {
		super(Material.portal);
		setHardness(0.3F);
		setBlockName(Utils.getUnlocalisedName(Strings.EMULATOR_NAME));
		setCreativeTab(GanysEnd.enableEmulator ? GanysEnd.endTab : null);
		setBlockTextureName(Utils.getBlockTexture(Strings.EMULATOR_NAME));
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess access, int x, int y, int z, int side) {
		return true;
	}

	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
		return Facing.oppositeSide[side];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		try {
			int meta = world.getBlockMetadata(x, y, z);
			x += Facing.offsetsXForSide[meta];
			y += Facing.offsetsYForSide[meta];
			z += Facing.offsetsZForSide[meta];

			if (!world.isAirBlock(x, y, z)) {
				Block block = world.getBlock(x, y, z);
				if (checkBounds(block))
					if (block.getRenderType() == 0 || block.getRenderType() == 31 || block.getRenderType() == 39)
						return block.getIcon(world, x, y, z, side);
			}
			return super.getIcon(world, x, y, z, side);
		} catch (StackOverflowError e) {
			return super.getIcon(world, x, y, z, side);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
		try {
			int meta = world.getBlockMetadata(x, y, z);
			x += Facing.offsetsXForSide[meta];
			y += Facing.offsetsYForSide[meta];
			z += Facing.offsetsZForSide[meta];

			if (!world.isAirBlock(x, y, z)) {
				Block block = world.getBlock(x, y, z);
				if (checkBounds(block))
					if (block.getRenderType() == 0 || block.getRenderType() == 31 || block.getRenderType() == 39)
						return block.colorMultiplier(world, x, y, z);
			}
			return super.colorMultiplier(world, x, y, z);
		} catch (StackOverflowError e) {
			return super.colorMultiplier(world, x, y, z);
		}
	}

	private boolean checkBounds(Block block) {
		return block.getBlockBoundsMaxX() == maxX && block.getBlockBoundsMaxY() == maxY && block.getBlockBoundsMaxZ() == maxZ && block.getBlockBoundsMinX() == minX && block.getBlockBoundsMinY() == minY && block.getBlockBoundsMinZ() == minZ;
	}
}