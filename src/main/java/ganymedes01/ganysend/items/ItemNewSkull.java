package ganymedes01.ganysend.items;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.blocks.ModBlocks;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.SkullTypes;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.tileentities.TileEntityBlockNewSkull;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.mojang.authlib.GameProfile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class ItemNewSkull extends ItemSkull {

	public ItemNewSkull() {
		setMaxDamage(0);
		setHasSubtypes(true);
		setCreativeTab(GanysEnd.endTab);
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.ITEM_NEW_SKULL_NAME));
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if (side == 0)
			return false;
		else if (!world.getBlock(x, y, z).getMaterial().isSolid())
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
				world.setBlock(x, y, z, ModBlocks.blockNewSkull, side, 2);
				int angle = 0;

				if (side == 1)
					angle = MathHelper.floor_double(player.rotationYaw * 16.0F / 360.0F + 0.5D) & 15;

				TileEntityBlockNewSkull tile = Utils.getTileEntity(world, x, y, z, TileEntityBlockNewSkull.class);

				if (tile != null) {
					GameProfile playerName = null;

					if (stack.hasTagCompound() && stack.getTagCompound().hasKey("SkullOwner"))
						playerName = NBTUtil.func_152459_a(stack.getTagCompound().getCompoundTag("Owner"));
					tile.setType(stack.getItemDamage(), playerName);
					tile.func_145903_a(angle);
					world.notifyBlockChange(x, y, z, ModBlocks.blockNewSkull);
				}
				stack.stackSize--;
				return true;
			}
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		SkullTypes type = SkullTypes.values()[stack.getItemDamage() >= SkullTypes.values().length ? 0 : stack.getItemDamage()];
		return super.getUnlocalizedName() + type.name() + "Head";
	}

	@Override
	public boolean isValidArmor(ItemStack stack, int armorType, Entity entity) {
		return armorType == 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getSubItems(Item id, CreativeTabs tab, List list) {
		for (SkullTypes skull : SkullTypes.values())
			if (skull.canShow())
				list.add(new ItemStack(id, 1, skull.ordinal()));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		return itemIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
	}
}