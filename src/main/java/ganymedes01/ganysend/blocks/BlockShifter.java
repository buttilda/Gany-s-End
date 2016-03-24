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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
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
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityBlockShifter();
	}

	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, Block neighbour) {
		if (world.isRemote)
			return;
		TileEntityBlockShifter tile = Utils.getTileEntity(world, pos, TileEntityBlockShifter.class);
		if (tile != null)
			tile.direction = world.isBlockIndirectlyGettingPowered(pos);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int id, float hitX, float hitY, float hitZ) {
		if (player.inventory.getCurrentItem() == null)
			return false;
		ItemStack current = player.inventory.getCurrentItem();
		if (current.getItem() == ModItems.enderTag && current.hasTagCompound())
			if (current.getTagCompound().getBoolean("Tagged")) {
				TileEntityBlockShifter tile = Utils.getTileEntity(world, x, y, z, TileEntityBlockShifter.class);
				if (tile != null) {
					int telX = current.getTagCompound().getIntArray("Position")[0];
					int telY = current.getTagCompound().getIntArray("Position")[1];
					int telZ = current.getTagCompound().getIntArray("Position")[2];
					int telDim = current.getTagCompound().getInteger("Dimension");

					if (telX == x && telY == y && telZ == z)
						return false;

					tile.receiverX = telX;
					tile.receiverY = telY;
					tile.receiverZ = telZ;
					tile.receiverDim = telDim;
					tile.tagged = true;
					world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "random.click", 0.3F, 0.6F);
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