package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.IConfigurable;
import ganymedes01.ganysend.core.utils.InventoryUtils;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.GUIsID;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.tileentities.TileEntityVoidCrate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class VoidCrate extends BlockContainer implements IConfigurable {

	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	public VoidCrate() {
		super(Material.iron);
		setHardness(2.0F);
		setBlockName(Utils.getUnlocalisedName(Strings.VOID_CRATE_NAME));
		setCreativeTab(GanysEnd.enableVoidCrate ? GanysEnd.endTab : null);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		InventoryUtils.dropInventoryContents(world.getTileEntity(x, y, z));
		super.breakBlock(world, x, y, z, block, meta);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		if (player.isSneaking())
			return false;
		else {
			TileEntityVoidCrate tile = Utils.getTileEntity(world, x, y, z, TileEntityVoidCrate.class);
			if (tile != null)
				player.openGui(GanysEnd.instance, GUIsID.VOID_CRATE, world, x, y, z);
			return true;
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityVoidCrate();
	}

	@Override
	public boolean isEnabled() {
		return GanysEnd.enableVoidCrate;
	}
}