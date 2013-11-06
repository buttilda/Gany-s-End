package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.items.ModItems;
import ganymedes01.ganysend.lib.ModIDs;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.network.PacketTypeHandler;
import ganymedes01.ganysend.network.packet.PacketTimeManipulator;
import ganymedes01.ganysend.tileentities.TileEntityTimeManipulator;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class TimeManipulator extends BlockContainer {

	public TimeManipulator() {
		super(ModIDs.TIME_MANIPULATOR_ID, Material.rock);
		setHardness(10.0F);
		setResistance(25.0F);
		setStepSound(soundWoodFootstep);
		setCreativeTab(GanysEnd.endTab);
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.TIME_MANIPULATOR_NAME));
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		if (world.provider.dimensionId != 0)
			return false;

		TileEntityTimeManipulator tile = (TileEntityTimeManipulator) world.getBlockTileEntity(x, y, z);
		if (tile != null)
			if (player.getCurrentEquippedItem() != null)
				if (player.getCurrentEquippedItem().getItem() == ModItems.infusedGem)
					if (player.getCurrentEquippedItem().getItemDamage() == 1) {
						tile.revertTime = true;
						tile.advanceTime = false;
						PacketDispatcher.sendPacketToAllPlayers(PacketTypeHandler.populatePacket(new PacketTimeManipulator(x, y, z, true, false)));
						if (!player.capabilities.isCreativeMode)
							player.inventory.getCurrentItem().stackSize--;
						return true;
					} else if (player.getCurrentEquippedItem().getItemDamage() == 0) {
						tile.revertTime = false;
						tile.advanceTime = true;
						PacketDispatcher.sendPacketToAllPlayers(PacketTypeHandler.populatePacket(new PacketTimeManipulator(x, y, z, false, true)));
						if (!player.capabilities.isCreativeMode)
							player.inventory.getCurrentItem().stackSize--;
						return true;
					}
		return false;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
		if (world.getBlockId(x, y + 1, z) == blockID)
			world.setBlockToAir(x, y + 1, z);
		if (world.getBlockId(x, y - 1, z) == blockID)
			world.setBlockToAir(x, y - 1, z);
		super.breakBlock(world, x, y, z, par5, par6);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		if (world.isAirBlock(x, y + 1, z)) {
			byte b0 = 0;
			int l = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
			if (l == 0)
				b0 = 2;
			if (l == 1)
				b0 = 3;
			if (l == 2)
				b0 = 0;
			if (l == 3)
				b0 = 1;
			world.setBlockMetadataWithNotify(x, y, z, b0, 2);
			world.setBlock(x, y + 1, z, blockID, b0 + 4, 2);
		} else {
			dropBlockAsItem(world, x, y, z, 0, 0);
			world.setBlockToAir(x, y, z);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityTimeManipulator();
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		TileEntityTimeManipulator tile = (TileEntityTimeManipulator) world.getBlockTileEntity(x, y, z);
		if (tile != null)
			if (tile.revertTime || tile.advanceTime) {
				world.spawnParticle("enchantmenttable", x + rand.nextFloat(), y + 0.5F, z + rand.nextFloat(), 0F, 3F, 0F);
				world.spawnParticle("enchantmenttable", x + rand.nextFloat(), y + 0.5F, z + rand.nextFloat(), 0F, 3F, 0F);
				world.spawnParticle("enchantmenttable", x + rand.nextFloat(), y + 0.5F, z + rand.nextFloat(), 0F, 3F, 0F);
			}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		blockIcon = reg.registerIcon("planks_spruce");
	}
}
