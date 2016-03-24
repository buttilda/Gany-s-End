package ganymedes01.ganysend.dispenser;

import ganymedes01.ganysend.ModItems;
import ganymedes01.ganysend.core.utils.Utils;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
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
	public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
		EnumFacing facing = BlockDispenser.getFacing(source.getBlockMetadata());
		World world = source.getWorld();
		BlockPos pos = source.getBlockPos().offset(facing);

		ModItems.infiniteBucket.onItemUse(stack, Utils.getPlayer(world), world, pos, facing, 0, 0, 0);

		return stack;
	}
}