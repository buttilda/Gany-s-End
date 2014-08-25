package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.InventoryUtils;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.GUIsID;
import ganymedes01.ganysend.lib.RenderIDs;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.tileentities.TileEntityFilteringHopper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHopper;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class BasicFilteringHopper extends BlockHopper {

	@SideOnly(Side.CLIENT)
	protected IIcon blockOutside, blockTop, blockBottom, blockInside;

	public BasicFilteringHopper() {
		setHardness(3.0F);
		setResistance(8.0F);
		setStepSound(soundTypeWood);
		setCreativeTab(GanysEnd.endTab);
		setBlockName(Utils.getUnlocalizedName(Strings.BASIC_FILTERING_HOPPER_NAME));
	}

	@Override
	public int getRenderType() {
		return RenderIDs.FILTERING_HOPPER;
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIconFromString(String string) {
		return string.equals("hopper_outside") ? blockBottom : string.equals("hopper_inside") ? blockInside : null;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		TileEntityFilteringHopper tile = new TileEntityFilteringHopper();
		tile.setBasic();
		return tile;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {

	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		if (player.isSneaking())
			return false;
		else {
			TileEntityFilteringHopper tile = Utils.getTileEntity(world, x, y, z, TileEntityFilteringHopper.class);
			if (tile != null)
				player.openGui(GanysEnd.instance, GUIsID.BASIC_FILTERING_HOPPER, world, x, y, z);
			return true;
		}
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		InventoryUtils.dropInventoryContents(world.getTileEntity(x, y, z));
		world.func_147453_f(x, y, z, block);
		world.removeTileEntity(x, y, z);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return side == 1 ? blockTop : blockOutside;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		blockOutside = reg.registerIcon(Utils.getBlockTexture(Strings.BASIC_FILTERING_HOPPER_NAME) + "_outside");
		blockTop = reg.registerIcon(Utils.getBlockTexture(Strings.BASIC_FILTERING_HOPPER_NAME) + "_top");
		registerExtraIcons(reg);
	}

	protected void registerExtraIcons(IIconRegister reg) {
		blockBottom = reg.registerIcon(Utils.getBlockTexture(Strings.BASIC_FILTERING_HOPPER_NAME) + "_bottom");
		blockInside = reg.registerIcon(Utils.getBlockTexture(Strings.BASIC_FILTERING_HOPPER_NAME) + "_inside");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getItemIconName() {
		return null;
	}
}