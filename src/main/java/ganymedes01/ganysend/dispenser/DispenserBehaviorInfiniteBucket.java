package ganymedes01.ganysend.dispenser;

import ganymedes01.ganysend.items.InfiniteBucket;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class DispenserBehaviorInfiniteBucket extends BehaviorDefaultDispenseItem {

	@Override
	public ItemStack dispenseStack(IBlockSource block, ItemStack stack) {
		EnumFacing enumfacing = BlockDispenser.func_149937_b(block.getBlockMetadata());
		World world = block.getWorld();
		int x = block.getXInt() + enumfacing.getFrontOffsetX();
		int y = block.getYInt() + enumfacing.getFrontOffsetY();
		int z = block.getZInt() + enumfacing.getFrontOffsetZ();

		InfiniteBucket.tryPlaceWater(world, x, y, z);

		return stack;
	}
}