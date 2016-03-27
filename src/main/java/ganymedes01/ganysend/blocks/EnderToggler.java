package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.IConfigurable;
import ganymedes01.ganysend.ModBlocks;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class EnderToggler extends Block implements IConfigurable {

	public EnderToggler() {
		super(Material.iron);
		setHardness(1.5F);
		setResistance(10.0F);
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.ENDER_TOGGLER_NAME));
		setCreativeTab(GanysEnd.enableEnderToggler ? GanysEnd.endTab : null);
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		if (world.isBlockIndirectlyGettingPowered(pos) > 0)
			world.setBlockState(pos, ModBlocks.enderToggler_air.getDefaultState(), 2);
		else
			world.notifyNeighborsOfStateChange(pos.up(), ModBlocks.enderToggler);
		if (world.getBlockState(pos.down()).getBlock() == ModBlocks.enderToggler_air) {
			world.setBlockState(pos, ModBlocks.enderToggler_air.getDefaultState(), 2);
			world.notifyNeighborsOfStateChange(pos.up(), ModBlocks.enderToggler_air);
		}
	}

	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighbourBlock) {
		if (world.isBlockIndirectlyGettingPowered(pos) > 0) {
			world.setBlockState(pos, ModBlocks.enderToggler_air.getDefaultState(), 2);
			world.notifyNeighborsOfStateChange(pos.up(), ModBlocks.enderToggler_air);
		}
		if (world.getBlockState(pos.down()).getBlock() == ModBlocks.enderToggler_air) {
			world.setBlockState(pos, ModBlocks.enderToggler_air.getDefaultState(), 2);
			world.notifyNeighborsOfStateChange(pos.up(), ModBlocks.enderToggler_air);
		}
	}

	@Override
	public boolean isEnabled() {
		return GanysEnd.enableEnderToggler;
	}
}