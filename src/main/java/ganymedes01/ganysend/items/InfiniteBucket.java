package ganymedes01.ganysend.items;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.IConfigurable;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCauldron;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemSimpleFoiled;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
		setTextureName(Utils.getItemTexture(Strings.INFINITE_BUCKET_NAME));
		setCreativeTab(GanysEnd.enableInfiniteBucket ? GanysEnd.endTab : null);
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.INFINITE_BUCKET_NAME));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.uncommon;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if (side < 0 || side > 5)
			return false;
		ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[side];

		IFluidHandler tile = Utils.getTileEntity(world, x, y, z, IFluidHandler.class);
		if (tile != null) {
			if (!world.isRemote)
				tile.fill(dir, new FluidStack(FluidRegistry.WATER, FluidContainerRegistry.BUCKET_VOLUME), true);
			return true;
		} else if (world.getBlock(x, y, z) == Blocks.cauldron) {
			int filled = BlockCauldron.func_150027_b(world.getBlockMetadata(x, y, z));
			if (filled < 3) {
				world.setBlockMetadataWithNotify(x, y, z, 3, 2);
				world.func_147453_f(x, y, z, Blocks.cauldron);
				return true;
			}
		}

		Block block = world.getBlock(x, y, z);
		if (block != Blocks.vine && block != Blocks.tallgrass && block != Blocks.deadbush && !block.isReplaceable(world, x, y, z)) {
			x += dir.offsetX;
			y += dir.offsetY;
			z += dir.offsetZ;
		}
		if (player == null || player.canPlayerEdit(x, y, z, side, stack)) {
			if (!world.isRemote)
				((ItemBucket) Items.water_bucket).tryPlaceContainedLiquid(world, x, y, z);
			return true;
		}

		return false;
	}

	@Override
	public boolean isEnabled() {
		return GanysEnd.enableInfiniteBucket;
	}
}