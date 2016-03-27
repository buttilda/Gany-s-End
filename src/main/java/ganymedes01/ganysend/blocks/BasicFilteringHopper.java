package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.IConfigurable;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.GUIsID;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.tileentities.TileEntityFilteringHopper;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class BasicFilteringHopper extends BlockHopper implements IConfigurable {

	public BasicFilteringHopper() {
		setHardness(3.0F);
		setResistance(8.0F);
		setStepSound(soundTypeWood);
		setCreativeTab(GanysEnd.enableHoppers ? GanysEnd.endTab : null);
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.BASIC_FILTERING_HOPPER_NAME));
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		TileEntityFilteringHopper tile = new TileEntityFilteringHopper(1);
		tile.setBasic();
		return tile;
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		if (player.isSneaking())
			return false;
		else {
			TileEntityFilteringHopper tile = Utils.getTileEntity(world, pos, TileEntityFilteringHopper.class);
			if (tile != null)
				player.openGui(GanysEnd.instance, GUIsID.BASIC_FILTERING_HOPPER, world, pos.getX(), pos.getY(), pos.getZ());
			return true;
		}
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileEntity tile = world.getTileEntity(pos);

		if (tile instanceof IInventory) {
			InventoryHelper.dropInventoryItems(world, pos, (IInventory) tile);
			world.updateComparatorOutputLevel(pos, this);
		}

		super.breakBlock(world, pos, state);
	}

	@Override
	public boolean isEnabled() {
		return GanysEnd.enableHoppers;
	}
}