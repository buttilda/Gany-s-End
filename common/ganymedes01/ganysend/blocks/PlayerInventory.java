package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.tileentities.TileEntityPlayerInventory;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
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

public class PlayerInventory extends BlockContainer {

	public PlayerInventory(int id) {
		super(id, Material.rock);
		setHardness(10.0F);
		setCreativeTab(GanysEnd.endTab);
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.PLAYER_INVENTORY_NAME));
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		if (player != null)
			if (player instanceof EntityPlayer) {
				world.setBlockTileEntity(x, y, z, new TileEntityPlayerInventory(((EntityPlayer) player).getCommandSenderName()));
				GanysEnd.proxy.handlePlayerInventoryPacket(x, y, z, ((EntityPlayer) player).getCommandSenderName());
			}
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityPlayerInventory();
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
		return -1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		blockIcon = ModBlocks.endstoneBrick.getBlockTextureFromSide(0);
	}
}
