package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.RenderIDs;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.network.PacketHandler;
import ganymedes01.ganysend.network.packet.PacketTileEntity;
import ganymedes01.ganysend.network.packet.PacketTileEntity.TileData;
import ganymedes01.ganysend.tileentities.TileEntityInventoryBinder;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.ByteBufUtils;
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
		setCreativeTab(GanysEnd.endTab);
		setBlockName(Utils.getUnlocalizedName(Strings.INVENTORY_BINDER_NAME));
		setBlockTextureName(Utils.getBlockTexture(Strings.INVENTORY_BINDER_NAME));
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		if (player != null)
			if (player instanceof EntityPlayer) {
				final String name = ((EntityPlayer) player).getCommandSenderName();
				world.setTileEntity(x, y, z, new TileEntityInventoryBinder(name));
				PacketHandler.sendToAll(new PacketTileEntity(x, y, z, new TileData() {

					@Override
					public void writeData(ByteBuf buffer) {
						ByteBufUtils.writeUTF8String(buffer, name);
					}
				}));
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