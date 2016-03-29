package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.IConfigurable;
import ganymedes01.ganysend.core.utils.InventoryUtils;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.GUIsID;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.tileentities.TileEntityVoidCrate;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class VoidCrate extends BlockContainer implements IConfigurable {

	public VoidCrate() {
		super(Material.iron);
		setHardness(2.0F);
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.VOID_CRATE_NAME));
		setCreativeTab(GanysEnd.enableVoidCrate ? GanysEnd.endTab : null);
	}

	@Override
	public int getRenderType() {
		return 3;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		IItemHandler itemHandler = world.getTileEntity(pos).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		for (int i = 0; i < itemHandler.getSlots(); i++) {
			ItemStack stack = itemHandler.getStackInSlot(i);
			InventoryUtils.dropStack(world, pos, stack);
		}
		super.breakBlock(world, pos, state);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		if (player.isSneaking())
			return false;
		else {
			TileEntityVoidCrate tile = Utils.getTileEntity(world, pos, TileEntityVoidCrate.class);
			if (tile != null)
				player.openGui(GanysEnd.instance, GUIsID.VOID_CRATE, world, pos.getX(), pos.getY(), pos.getZ());
			return true;
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityVoidCrate();
	}

	@Override
	public boolean isEnabled() {
		return GanysEnd.enableVoidCrate;
	}
}