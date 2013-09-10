package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.GUIsID;
import ganymedes01.ganysend.lib.Reference;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.tileentities.TileEntityFilteringHopper;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockHopper;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BasicFilteringHopper extends BlockHopper {
	@SideOnly(Side.CLIENT)
	protected Icon blockOutside, blockTop;
	private final Random rand = new Random();

	public BasicFilteringHopper(int id) {
		super(id);
		setHardness(3.0F);
		setResistance(8.0F);
		setStepSound(soundWoodFootstep);
		setCreativeTab(GanysEnd.endTab);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.BASIC_FILTERING_HOPPER_NAME));
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess access, int x, int y, int z) {
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axis, List list, Entity entity) {
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
		addCollisionBoxes(world, x, y, z, axis, list, entity);
		float f = 0.125F;
		setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
		addCollisionBoxes(world, x, y, z, axis, list, entity);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
		addCollisionBoxes(world, x, y, z, axis, list, entity);
		setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		addCollisionBoxes(world, x, y, z, axis, list, entity);
		setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
		addCollisionBoxes(world, x, y, z, axis, list, entity);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	private void addCollisionBoxes(World world, int x, int y, int z, AxisAlignedBB axis, List list, Entity entity) {
		AxisAlignedBB axisalignedbb1 = getCollisionBoundingBoxFromPool(world, x, y, z);
		if (axisalignedbb1 != null && axis.intersectsWith(axisalignedbb1))
			list.add(axisalignedbb1);
	}

	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
		int j1 = Facing.oppositeSide[side];
		if (j1 == 1)
			j1 = 0;
		return j1;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		TileEntityFilteringHopper tile = new TileEntityFilteringHopper();
		tile.setBasic();
		return tile;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {

	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		updateMetadata(world, x, y, z);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		if (player.isSneaking())
			return false;
		else {
			TileEntityFilteringHopper tileHopper = (TileEntityFilteringHopper) world.getBlockTileEntity(x, y, z);
			if (tileHopper != null)
				player.openGui(GanysEnd.instance, GUIsID.BASIC_FILTERING_HOPPER, world, x, y, z);
			return true;
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int side) {
		updateMetadata(world, x, y, z);
	}

	private void updateMetadata(World world, int x, int y, int z) {
		int l = world.getBlockMetadata(x, y, z);
		int i1 = getDirectionFromMetadata(l);
		boolean flag = !world.isBlockIndirectlyGettingPowered(x, y, z);
		boolean flag1 = getIsBlockNotPoweredFromMetadata(l);

		if (flag != flag1)
			world.setBlockMetadataWithNotify(x, y, z, i1 | (flag ? 0 : 8), 4);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
		TileEntityFilteringHopper tile = (TileEntityFilteringHopper) world.getBlockTileEntity(x, y, z);
		if (tile != null) {
			for (int j1 = 0; j1 < tile.getSizeInventory() + 1; ++j1) {
				ItemStack stack = tile.getStackInSlot(j1);
				if (stack != null)
					Utils.dropStack(world, x, y, z, stack);
			}
			world.func_96440_m(x, y, z, par5);
		}
		world.removeBlockTileEntity(x, y, z);
	}

	@Override
	public int getRenderType() {
		return 38;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	public static int getDirectionFromMetadata(int meta) {
		return meta & 7;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess access, int x, int y, int z, int side) {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return side == 1 ? blockTop : blockOutside;
	}

	public static boolean getIsBlockNotPoweredFromMetadata(int meta) {
		return (meta & 8) != 8;
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
	public void registerIcons(IconRegister reg) {
		blockOutside = reg.registerIcon(Utils.getBlockTexture(Strings.BASIC_FILTERING_HOPPER_NAME, true) + "outside");
		blockTop = reg.registerIcon(Utils.getBlockTexture(Strings.BASIC_FILTERING_HOPPER_NAME, true) + "top");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getItemIconName() {
		return Reference.ITEM_BLOCK_TEXTURE_PATH + Strings.BASIC_FILTERING_HOPPER_NAME;
	}
}
