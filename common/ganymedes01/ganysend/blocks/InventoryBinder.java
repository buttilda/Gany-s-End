package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.ModIDs;
import ganymedes01.ganysend.lib.RenderIDs;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.network.PacketTypeHandler;
import ganymedes01.ganysend.network.packet.PacketInventoryBinder;
import ganymedes01.ganysend.tileentities.TileEntityInventoryBinder;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
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

public class InventoryBinder extends BlockContainer {

	@SideOnly(Side.CLIENT)
	private Icon inside;

	public InventoryBinder() {
		super(ModIDs.INVENTORY_BINDER_ID, Material.rock);
		setHardness(10.0F);
		setCreativeTab(GanysEnd.endTab);
		setTextureName(Utils.getBlockTexture(Strings.INVENTORY_BINDER_NAME));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.INVENTORY_BINDER_NAME));
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		if (player != null)
			if (player instanceof EntityPlayer) {
				world.setBlockTileEntity(x, y, z, new TileEntityInventoryBinder(((EntityPlayer) player).getCommandSenderName()));
				PacketDispatcher.sendPacketToAllPlayers(PacketTypeHandler.populatePacket(new PacketInventoryBinder(x, y, z, ((EntityPlayer) player).getCommandSenderName())));
			}
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
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

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		if (meta == 1)
			return inside;
		else
			return super.getIcon(side, meta);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		super.registerIcons(reg);
		inside = reg.registerIcon(Utils.getBlockTexture(Strings.INVENTORY_BINDER_NAME) + "_inside");
	}
}