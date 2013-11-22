package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.ModIDs;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
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
		super(ModIDs.EMULATOR, Material.portal);
		setHardness(0.3F);
		setCreativeTab(GanysEnd.endTab);
		setTextureName(Utils.getBlockTexture(Strings.EMULATOR_NAME));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.EMULATOR_NAME));
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
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess access, int x, int y, int z, int side) {
		if (!access.isAirBlock(x, y - 1, z)) {
			Block block = Block.blocksList[access.getBlockId(x, y - 1, z)];
			if (checkBounds(block))
				if (block.getRenderType() == 0 || block.getRenderType() == 31 || block.getRenderType() == 39)
					return block.getBlockTexture(access, x, y - 1, z, side);
		}
		return super.getBlockTexture(access, x, y, z, side);
	}

	private boolean checkBounds(Block block) {
		return block.getBlockBoundsMaxX() == maxX && block.getBlockBoundsMaxY() == maxY && block.getBlockBoundsMaxZ() == maxZ && block.getBlockBoundsMinX() == minX && block.getBlockBoundsMinY() == minY && block.getBlockBoundsMinZ() == minZ;
	}
}