package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.items.ModItems;
import ganymedes01.ganysend.lib.ModIDs;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.tileentities.TileEntityEnergyPortal;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class EnergyPortal extends BlockContainer {

	protected EnergyPortal() {
		super(ModIDs.ENERGY_PORTAL_ID, Material.rock);
		setCreativeTab(GanysEnd.endTab);
		setTextureName(Utils.getBlockTexture(Strings.ENERGY_PORTAL_NAME));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.ENERGY_PORTAL_NAME));
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int id, float hitX, float hitY, float hitZ) {
		if (player.inventory.getCurrentItem() == null)
			return false;
		if (player.inventory.getCurrentItem().getItem() == ModItems.enderTag)
			if (player.inventory.getCurrentItem().getTagCompound().getBoolean("Tagged"))
				if (world.getBlockTileEntity(x, y, z) instanceof TileEntityEnergyPortal) {
					TileEntityEnergyPortal tile = (TileEntityEnergyPortal) world.getBlockTileEntity(x, y, z);

					int telX = player.inventory.getCurrentItem().getTagCompound().getIntArray("Position")[0];
					int telY = player.inventory.getCurrentItem().getTagCompound().getIntArray("Position")[1];
					int telZ = player.inventory.getCurrentItem().getTagCompound().getIntArray("Position")[2];
					int telDim = player.inventory.getCurrentItem().getTagCompound().getInteger("Dimension");

					if (telX == x && telY == y && telZ == z)
						return false;

					tile.setReceiverCoord(telX, telY, telZ, telDim);
					world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "random.click", 0.3F, 0.6F);
					world.notifyBlocksOfNeighborChange(x, y, z, id);
					return true;
				}
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		Block.enderChest.randomDisplayTick(world, x, y, z, rand);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityEnergyPortal();
	}
}