package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.tileentities.TileEntityInfiniteWaterSource;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class CreativeInfiniteFluidSource extends InfiniteWaterSource {

	protected CreativeInfiniteFluidSource() {
		super(Material.rock);
		setBlockName(Utils.getUnlocalizedName(Strings.CREATIVE_INFINITE_FLUID_SOURCE_NAME));
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (player.getCurrentEquippedItem() == null)
			return false;

		TileEntityInfiniteWaterSource tile = Utils.getTileEntity(world, x, y, z, TileEntityInfiniteWaterSource.class);
		if (tile == null)
			return false;

		ItemStack stack = player.getCurrentEquippedItem();
		if (FluidContainerRegistry.isEmptyContainer(stack)) {
			player.inventory.addItemStackToInventory(FluidContainerRegistry.fillFluidContainer(tile.getFluid(), stack));
			stack.stackSize--;
			if (stack.stackSize == 0)
				stack = null;
			return true;
		} else {
			FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(stack);
			if (fluid == null) {
				fluid = FluidRegistry.getFluidStack("potion", FluidContainerRegistry.getFluidForFilledItem(new ItemStack(Items.potionitem)).amount);
				if (fluid != null) {
					fluid.tag = new NBTTagCompound();
					fluid.tag.setInteger("potionMeta", stack.getItemDamage());
				}
			}
			if (fluid != null) {
				tile.setFluid(fluid);
				return true;
			}
		}
		return false;
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		TileEntityInfiniteWaterSource tile = Utils.getTileEntity(world, x, y, z, TileEntityInfiniteWaterSource.class);
		if (tile == null)
			return;

		tile.setFluid(null);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block neighbour) {
	}
}