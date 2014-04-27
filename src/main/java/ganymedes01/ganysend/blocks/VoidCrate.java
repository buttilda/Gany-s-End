package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.GUIsID;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.tileentities.TileEntityVoidCrate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class VoidCrate extends BlockContainer {

	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	public VoidCrate() {
		super(Material.iron);
		setHardness(2.0F);
		setCreativeTab(GanysEnd.endTab);
		setBlockName(Utils.getUnlocalizedName(Strings.VOID_CRATE_NAME));
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		Utils.dropInventoryContents(world.getTileEntity(x, y, z));
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
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return side <= 1 ? icons[1] : icons[0];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		icons = new IIcon[2];
		for (int i = 0; i < 2; i++)
			icons[i] = reg.registerIcon(Utils.getBlockTexture(Strings.VOID_CRATE_NAME) + i);
	}
}