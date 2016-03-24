package ganymedes01.ganysend.items;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.IConfigurable;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemSimpleFoiled;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class InfiniteBucket extends ItemSimpleFoiled implements IConfigurable {

	public InfiniteBucket() {
		setMaxStackSize(1);
		setContainerItem(this);
		setCreativeTab(GanysEnd.enableInfiniteBucket ? GanysEnd.endTab : null);
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.INFINITE_BUCKET_NAME));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.UNCOMMON;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		IFluidHandler tile = Utils.getTileEntity(world, pos, IFluidHandler.class);
		if (tile != null) {
			if (!world.isRemote)
				tile.fill(side, new FluidStack(FluidRegistry.WATER, FluidContainerRegistry.BUCKET_VOLUME), true);
			return true;
		} else if (world.getBlockState(pos).getBlock() == Blocks.cauldron) {
			Blocks.cauldron.setWaterLevel(world, pos, world.getBlockState(pos), 3);
			return true;
		}

		Block block = world.getBlockState(pos).getBlock();
		if (block != Blocks.vine && block != Blocks.tallgrass && block != Blocks.deadbush && !block.isReplaceable(world, pos))
			pos = pos.offset(side);
		if (player == null || player.canPlayerEdit(pos, side, stack)) {
			if (!world.isRemote)
				((ItemBucket) Items.water_bucket).tryPlaceContainedLiquid(world, pos);
			return true;
		}

		return false;
	}

	@Override
	public boolean isEnabled() {
		return GanysEnd.enableInfiniteBucket;
	}
}