package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.ModBlocks;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class EnderToggler extends Block {

	@SideOnly(Side.CLIENT)
	private IIcon blockSide, blockBottom, blockTop;

	public EnderToggler() {
		super(Material.iron);
		setHardness(1.5F);
		setResistance(10.0F);
		setBlockName(Utils.getUnlocalizedName(Strings.ENDER_TOGGLER_NAME));
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
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return side == 0 ? blockBottom : side == 1 ? blockTop : blockSide;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		blockSide = reg.registerIcon(Utils.getBlockTexture(Strings.ENDER_TOGGLER_NAME) + "_side");
		blockBottom = reg.registerIcon(Utils.getBlockTexture(Strings.ENDER_TOGGLER_NAME) + "_bottom");
		blockTop = reg.registerIcon(Utils.getBlockTexture(Strings.ENDER_TOGGLER_NAME) + "_top");
	}
}