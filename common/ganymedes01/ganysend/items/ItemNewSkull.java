package ganymedes01.ganysend.items;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.blocks.ModBlocks;
import ganymedes01.ganysend.core.utils.HeadsHelper;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.ModIDs;
import ganymedes01.ganysend.lib.Reference;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.tileentities.TileEntityBlockNewSkull;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class ItemNewSkull extends ItemSkull {

	@SideOnly(Side.CLIENT)
	private Icon[] icons;

	public ItemNewSkull() {
		super(ModIDs.ITEM_NEW_SKULL_ID);
		setMaxDamage(0);
		setHasSubtypes(true);
		setCreativeTab(GanysEnd.endTab);
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.ITEM_NEW_SKULL_NAME));
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if (side == 0)
			return false;
		else if (!world.getBlockMaterial(x, y, z).isSolid())
			return false;
		else {
			if (side == 1)
				y++;
			if (side == 2)
				z--;
			if (side == 3)
				z++;
			if (side == 4)
				x--;
			if (side == 5)
				x++;

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
					world.notifyBlockChange(x, y, z, ModBlocks.blockNewSkull.blockID);
				}
				stack.stackSize--;
				return true;
			}
		}
	}

	public static final int getSkullCount() {
		return HeadsHelper.skullTypes.length;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs tab, List list) {
		boolean TFPresent = Loader.isModLoaded("TwilightForest");
		boolean TEPresent = Loader.isModLoaded("ThermalExpansion");
		for (int i = 0; i < HeadsHelper.skullTypes.length; i++) {
			if (!TFPresent && HeadsHelper.TFskullsIndexes.contains(i))
				continue;
			if (!TEPresent && i == 27)
				continue;
			list.add(new ItemStack(id, 1, i));
		}

	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int meta) {
		if (meta < 0 || meta >= HeadsHelper.skullTypes.length)
			meta = 0;

		return icons[meta];
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int meta = stack.getItemDamage();

		if (meta < 0 || meta >= HeadsHelper.skullTypes.length)
			meta = 0;

		return "item." + Utils.getUnlocalizedName(Strings.ITEM_NEW_SKULL_NAME) + HeadsHelper.skullTypes[meta] + "Head";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		icons = new Icon[HeadsHelper.skullTypes.length];

		for (int i = 0; i < HeadsHelper.skullTypes.length; i++)
			icons[i] = reg.registerIcon(Reference.ITEM_BLOCK_TEXTURE_PATH + HeadsHelper.skullTypes[i] + "Head");
	}

	@Override
	public boolean isValidArmor(ItemStack stack, int armorType, Entity entity) {
		return armorType == 0;
	}
}