package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.GUIsID;
import ganymedes01.ganysend.lib.ModIDs;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.tileentities.TileEntityEnderFurnace;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
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
	private Icon[] icons;

	public EnderFurnace() {
		super(ModIDs.ENDER_FURNACE_ID, Material.rock);
		setHardness(5.0F);
		setCreativeTab(GanysEnd.endTab);
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.ENDER_FURNACE_NAME));
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		if (!world.isRemote) {
			int minZ = world.getBlockId(x, y, z - 1);
			int maxZ = world.getBlockId(x, y, z + 1);
			int minX = world.getBlockId(x - 1, y, z);
			int maxX = world.getBlockId(x + 1, y, z);
			byte dir = 3;

			if (Block.opaqueCubeLookup[maxZ] && !Block.opaqueCubeLookup[minZ])
				dir = 2;
			if (Block.opaqueCubeLookup[minX] && !Block.opaqueCubeLookup[maxX])
				dir = 5;
			if (Block.opaqueCubeLookup[maxX] && !Block.opaqueCubeLookup[minX])
				dir = 4;

			world.setBlockMetadataWithNotify(x, y, z, dir, 2);
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
			TileEntityEnderFurnace tile = (TileEntityEnderFurnace) world.getBlockTileEntity(x, y, z);
			if (tile != null)
				player.openGui(GanysEnd.instance, GUIsID.ENDER_FURNACE, world, x, y, z);
			return true;
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityEnderFurnace();
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int id, int meta) {
		TileEntityEnderFurnace tile = (TileEntityEnderFurnace) world.getBlockTileEntity(x, y, z);
		if (tile != null) {
			for (int i = 0; i < tile.getSizeInventory(); i++) {
				ItemStack stack = tile.getStackInSlot(i);
				if (stack != null)
					Utils.dropStack(world, x, y, z, stack);
			}
			world.func_96440_m(x, y, z, id);
		}
		world.removeBlockTileEntity(x, y, z);
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (tile instanceof TileEntityEnderFurnace)
			return ((TileEntityEnderFurnace) tile).lightLevel;
		return 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess access, int x, int y, int z, int side) {
		if (side == 0 || side == 1)
			return icons[0];
		if (side != access.getBlockMetadata(x, y, z))
			return icons[1];

		TileEntity tile = access.getBlockTileEntity(x, y, z);
		if (tile instanceof TileEntityEnderFurnace)
			return ((TileEntityEnderFurnace) tile).lightLevel == 15 ? icons[2] : icons[3];

		return super.getBlockTexture(access, x, y, z, side);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (tile instanceof TileEntityEnderFurnace && ((TileEntityEnderFurnace) tile).lightLevel > 0)
			for (int i = 0; i < 5; i++)
				Block.enderChest.randomDisplayTick(world, x, y, z, rand);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
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
	public void registerIcons(IconRegister reg) {
		icons = new Icon[4];
		for (int i = 0; i < icons.length; i++)
			icons[i] = reg.registerIcon(Utils.getBlockTexture(Strings.ENDER_FURNACE_NAME + i));
	}
}