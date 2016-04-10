package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.IConfigurable;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class EnderFlower extends BlockBush implements IConfigurable {

	public EnderFlower() {
		setLightLevel(0.3F);
		setStepSound(soundTypeGrass);
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.ENDER_FLOWER_NAME));
		setCreativeTab(GanysEnd.enableEnderFlower ? GanysEnd.endTab : null);
	}

	@Override
	public boolean canEntityDestroy(IBlockAccess world, BlockPos pos, Entity entity) {
		return !(entity instanceof EntityDragon);
	}

	@Override
	protected boolean canPlaceBlockOn(Block ground) {
		return ground == Blocks.end_stone;
	}

	@Override
	public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
		return canPlaceBlockAt(world, pos);
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
		return world.provider.getDimensionId() == 1 && canPlaceBlockOn(world.getBlockState(pos.down()).getBlock());
	}

	@Override
	public boolean isEnabled() {
		return GanysEnd.enableEnderFlower;
	}
}