package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.GUIsID;
import ganymedes01.ganysend.lib.ModIDs;
import ganymedes01.ganysend.lib.Reference;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.tileentities.TileEntityAdvancedFilteringHopper;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
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

public class AdvancedFilteringHopper extends BasicFilteringHopper {

	public AdvancedFilteringHopper() {
		super(ModIDs.ADVANCED_FILTERING_HOPPER_ID);
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.ADVANCED_FILTERING_HOPPER_NAME));
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		if (player.isSneaking())
			return false;
		else {
			TileEntityAdvancedFilteringHopper tileHopper = (TileEntityAdvancedFilteringHopper) world.getBlockTileEntity(x, y, z);
			if (tileHopper != null)
				player.openGui(GanysEnd.instance, GUIsID.ADVANCED_FILTERING_HOPPER, world, x, y, z);
			return true;
		}
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
		TileEntityAdvancedFilteringHopper tile = (TileEntityAdvancedFilteringHopper) world.getBlockTileEntity(x, y, z);
		if (tile != null) {
			for (int j1 = 0; j1 < tile.getSizeInventory() + tile.getFilterInventorySize(); ++j1) {
				ItemStack stack = tile.getStackInSlot(j1);
				if (stack != null)
					Utils.dropStack(world, x, y, z, stack);
			}
			world.func_96440_m(x, y, z, par5);
		}
		world.removeBlockTileEntity(x, y, z);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		TileEntityAdvancedFilteringHopper tile = new TileEntityAdvancedFilteringHopper();
		tile.setBasic();
		return tile;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return side == 1 ? blockTop : blockOutside;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		blockOutside = reg.registerIcon(Utils.getBlockTexture(Strings.ADVANCED_FILTERING_HOPPER_NAME, false));
		blockTop = reg.registerIcon(Utils.getBlockTexture(Strings.BASIC_FILTERING_HOPPER_NAME, true) + "top");
		registerExtraIcons(reg);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getItemIconName() {
		return Reference.ITEM_BLOCK_TEXTURE_PATH + Strings.ADVANCED_FILTERING_HOPPER_NAME;
	}
}
