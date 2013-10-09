package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.GUIsID;
import ganymedes01.ganysend.lib.ModIDs;
import ganymedes01.ganysend.lib.Reference;
import ganymedes01.ganysend.lib.RenderIDs;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.tileentities.TileEntityFilteringHopper;
import net.minecraft.block.BlockHopper;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
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

public class BasicFilteringHopper extends BlockHopper {

	@SideOnly(Side.CLIENT)
	protected Icon blockOutside, blockTop, blockBottom, blockInside;

	public BasicFilteringHopper() {
		this(ModIDs.BASIC_FILTERING_HOPPER_ID);
	}

	public BasicFilteringHopper(int id) {
		super(id);
		setHardness(3.0F);
		setResistance(8.0F);
		setStepSound(soundWoodFootstep);
		setCreativeTab(GanysEnd.endTab);
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.BASIC_FILTERING_HOPPER_NAME));
	}

	@Override
	public int getRenderType() {
		return RenderIDs.filteringHopper;
	}

	@SideOnly(Side.CLIENT)
	public Icon getIconFromString(String string) {
		return string.equals("hopper_outside") ? blockBottom : string.equals("hopper_inside") ? blockInside : null;
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
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return side == 1 ? blockTop : blockOutside;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		blockOutside = reg.registerIcon(Utils.getBlockTexture(Strings.BASIC_FILTERING_HOPPER_NAME, true) + "outside");
		blockTop = reg.registerIcon(Utils.getBlockTexture(Strings.BASIC_FILTERING_HOPPER_NAME, true) + "top");
		registerExtraIcons(reg);
	}

	protected void registerExtraIcons(IconRegister reg) {
		blockBottom = reg.registerIcon(Utils.getBlockTexture(Strings.BASIC_FILTERING_HOPPER_NAME, true) + "bottom");
		blockInside = reg.registerIcon(Utils.getBlockTexture(Strings.BASIC_FILTERING_HOPPER_NAME, true) + "inside");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getItemIconName() {
		return Reference.ITEM_BLOCK_TEXTURE_PATH + Strings.BASIC_FILTERING_HOPPER_NAME;
	}
}