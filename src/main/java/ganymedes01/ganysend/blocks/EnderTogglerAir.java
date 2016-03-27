package ganymedes01.ganysend.blocks;

import java.util.Random;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.IConfigurable;
import ganymedes01.ganysend.ModBlocks;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class EnderTogglerAir extends Block implements IConfigurable {

	public EnderTogglerAir() {
		super(Material.iron);
		setBlockUnbreakable();
		setResistance(6000000.0F);
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.ENDER_TOGGLER_AIR_NAME));
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, BlockPos pos) {
		return null;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		setBlockBounds(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, BlockPos pos, Random rand) {
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
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		super.onBlockAdded(world, pos, state);
		if (GanysEnd.togglerShouldMakeSound)
			world.playSoundEffect(pos, "mob.endermen.portal", 1.0F, 1.0F);
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		super.breakBlock(world, pos, state);
		if (GanysEnd.togglerShouldMakeSound)
			world.playSoundEffect(pos, "mob.endermen.portal", 1.0F, 1.0F);
	}

	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighbour) {
		if (world.isBlockIndirectlyGettingPowered(pos) <= 0)
			world.setBlockState(pos, ModBlocks.enderToggler.getDefaultState(), 2);
	}

	@Override
	public boolean isEnabled() {
		return GanysEnd.enableEnderToggler;
	}
}