package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.ModIDs;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.tileentities.TileEntityInfiniteWaterSource;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class InfiniteWaterSource extends BlockContainer {

	protected InfiniteWaterSource() {
		super(ModIDs.INFINITE_WATER_SOURCE_ID, Material.rock);
		setHardness(3.5F);
		setCreativeTab(GanysEnd.endTab);
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.INFINITE_WATER_SOURCE_NAME));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess access, int x, int y, int z, int side) {
		return true;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass() {
		return 1;
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		blockIcon = reg.registerIcon("water_still");
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (player.getCurrentEquippedItem() == null)
			return false;
		ItemStack stack = player.getCurrentEquippedItem();
		if (FluidContainerRegistry.isEmptyContainer(stack)) {
			player.inventory.addItemStackToInventory(FluidContainerRegistry.fillFluidContainer(new FluidStack(FluidRegistry.WATER, FluidContainerRegistry.BUCKET_VOLUME), stack));
			stack.stackSize--;
			if (stack.stackSize == 0)
				stack = null;
			return true;
		} else
			return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityInfiniteWaterSource();
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		checkForHarden(world, x, y, z);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int neighbourID) {
		checkForHarden(world, x, y, z);
	}

	private void checkForHarden(World world, int X, int Y, int Z) {
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			int x = X + dir.offsetX;
			int y = Y + dir.offsetY;
			int z = Z + dir.offsetZ;
			if (world.getBlockMaterial(x, y, z) == Material.lava) {
				int meta = world.getBlockMetadata(x, y, z);
				if (meta == 0)
					world.setBlock(x, y, z, Block.obsidian.blockID);
				else if (meta <= 4)
					world.setBlock(x, y, z, Block.cobblestone.blockID);
				triggerLavaMixEffects(world, x, y, z);
			}
		}
	}

	protected void triggerLavaMixEffects(World world, int x, int y, int z) {
		world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, "random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
		for (int i = 0; i < 8; i++)
			world.spawnParticle("largesmoke", x + Math.random(), y + 1.2D, z + Math.random(), 0.0D, 0.0D, 0.0D);
	}
}