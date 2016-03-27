package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.IConfigurable;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.tileentities.TileEntityInventoryBinder;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
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

public class InventoryBinder extends BlockContainer implements IConfigurable {

	public InventoryBinder() {
		super(Material.rock);
		setHardness(10.0F);
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.INVENTORY_BINDER_NAME));
		setCreativeTab(GanysEnd.enableInventoryBinder ? GanysEnd.endTab : null);
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		if (placer != null && placer instanceof EntityPlayer) {
			TileEntityInventoryBinder tile = Utils.getTileEntity(world, pos, TileEntityInventoryBinder.class);
			tile.setPlayerProfile(((EntityPlayer) placer).getGameProfile());
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityInventoryBinder();
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return GanysEnd.enableInventoryBinder;
	}
}