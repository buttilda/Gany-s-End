package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.RenderIDs;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.tileentities.TileEntityInventoryBinder;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
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

public class InventoryBinder extends BlockContainer {

	public InventoryBinder() {
		super(Material.rock);
		setHardness(10.0F);
		setBlockName(Utils.getUnlocalizedName(Strings.INVENTORY_BINDER_NAME));
		setCreativeTab(GanysEnd.enableInventoryBinder ? GanysEnd.endTab : null);
		setBlockTextureName(Utils.getBlockTexture(Strings.INVENTORY_BINDER_NAME));
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		if (player != null && player instanceof EntityPlayer) {
			TileEntityInventoryBinder tile = Utils.getTileEntity(world, x, y, z, TileEntityInventoryBinder.class);
			tile.setPlayerName(((EntityPlayer) player).getCommandSenderName());
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityInventoryBinder();
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return RenderIDs.INVENTORY_BINDER;
	}
}