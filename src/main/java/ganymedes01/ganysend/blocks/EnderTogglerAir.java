package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.AxisAlignedBB;
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

public class EnderTogglerAir extends Block {

	public EnderTogglerAir() {
		super(Material.iron);
		setHardness(-1.0F);
		setResistance(6000000.0F);
		setBlockName(Utils.getUnlocalizedName(Strings.ENDER_TOGGLER_AIR_NAME));
		setBlockTextureName(Utils.getBlockTexture(Strings.ENDER_TOGGLER_AIR_NAME));
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public int getRenderBlockPass() {
		return 0;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		setBlockBounds(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		int max = 5;
		for (int j = 1; j <= max; j++) {
			double d0 = x + j * 0.20D;
			double d1 = y;
			double d2 = z + 0.5D;
			world.spawnParticle("portal", d0, d1, d2, 0.0D, 0.0D, 0.0D);
		}
		for (int j = 1; j <= max; j++) {
			double d0 = x + 0.5D;
			double d1 = y;
			double d2 = z + j * 0.20D;
			world.spawnParticle("portal", d0, d1, d2, 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		if (GanysEnd.togglerShouldMakeSound)
			world.playSoundEffect(x, y, z, "mob.endermen.portal", 1.0F, 1.0F);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		super.breakBlock(world, x, y, z, block, meta);
		if (GanysEnd.togglerShouldMakeSound)
			world.playSoundEffect(x, y, z, "mob.endermen.portal", 1.0F, 1.0F);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block neighbour) {
		if (!world.isBlockIndirectlyGettingPowered(x, y, z))
			world.setBlock(x, y, z, ModBlocks.enderToggler, 0, 2);
	}
}