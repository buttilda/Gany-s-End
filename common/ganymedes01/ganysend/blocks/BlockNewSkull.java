package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.items.ModItems;
import ganymedes01.ganysend.lib.ModIDs;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.tileentities.TileEntityBlockNewSkull;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class BlockNewSkull extends BlockContainer {

	public BlockNewSkull() {
		super(ModIDs.BLOCK_NEW_SKULL_ID, Material.circuits);
		setHardness(1.0F);
		setTextureName("skull");
		setStepSound(soundStoneFootstep);
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.BLOCK_NEW_SKULL_NAME));
	}

	@Override
	public int getRenderType() {
		return RenderingRegistry.getNextAvailableRenderId();
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess access, int x, int y, int z) {
		int meta = access.getBlockMetadata(x, y, z) & 7;
		switch (meta) {
			case 1:
			default:
				setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
				break;
			case 2:
				setBlockBounds(0.25F, 0.25F, 0.5F, 0.75F, 0.75F, 1.0F);
				break;
			case 3:
				setBlockBounds(0.25F, 0.25F, 0.0F, 0.75F, 0.75F, 0.5F);
				break;
			case 4:
				setBlockBounds(0.5F, 0.25F, 0.25F, 1.0F, 0.75F, 0.75F);
				break;
			case 5:
				setBlockBounds(0.0F, 0.25F, 0.25F, 0.5F, 0.75F, 0.75F);
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		setBlockBoundsBasedOnState(world, x, y, z);
		return super.getCollisionBoundingBoxFromPool(world, x, y, z);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		int l = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 2.5D) & 3;
		world.setBlockMetadataWithNotify(x, y, z, l, 2);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityBlockNewSkull();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int idPicked(World world, int x, int y, int z) {
		return ModItems.itemNewSkull.itemID;
	}

	@Override
	public int getDamageValue(World world, int x, int y, int z) {
		TileEntity tileentity = world.getBlockTileEntity(x, y, z);
		return tileentity != null && tileentity instanceof TileEntityBlockNewSkull ? ((TileEntityBlockNewSkull) tileentity).getSkullType() : super.getDamageValue(world, x, y, z);
	}

	@Override
	public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player) {
		if (!player.capabilities.isCreativeMode) {
			ArrayList<ItemStack> drops = getBlockDropped(world, x, y, z, meta, 0);
			if (ForgeEventFactory.fireBlockHarvesting(drops, world, this, x, y, z, meta, 0, 1.0F, false, player) > 0.0F)
				for (ItemStack stack : drops)
					Utils.dropStack(world, x, y, z, stack);
		}
	}

	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int meta, int fortune) {
		ArrayList<ItemStack> drops = new ArrayList<ItemStack>();

		TileEntityBlockNewSkull tile = (TileEntityBlockNewSkull) world.getBlockTileEntity(x, y, z);
		if (tile == null)
			return drops;
		ItemStack itemstack = new ItemStack(ModItems.itemNewSkull, 1, tile.getSkullType());

		if (tile.getSkullType() == 3 && tile.getExtraType() != null && tile.getExtraType().length() > 0) {
			itemstack.setTagCompound(new NBTTagCompound());
			itemstack.getTagCompound().setString("SkullOwner", tile.getExtraType());
		}
		drops.add(itemstack);

		return drops;
	}

	@Override
	public int quantityDropped(Random rand) {
		return 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return Block.slowSand.getBlockTextureFromSide(side);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getItemIconName() {
		return getTextureName() + "_" + ItemSkull.field_94587_a[0];
	}
}