package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.GUIsID;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.tileentities.TileEntityEnderFurnace;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
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
	private Icon blockSide, blockBottom, blockTop;

	public EnderFurnace(int id, boolean isActive) {
		super(id, Material.rock);
		setHardness(3.5F);
		this.isActive = isActive;
		if (!isActive)
			setCreativeTab(GanysEnd.endTab);
		else
			setLightValue(1.0F);
		setStepSound(soundStoneFootstep);
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.ENDER_FURNACE_NAME));
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

	public static void updateFurnaceBlockState(boolean par0, World par1World, int par2, int par3, int par4) {
		int l = par1World.getBlockMetadata(par2, par3, par4);
		TileEntity tileentity = par1World.getBlockTileEntity(par2, par3, par4);
		keepFurnaceInventory = true;

		if (par0)
			par1World.setBlock(par2, par3, par4, ModBlocks.enderFurnace.blockID);
		else
			par1World.setBlock(par2, par3, par4, ModBlocks.enderFurnace_off.blockID);

		keepFurnaceInventory = false;
		par1World.setBlockMetadataWithNotify(par2, par3, par4, l, 2);

		if (tileentity != null) {
			tileentity.validate();
			par1World.setBlockTileEntity(par2, par3, par4, tileentity);
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
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return side == 0 ? blockBottom : (side == 1 ? blockTop : blockSide);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		String textureName = Utils.getBlockTexture(Strings.ENDER_FURNACE_NAME, true);

		blockTop = reg.registerIcon(textureName + "top_" + (isActive ? "on" : "off"));
		blockSide = reg.registerIcon(textureName + "side");
		blockBottom = reg.registerIcon(textureName + "bottom");
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
