package ganymedes01.ganysend.blocks;

import java.util.ArrayList;
import java.util.List;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.IConfigurable;
import ganymedes01.ganysend.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class EndiumBlock extends Block implements IConfigurable {

	public EndiumBlock() {
		super(Material.cloth);
		setHardness(0.2F);
		setResistance(0.5F);
		setStepSound(soundTypeMetal);
		setCreativeTab(GanysEnd.enableEndium ? GanysEnd.endTab : null);
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		list.add(new ItemStack(ModItems.endiumIngot, 9));
		return list;
	}

	@Override
	public boolean isBeaconBase(IBlockAccess world, BlockPos pos, BlockPos beacon) {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return GanysEnd.enableEndium;
	}
}