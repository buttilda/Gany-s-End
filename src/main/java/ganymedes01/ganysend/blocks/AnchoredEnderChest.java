package ganymedes01.ganysend.blocks;

import java.util.Random;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.tileentities.TileEntityAnchoredEnderChest;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class AnchoredEnderChest extends InventoryBinder {

	private static final PropertyDirection VARIANTS = BlockFurnace.FACING;

	public AnchoredEnderChest() {
		setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.ANCHORED_ENDER_CHEST_NAME));
		setCreativeTab(GanysEnd.enableAnchoredEnderChest ? GanysEnd.endTab : null);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote && !world.getBlockState(pos.up()).getBlock().isNormalCube()) {
			TileEntityAnchoredEnderChest tile = Utils.getTileEntity(world, pos, TileEntityAnchoredEnderChest.class);
			if (tile != null && tile.getPlayerInventory() != null)
				player.displayGUIChest(tile);
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityAnchoredEnderChest();
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(Blocks.obsidian);
	}

	@Override
	public int quantityDropped(Random rand) {
		return 8;
	}

	@Override
	protected boolean canSilkHarvest() {
		return true;
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return getDefaultState().withProperty(VARIANTS, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		super.onBlockPlacedBy(world, pos, state, placer, stack);
		Blocks.ender_chest.onBlockPlacedBy(world, pos, state, placer, stack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, BlockPos pos, IBlockState state, Random rand) {
		Blocks.ender_chest.randomDisplayTick(world, pos, state, rand);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IBlockState getStateForEntityRender(IBlockState state) {
		return getDefaultState().withProperty(VARIANTS, EnumFacing.SOUTH);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.getFront(meta);

		if (enumfacing.getAxis() == EnumFacing.Axis.Y)
			enumfacing = EnumFacing.NORTH;

		return getDefaultState().withProperty(VARIANTS, enumfacing);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(VARIANTS).getIndex();
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { VARIANTS });
	}

	@Override
	public boolean isEnabled() {
		return GanysEnd.enableAnchoredEnderChest;
	}
}