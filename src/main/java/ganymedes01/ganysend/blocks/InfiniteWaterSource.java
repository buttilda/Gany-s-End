package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.IConfigurable;
import ganymedes01.ganysend.core.utils.InventoryUtils;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.tileentities.TileEntityInfiniteWaterSource;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class InfiniteWaterSource extends BlockContainer implements IConfigurable {

	public InfiniteWaterSource() {
		this(Material.rock);
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.INFINITE_WATER_SOURCE_NAME));
	}

	protected InfiniteWaterSource(Material material) {
		super(material);
		setHardness(3.5F);
		setCreativeTab(GanysEnd.enableInfiniteWaterSource ? GanysEnd.endTab : null);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (player.getCurrentEquippedItem() == null)
			return false;

		TileEntityInfiniteWaterSource tile = Utils.getTileEntity(world, pos, TileEntityInfiniteWaterSource.class);
		if (tile == null)
			return false;

		ItemStack stack = player.getCurrentEquippedItem();
		if (FluidContainerRegistry.isEmptyContainer(stack)) {
			ItemStack filled = FluidContainerRegistry.fillFluidContainer(tile.getFluid(), stack);
			if (filled != null) {
				InventoryUtils.addToPlayerInventory(player, filled, pos);
				stack.stackSize--;
				if (stack.stackSize == 0)
					stack = null;
				return true;
			}
		}

		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityInfiniteWaterSource();
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		checkForHarden(world, pos);
	}

	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighbour) {
		checkForHarden(world, pos);
	}

	private void checkForHarden(World world, BlockPos pos) {
		for (EnumFacing side : EnumFacing.VALUES) {
			BlockPos sidePos = pos.offset(side);
			if (world.getBlockState(sidePos).getBlock().getMaterial() == Material.lava) {
				int meta = world.getBlockMetadata(sidePos);
				if (meta == 0)
					world.setBlock(sidePos, Blocks.obsidian);
				else if (meta <= 4)
					world.setBlock(sidePos, Blocks.cobblestone);
				triggerLavaMixEffects(world, sidePos);
			}
		}
	}

	private void triggerLavaMixEffects(World world, BlockPos pos) {
		world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, "random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
		for (int i = 0; i < 8; i++)
			world.spawnParticle("largesmoke", x + Math.random(), y + 1.2D, z + Math.random(), 0.0D, 0.0D, 0.0D);
	}

	@Override
	public boolean isEnabled() {
		return GanysEnd.enableInfiniteWaterSource;
	}
}