package ganymedes01.ganysend.blocks;

import java.util.Random;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.IConfigurable;
import ganymedes01.ganysend.ModItems;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.tileentities.TileEntityTimeManipulator;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class TimeManipulator extends BlockContainer implements IConfigurable {

	public TimeManipulator() {
		super(Material.rock);
		setHardness(10.0F);
		setResistance(25.0F);
		setStepSound(soundTypeWood);
		setCreativeTab(GanysEnd.enableTimeManipulator ? GanysEnd.endTab : null);
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.TIME_MANIPULATOR_NAME));
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		if (world.provider.getDimensionId() != 0)
			return false;

		TileEntityTimeManipulator tile = Utils.getTileEntity(world, pos, TileEntityTimeManipulator.class);
		if (tile != null)
			if (player.getCurrentEquippedItem() != null)
				if (player.getCurrentEquippedItem().getItem() == ModItems.infusedGem)
					if (player.getCurrentEquippedItem().getItemDamage() == 1) {
						tile.revertTime = true;
						tile.advanceTime = false;
						tile.sendUpdates();
						if (!player.capabilities.isCreativeMode)
							player.inventory.getCurrentItem().stackSize--;
						return true;
					} else if (player.getCurrentEquippedItem().getItemDamage() == 0) {
						tile.revertTime = false;
						tile.advanceTime = true;
						tile.sendUpdates();
						if (!player.capabilities.isCreativeMode)
							player.inventory.getCurrentItem().stackSize--;
						return true;
					}
		return false;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		if (world.getBlockState(pos.up()).getBlock() == this)
			world.setBlockToAir(pos.up());
		if (world.getBlockState(pos.down()).getBlock() == this)
			world.setBlockToAir(pos.down());
		super.breakBlock(world, pos, state);
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		if (world.getBlockState(pos.up()).getBlock().isReplaceable(world, pos.up())) {
			byte meta = 0;
			int rotation = MathHelper.floor_double(placer.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
			if (rotation == 0)
				meta = 2;
			if (rotation == 1)
				meta = 3;
			if (rotation == 2)
				meta = 0;
			if (rotation == 3)
				meta = 1;
			world.setBlockMetadataWithNotify(pos, meta, 2);
			world.setBlock(pos.up(), this, meta + 4, 2);
		} else {
			dropBlockAsItem(world, pos, 0, 0);
			world.setBlockToAir(pos);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityTimeManipulator();
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, BlockPos pos, IBlockState state, Random rand) {
		TileEntityTimeManipulator tile = Utils.getTileEntity(world, pos, TileEntityTimeManipulator.class);
		if (tile != null)
			if (tile.revertTime || tile.advanceTime) {
				world.spawnParticle("enchantmenttable", x + rand.nextFloat(), y + 0.5F, z + rand.nextFloat(), 0F, 3F, 0F);
				world.spawnParticle("enchantmenttable", x + rand.nextFloat(), y + 0.5F, z + rand.nextFloat(), 0F, 3F, 0F);
				world.spawnParticle("enchantmenttable", x + rand.nextFloat(), y + 0.5F, z + rand.nextFloat(), 0F, 3F, 0F);
			}
	}

	@Override
	public boolean isEnabled() {
		return GanysEnd.enableTimeManipulator;
	}
}