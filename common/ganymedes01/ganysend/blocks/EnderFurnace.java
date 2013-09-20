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
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
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

	private final boolean isActive;
	private static boolean keepFurnaceInventory;
	@SideOnly(Side.CLIENT)
	private Icon iconSide, iconFront;

	public EnderFurnace(int id, boolean isActive) {
		super(id, Material.rock);
		setHardness(3.5F);
		this.isActive = isActive;
		if (!isActive)
			setCreativeTab(GanysEnd.endTab);
		else
			setLightValue(1.0F);
		setStepSound(soundStoneFootstep);
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.ENDER_FURNACE_NAME + "_" + (isActive ? "on" : "off")));
	}

	@Override
	public int idDropped(int id, Random rand, int fortune) {
		return ModBlocks.enderFurnace.blockID;
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
	public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
		if (!keepFurnaceInventory) {
			TileEntityEnderFurnace tile = (TileEntityEnderFurnace) world.getBlockTileEntity(x, y, z);
			if (tile != null) {
				for (int i = 0; i < tile.getSizeInventory(); ++i) {
					ItemStack stack = tile.getStackInSlot(i);
					if (stack != null)
						Utils.dropStack(world, x, y, z, stack);
				}
				world.func_96440_m(x, y, z, par5);
			}
			super.breakBlock(world, x, y, z, par5, par6);
		}
		super.breakBlock(world, x, y, z, par5, par6);
	}

	public static void updateFurnaceBlockState(boolean isActive, World world, int x, int y, int z) {
		int l = world.getBlockMetadata(x, y, z);
		TileEntity tileentity = world.getBlockTileEntity(x, y, z);
		keepFurnaceInventory = true;

		if (isActive)
			world.setBlock(x, y, z, ModBlocks.enderFurnace.blockID);
		else
			world.setBlock(x, y, z, ModBlocks.enderFurnace_off.blockID);

		keepFurnaceInventory = false;
		world.setBlockMetadataWithNotify(x, y, z, l, 2);

		if (tileentity != null) {
			tileentity.validate();
			world.setBlockTileEntity(x, y, z, tileentity);
		}
	}

	@Override
	public boolean hasComparatorInputOverride() {
		return true;
	}

	@Override
	public int getComparatorInputOverride(World world, int x, int y, int z, int side) {
		return Container.calcRedstoneFromInventory((IInventory) world.getBlockTileEntity(x, y, z));
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		setDefaultDirection(world, x, y, z);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		int l = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		if (l == 0)
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		if (l == 1)
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		if (l == 2)
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		if (l == 3)
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		if (stack.hasDisplayName())
			((TileEntityFurnace) world.getBlockTileEntity(x, y, z)).setGuiDisplayName(stack.getDisplayName());
	}

	private void setDefaultDirection(World world, int x, int y, int z) {
		if (!world.isRemote) {
			int l = world.getBlockId(x, y, z - 1);
			int i1 = world.getBlockId(x, y, z + 1);
			int j1 = world.getBlockId(x - 1, y, z);
			int k1 = world.getBlockId(x + 1, y, z);
			byte b0 = 3;

			if (Block.opaqueCubeLookup[l] && !Block.opaqueCubeLookup[i1])
				b0 = 3;
			if (Block.opaqueCubeLookup[i1] && !Block.opaqueCubeLookup[l])
				b0 = 2;
			if (Block.opaqueCubeLookup[j1] && !Block.opaqueCubeLookup[k1])
				b0 = 5;
			if (Block.opaqueCubeLookup[k1] && !Block.opaqueCubeLookup[j1])
				b0 = 4;

			world.setBlockMetadataWithNotify(x, y, z, b0, 2);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return side == 0 || side == 1 ? iconSide : side != meta ? blockIcon : iconFront;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		String textureName = Utils.getBlockTexture(Strings.ENDER_FURNACE_NAME, true);

		blockIcon = reg.registerIcon(textureName + "side");
		iconFront = reg.registerIcon(textureName + "top_" + (isActive ? "on" : "off"));
		iconSide = reg.registerIcon(textureName + "bottom");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int idPicked(World world, int x, int y, int z) {
		return ModBlocks.enderFurnace.blockID;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityEnderFurnace();
	}
}
