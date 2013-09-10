package ganymedes01.ganysend.items;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.blocks.ModBlocks;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Reference;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.tileentities.TileEntityBlockNewSkull;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class ItemNewSkull extends ItemSkull {

	private static final String[] skullTypes = new String[] { "blaze", "enderman", "pigman", "player", "spider", "caveSpider", "pig", "cow", "mooshroom", "sheep", "wolf", "villager", "chicken", "witch", "zombieVillager" };
	@SideOnly(Side.CLIENT)
	private Icon[] icons;

	public ItemNewSkull(int id) {
		super(id);
		setMaxDamage(0);
		setHasSubtypes(true);
		setCreativeTab(GanysEnd.endTab);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if (side == 0)
			return false;
		else if (!world.getBlockMaterial(x, y, z).isSolid())
			return false;
		else {
			if (side == 1)
				++y;

			if (side == 2)
				--z;

			if (side == 3)
				++z;

			if (side == 4)
				--x;

			if (side == 5)
				++x;

			if (!player.canPlayerEdit(x, y, z, side, stack))
				return false;
			else if (!ModBlocks.blockNewSkull.canPlaceBlockAt(world, x, y, z))
				return false;
			else {
				world.setBlock(x, y, z, ModBlocks.blockNewSkull.blockID, side, 2);
				int i1 = 0;

				if (side == 1)
					i1 = MathHelper.floor_double(player.rotationYaw * 16.0F / 360.0F + 0.5D) & 15;

				TileEntity tileentity = world.getBlockTileEntity(x, y, z);

				if (tileentity != null && tileentity instanceof TileEntityBlockNewSkull) {
					String playerName = "";

					if (stack.hasTagCompound() && stack.getTagCompound().hasKey("SkullOwner"))
						playerName = stack.getTagCompound().getString("SkullOwner");
					((TileEntityBlockNewSkull) tileentity).setSkullType(stack.getItemDamage(), playerName);
					((TileEntityBlockNewSkull) tileentity).setSkullRotation(i1);
				}

				--stack.stackSize;
				return true;
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs tab, List list) {
		for (int j = 0; j < skullTypes.length; ++j)
			list.add(new ItemStack(id, 1, j));
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int meta) {
		if (meta < 0 || meta >= skullTypes.length)
			meta = 0;

		return icons[meta];
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int i = stack.getItemDamage();

		if (i < 0 || i >= skullTypes.length)
			i = 0;

		return "item." + Utils.getUnlocalizedName(Strings.ITEM_NEW_SKULL_NAME) + skullTypes[i] + "Head";
	}

	@Override
	public String getItemDisplayName(ItemStack stack) {
		return stack.getItemDamage() == 4 && stack.hasTagCompound() && stack.getTagCompound().hasKey("SkullOwner") ? StatCollector.translateToLocalFormatted("item.skull.player.name", new Object[] { stack.getTagCompound().getString("SkullOwner") }) : super.getItemDisplayName(stack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		icons = new Icon[skullTypes.length];

		for (int i = 0; i < skullTypes.length; ++i)
			icons[i] = reg.registerIcon(Reference.ITEM_BLOCK_TEXTURE_PATH + skullTypes[i] + "Head");
	}
}
