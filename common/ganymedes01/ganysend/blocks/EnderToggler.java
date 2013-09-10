package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
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
	private Icon blockSide, blockBottom, blockTop;

	public EnderToggler(int id) {
		super(id, Material.iron);
		setHardness(1.5F);
		setResistance(10.0F);
		setCreativeTab(GanysEnd.endTab);
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.ENDER_TOGGLER_NAME));
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		if (world.isBlockIndirectlyGettingPowered(x, y, z))
			world.setBlock(x, y, z, ModBlocks.enderToggler_air.blockID, 0, 2);
		else
			world.notifyBlockOfNeighborChange(x, y + 1, z, ModBlocks.enderToggler.blockID);
		if (world.getBlockId(x, y - 1, z) == ModBlocks.enderToggler_air.blockID) {
			world.setBlock(x, y, z, ModBlocks.enderToggler_air.blockID, 0, 2);
			world.notifyBlockOfNeighborChange(x, y + 1, z, ModBlocks.enderToggler_air.blockID);
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int id) {
		if (world.isBlockIndirectlyGettingPowered(x, y, z)) {
			world.setBlock(x, y, z, ModBlocks.enderToggler_air.blockID, 0, 2);
			world.notifyBlockOfNeighborChange(x, y + 1, z, ModBlocks.enderToggler_air.blockID);
		}
		if (world.getBlockId(x, y - 1, z) == ModBlocks.enderToggler_air.blockID) {
			world.setBlock(x, y, z, ModBlocks.enderToggler_air.blockID, 0, 2);
			world.notifyBlockOfNeighborChange(x, y + 1, z, ModBlocks.enderToggler_air.blockID);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return side == 0 ? blockBottom : (side == 1 ? blockTop : blockSide);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		blockSide = reg.registerIcon(Utils.getBlockTexture(Strings.ENDER_TOGGLER_NAME, true) + "side");
		blockBottom = reg.registerIcon(Utils.getBlockTexture(Strings.ENDER_TOGGLER_NAME, true) + "bottom");
		blockTop = reg.registerIcon(Utils.getBlockTexture(Strings.ENDER_TOGGLER_NAME, true) + "top");
	}
}
