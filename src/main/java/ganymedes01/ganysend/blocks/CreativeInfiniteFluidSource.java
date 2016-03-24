package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.core.utils.InventoryUtils;
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
import net.minecraftforge.fluids.IFluidContainerItem;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class CreativeInfiniteFluidSource extends InfiniteWaterSource {

	public CreativeInfiniteFluidSource() {
		super(Material.rock);
		setBlockUnbreakable();
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.CREATIVE_INFINITE_FLUID_SOURCE_NAME));
	}

	@Override
	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {
		if (player.getCurrentEquippedItem() == null)
			return;

		TileEntityInfiniteWaterSource tile = Utils.getTileEntity(world, x, y, z, TileEntityInfiniteWaterSource.class);
		if (tile == null)
			return;

		ItemStack stack = player.getCurrentEquippedItem();
		if (stack == null)
			return;

		if (FluidContainerRegistry.isEmptyContainer(stack)) {
			ItemStack filled = FluidContainerRegistry.fillFluidContainer(tile.getFluid(), stack);
			if (filled != null) {
				InventoryUtils.addToPlayerInventory(player, filled, x, y, z);
				stack.stackSize--;
				if (stack.stackSize == 0)
					stack = null;
			}
		} else if (stack.getItem() instanceof IFluidContainerItem) {
			IFluidContainerItem item = (IFluidContainerItem) stack.getItem();
			FluidStack fluid = item.getFluid(stack);
			tile.setFluid(fluid);
		} else {
			FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(stack);
			if (fluid == null) {
				if (stack.getItem() == Items.potionitem) {
					fluid = FluidRegistry.getFluidStack("potion", FluidContainerRegistry.getFluidForFilledItem(new ItemStack(Items.potionitem)).amount);
					if (fluid != null) {
						fluid.tag = new NBTTagCompound();
						fluid.tag.setInteger("potionMeta", stack.getItemDamage());
						tile.setFluid(fluid);
					}
				}
			} else
				tile.setFluid(fluid);
		}
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