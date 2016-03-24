package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.IConfigurable;
import ganymedes01.ganysend.ModBlocks;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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
	public void onBlockAdded(World world, int x, int y, int z) {
		if (world.isBlockIndirectlyGettingPowered(x, y, z))
			world.setBlock(x, y, z, ModBlocks.enderToggler_air, 0, 2);
		else
			world.notifyBlockOfNeighborChange(x, y + 1, z, ModBlocks.enderToggler);
		if (world.getBlock(x, y - 1, z) == ModBlocks.enderToggler_air) {
			world.setBlock(x, y, z, ModBlocks.enderToggler_air, 0, 2);
			world.notifyBlockOfNeighborChange(x, y + 1, z, ModBlocks.enderToggler_air);
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block neighbour) {
		if (world.isBlockIndirectlyGettingPowered(x, y, z)) {
			world.setBlock(x, y, z, ModBlocks.enderToggler_air, 0, 2);
			world.notifyBlockOfNeighborChange(x, y + 1, z, ModBlocks.enderToggler_air);
		}
		if (world.getBlock(x, y - 1, z) == ModBlocks.enderToggler_air) {
			world.setBlock(x, y, z, ModBlocks.enderToggler_air, 0, 2);
			world.notifyBlockOfNeighborChange(x, y + 1, z, ModBlocks.enderToggler_air);
		}
	}

	@Override
	public boolean isEnabled() {
		return GanysEnd.enableEnderToggler;
	}
}