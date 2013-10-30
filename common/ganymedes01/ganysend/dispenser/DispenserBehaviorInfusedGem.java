package ganymedes01.ganysend.dispenser;

import ganymedes01.ganysend.blocks.ModBlocks;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.FakePlayerFactory;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class DispenserBehaviorInfusedGem extends BehaviorDefaultDispenseItem {

	@Override
	public ItemStack dispenseStack(IBlockSource block, ItemStack stack) {
		EnumFacing enumfacing = BlockDispenser.getFacing(block.getBlockMetadata());
		World world = block.getWorld();
		int x = block.getXInt() + enumfacing.getFrontOffsetX();
		int y = block.getYInt() + enumfacing.getFrontOffsetY();
		int z = block.getZInt() + enumfacing.getFrontOffsetZ();

		EntityPlayer player = FakePlayerFactory.getMinecraft(world);
		player.setCurrentItemOrArmor(0, stack);

		if (world.getBlockId(x, y, z) == ModBlocks.timeManipulator.blockID)
			ModBlocks.timeManipulator.onBlockActivated(world, x, y, z, player, 0, 0.0F, 0.0F, 0.0F);

		return stack;
	}
}