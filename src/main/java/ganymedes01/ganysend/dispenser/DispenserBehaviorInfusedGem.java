package ganymedes01.ganysend.dispenser;

import ganymedes01.ganysend.ModBlocks;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.state.IBlockState;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayerFactory;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class DispenserBehaviorInfusedGem extends BehaviorDefaultDispenseItem {

	@Override
	public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
		EnumFacing facing = BlockDispenser.getFacing(source.getBlockMetadata());
		World world = source.getWorld();
		BlockPos pos = source.getBlockPos().offset(facing);
		if (world instanceof WorldServer) {
			EntityPlayer player = FakePlayerFactory.getMinecraft((WorldServer) world);
			player.setCurrentItemOrArmor(0, stack);

			IBlockState state = world.getBlockState(pos);
			if (state.getBlock() == ModBlocks.time_manipulator)
				ModBlocks.time_manipulator.onBlockActivated(world, pos, state, player, facing, 0.0F, 0.0F, 0.0F);
		}
		return stack;
	}
}