package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.GUIsID;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.tileentities.TileEntityEnderFurnace;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class EnderFurnace extends BlockContainer {

	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	public EnderFurnace() {
		super(Material.rock);
		setHardness(5.0F);
		setCreativeTab(GanysEnd.endTab);
		setBlockName(Utils.getUnlocalizedName(Strings.ENDER_FURNACE_NAME));
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		if (!world.isRemote) {
			Block block = world.getBlock(x, y, z - 1);
			Block block1 = world.getBlock(x, y, z + 1);
			Block block2 = world.getBlock(x - 1, y, z);
			Block block3 = world.getBlock(x + 1, y, z);

			byte b0 = 3;

			if (block1.func_149730_j() && !block.func_149730_j())
				b0 = 2;
			if (block2.func_149730_j() && !block3.func_149730_j())
				b0 = 5;
			if (block3.func_149730_j() && !block2.func_149730_j())
				b0 = 4;

			world.setBlockMetadataWithNotify(x, y, z, b0, 2);
		}
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		switch (MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3) {
			case 0:
				world.setBlockMetadataWithNotify(x, y, z, 2, 2);
				break;
			case 1:
				world.setBlockMetadataWithNotify(x, y, z, 5, 2);
				break;
			case 2:
				world.setBlockMetadataWithNotify(x, y, z, 3, 2);
				break;
			case 3:
				world.setBlockMetadataWithNotify(x, y, z, 4, 2);
				break;
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		if (player.isSneaking())
			return false;
		else {
			TileEntityEnderFurnace tile = Utils.getTileEntity(world, x, y, z, TileEntityEnderFurnace.class);
			if (tile != null)
				player.openGui(GanysEnd.instance, GUIsID.ENDER_FURNACE, world, x, y, z);
			return true;
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityEnderFurnace();
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		Utils.dropInventoryContents(world.getTileEntity(x, y, z));
		super.breakBlock(world, x, y, z, block, meta);
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		TileEntityEnderFurnace tile = Utils.getTileEntity(world, x, y, z, TileEntityEnderFurnace.class);
		if (tile != null)
			return tile.lightLevel;
		return 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		if (side == 0 || side == 1)
			return icons[0];
		if (side != world.getBlockMetadata(x, y, z))
			return icons[1];

		TileEntityEnderFurnace tile = Utils.getTileEntity(world, x, y, z, TileEntityEnderFurnace.class);
		if (tile != null)
			return tile.lightLevel == 15 ? icons[2] : icons[3];

		return super.getIcon(world, x, y, z, side);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		TileEntityEnderFurnace tile = Utils.getTileEntity(world, x, y, z, TileEntityEnderFurnace.class);
		if (tile != null && tile.lightLevel > 0)
			for (int i = 0; i < 5; i++)
				Blocks.ender_chest.randomDisplayTick(world, x, y, z, rand);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		meta = 3;
		if (side == 0 || side == 1)
			return icons[0];
		else if (side != meta)
			return icons[1];
		else
			return icons[3];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		icons = new IIcon[4];
		for (int i = 0; i < icons.length; i++)
			icons[i] = reg.registerIcon(Utils.getBlockTexture(Strings.ENDER_FURNACE_NAME + i));
	}
}