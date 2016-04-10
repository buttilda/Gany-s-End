package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.IConfigurable;
import ganymedes01.ganysend.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EndWalls extends Block implements IConfigurable {

	public EndWalls(Block block, String name) {
		super(block.getMaterial());
		setStepSound(block.stepSound);
		setUnlocalizedName(Utils.getUnlocalisedName(name));
		setCreativeTab(GanysEnd.enableDecorativeBlocks ? GanysEnd.endTab : null);
		setDefaultState(blockState.getBaseState().withProperty(BlockWall.UP, false).withProperty(BlockWall.NORTH, false).withProperty(BlockWall.EAST, false).withProperty(BlockWall.SOUTH, false).withProperty(BlockWall.WEST, false));
	}

	@Override
	public boolean isFullCube() {
		return false;
	}

	@Override
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
		boolean flag = canConnectTo(worldIn, pos.north());
		boolean flag1 = canConnectTo(worldIn, pos.south());
		boolean flag2 = canConnectTo(worldIn, pos.west());
		boolean flag3 = canConnectTo(worldIn, pos.east());
		float f = 0.25F;
		float f1 = 0.75F;
		float f2 = 0.25F;
		float f3 = 0.75F;
		float f4 = 1.0F;

		if (flag)
			f2 = 0.0F;

		if (flag1)
			f3 = 1.0F;

		if (flag2)
			f = 0.0F;

		if (flag3)
			f1 = 1.0F;

		if (flag && flag1 && !flag2 && !flag3) {
			f4 = 0.8125F;
			f = 0.3125F;
			f1 = 0.6875F;
		} else if (!flag && !flag1 && flag2 && flag3) {
			f4 = 0.8125F;
			f2 = 0.3125F;
			f3 = 0.6875F;
		}

		setBlockBounds(f, 0.0F, f2, f1, f4, f3);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
		setBlockBoundsBasedOnState(worldIn, pos);
		maxY = 1.5D;
		return super.getCollisionBoundingBox(worldIn, pos, state);
	}

	public boolean canConnectTo(IBlockAccess worldIn, BlockPos pos) {
		Block block = worldIn.getBlockState(pos).getBlock();
		return block == Blocks.barrier ? false : block != this && !(block instanceof BlockFenceGate) ? block.getMaterial().isOpaque() && block.isFullCube() ? block.getMaterial() != Material.gourd : false : true;
	}

	@Override
	public int damageDropped(IBlockState state) {
		return 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
		return side == EnumFacing.DOWN ? super.shouldSideBeRendered(worldIn, pos, side) : true;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState();
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return state.withProperty(BlockWall.UP, !worldIn.isAirBlock(pos.up())).withProperty(BlockWall.NORTH, canConnectTo(worldIn, pos.north())).withProperty(BlockWall.EAST, canConnectTo(worldIn, pos.east())).withProperty(BlockWall.SOUTH, canConnectTo(worldIn, pos.south())).withProperty(BlockWall.WEST, canConnectTo(worldIn, pos.west()));
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, BlockWall.UP, BlockWall.NORTH, BlockWall.EAST, BlockWall.WEST, BlockWall.SOUTH);
	}

	@Override
	public boolean isEnabled() {
		return GanysEnd.enableDecorativeBlocks;
	}
}