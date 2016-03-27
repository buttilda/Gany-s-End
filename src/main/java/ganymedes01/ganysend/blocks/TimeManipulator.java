package ganymedes01.ganysend.blocks;

import java.util.Random;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.IConfigurable;
import ganymedes01.ganysend.ModItems;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.tileentities.TileEntityTimeManipulator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
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
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		if (world.provider.dimensionId != 0)
			return false;

		TileEntityTimeManipulator tile = Utils.getTileEntity(world, x, y, z, TileEntityTimeManipulator.class);
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
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		if (world.getBlock(x, y + 1, z) == this)
			world.setBlockToAir(x, y + 1, z);
		if (world.getBlock(x, y - 1, z) == this)
			world.setBlockToAir(x, y - 1, z);
		super.breakBlock(world, x, y, z, block, meta);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		if (world.isAirBlock(x, y + 1, z)) {
			byte meta = 0;
			int rotation = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
			if (rotation == 0)
				meta = 2;
			if (rotation == 1)
				meta = 3;
			if (rotation == 2)
				meta = 0;
			if (rotation == 3)
				meta = 1;
			world.setBlockMetadataWithNotify(x, y, z, meta, 2);
			world.setBlock(x, y + 1, z, this, meta + 4, 2);
		} else {
			dropBlockAsItem(world, x, y, z, 0, 0);
			world.setBlockToAir(x, y, z);
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
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		TileEntityTimeManipulator tile = Utils.getTileEntity(world, x, y, z, TileEntityTimeManipulator.class);
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