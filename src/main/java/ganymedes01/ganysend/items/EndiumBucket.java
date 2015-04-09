package ganymedes01.ganysend.items;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.IEndiumTool;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidContainerItem;
import net.minecraftforge.fluids.IFluidHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class EndiumBucket extends Item implements IFluidContainerItem, IEndiumTool {

	public EndiumBucket() {
		setMaxStackSize(1);
		setTextureName(Utils.getItemTexture(Strings.ENDIUM_BUCKET_NAME));
		setCreativeTab(GanysEnd.enableEndiumTools ? GanysEnd.endTab : null);
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.ENDIUM_BUCKET_NAME));
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		IFluidHandler tile = Utils.getTileEntity(world, x, y, z, IFluidHandler.class);
		if (tile != null) {
			NBTTagCompound nbt = getNBT(stack);
			boolean tagged = nbt.getBoolean("Tagged");
			if (!tagged || player.isSneaking()) {
				nbt.setIntArray("Position", new int[] { x, y, z });
				nbt.setInteger("Dimension", world.provider.dimensionId);
				nbt.setBoolean("Tagged", true);
				player.swingItem();
			}
		}
		return false;
	}

	private NBTTagCompound getNBT(ItemStack stack) {
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		return stack.getTagCompound();
	}

	private IFluidHandler getFluidHandler(ItemStack stack) {
		return getFluidHandler(getNBT(stack));
	}

	private IFluidHandler getFluidHandler(NBTTagCompound nbt) {
		if (nbt.getBoolean("Tagged")) {
			int[] pos = nbt.getIntArray("Position");
			World world = DimensionManager.getWorld(nbt.getInteger("Dimension"));
			if (world != null)
				return Utils.getTileEntity(world, pos[0], pos[1], pos[2], IFluidHandler.class);
		}
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack, int pass) {
		return stack.hasTagCompound() && stack.stackTagCompound.hasKey("Tagged");
	}

	@Override
	public FluidStack getFluid(ItemStack container) {
		IFluidHandler tile = getFluidHandler(container);
		if (tile != null) {
			FluidTankInfo[] infos = tile.getTankInfo(ForgeDirection.UNKNOWN);
			return infos != null && infos.length > 0 ? infos[0].fluid : null;
		}
		return null;
	}

	@Override
	public int getCapacity(ItemStack container) {
		IFluidHandler tile = getFluidHandler(container);
		if (tile != null) {
			FluidTankInfo[] infos = tile.getTankInfo(ForgeDirection.UNKNOWN);
			return infos != null && infos.length > 0 ? infos[0].capacity : null;
		}
		return 0;
	}

	@Override
	public int fill(ItemStack container, FluidStack resource, boolean doFill) {
		IFluidHandler tile = getFluidHandler(container);
		return tile != null ? tile.fill(ForgeDirection.UNKNOWN, resource, doFill) : 0;
	}

	@Override
	public FluidStack drain(ItemStack container, int maxDrain, boolean doDrain) {
		IFluidHandler tile = getFluidHandler(container);
		return tile != null ? tile.drain(ForgeDirection.UNKNOWN, maxDrain, doDrain) : null;
	}
}