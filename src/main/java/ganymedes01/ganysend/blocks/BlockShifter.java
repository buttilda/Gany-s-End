package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.IConfigurable;
import ganymedes01.ganysend.ModItems;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.tileentities.TileEntityBlockShifter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
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

public class BlockShifter extends BlockContainer implements IConfigurable {

	public BlockShifter() {
		super(Material.iron);
		setHardness(1.5F);
		setResistance(10.0F);
		setCreativeTab(GanysEnd.enableShifters ? GanysEnd.endTab : null);
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.BLOCK_SHIFTER_NAME));
	}

	@Override
	public int getRenderType() {
		return 3;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityBlockShifter();
	}

	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighbour) {
		if (world.isRemote)
			return;
		TileEntityBlockShifter tile = Utils.getTileEntity(world, pos, TileEntityBlockShifter.class);
		if (tile != null)
			tile.direction = world.isBlockIndirectlyGettingPowered(pos) > 0;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (player.inventory.getCurrentItem() == null)
			return false;
		ItemStack current = player.inventory.getCurrentItem();
		if (current.getItem() == ModItems.ender_tag && current.hasTagCompound())
			if (current.getTagCompound().getBoolean("Tagged")) {
				TileEntityBlockShifter tile = Utils.getTileEntity(world, pos, TileEntityBlockShifter.class);
				if (tile != null) {
					int telX = current.getTagCompound().getIntArray("Position")[0];
					int telY = current.getTagCompound().getIntArray("Position")[1];
					int telZ = current.getTagCompound().getIntArray("Position")[2];
					BlockPos tel = new BlockPos(telX, telY, telZ);
					int telDim = current.getTagCompound().getInteger("Dimension");

					if (telX == pos.getX() && telY == pos.getY() && telZ == pos.getZ())
						return false;

					tile.receiver = tel;
					tile.receiverDim = telDim;
					tile.tagged = true;
					world.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "random.click", 0.3F, 0.6F);
					return true;
				}
			}
		return false;
	}

	@Override
	public boolean isEnabled() {
		return GanysEnd.enableShifters;
	}
}