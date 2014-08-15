package ganymedes01.ganysend.dispenser;

import ganymedes01.ganysend.ModItems;
import ganymedes01.ganysend.core.utils.Utils;
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

		ModItems.infiniteBucket.onItemUse(stack, Utils.getPlayer(world), world, x, y, z, enumfacing.ordinal(), 0, 0, 0);

		return stack;
	}
}