package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.items.ModItems;
import ganymedes01.ganysend.lib.ModIDs;
import ganymedes01.ganysend.lib.RenderIDs;
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
import net.minecraft.util.Icon;
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

	@SideOnly(Side.CLIENT)
	private Icon[] bottomIcons, topIcons;
	@SideOnly(Side.CLIENT)
	private Icon surface;

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
			world.setBlock(x, y + 1, z, blockID, meta + 4, 2);
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
		return RenderIDs.TIME_MANIPULATOR;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		if (meta > 0)
			return surface;
		if (side < 4)
			return bottomIcons[side];
		else
			return topIcons[side - 4];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		bottomIcons = new Icon[4];
		bottomIcons[0] = reg.registerIcon(Utils.getBlockTexture(Strings.TIME_MANIPULATOR_NAME + "_bottom_front"));
		bottomIcons[1] = reg.registerIcon(Utils.getBlockTexture(Strings.TIME_MANIPULATOR_NAME + "_bottom_right"));
		bottomIcons[2] = reg.registerIcon(Utils.getBlockTexture(Strings.TIME_MANIPULATOR_NAME + "_bottom_left"));
		bottomIcons[3] = reg.registerIcon(Utils.getBlockTexture(Strings.TIME_MANIPULATOR_NAME + "_bottom_back"));
		topIcons = new Icon[4];
		topIcons[0] = reg.registerIcon(Utils.getBlockTexture(Strings.TIME_MANIPULATOR_NAME + "_top_front"));
		topIcons[1] = reg.registerIcon(Utils.getBlockTexture(Strings.TIME_MANIPULATOR_NAME + "_top_right"));
		topIcons[2] = reg.registerIcon(Utils.getBlockTexture(Strings.TIME_MANIPULATOR_NAME + "_top_left"));
		topIcons[3] = reg.registerIcon(Utils.getBlockTexture(Strings.TIME_MANIPULATOR_NAME + "_top_back"));

		surface = reg.registerIcon(Utils.getBlockTexture(Strings.TIME_MANIPULATOR_NAME + "_surface"));
		blockIcon = reg.registerIcon("planks_spruce");
	}
}