package ganymedes01.ganysend.items;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.ModBlocks;
import ganymedes01.ganysend.ModItems;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.SkullTypes;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.tileentities.TileEntityBlockSkull;

import java.util.List;
import java.util.UUID;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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

public class Skull extends ItemSkull {

	public Skull() {
		setMaxDamage(0);
		setHasSubtypes(true);
		setCreativeTab(GanysEnd.isHeadcrumbsLoaded || !GanysEnd.enableSkulls ? null : GanysEnd.endTab);
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.ITEM_NEW_SKULL_NAME));
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
			else if (!world.isRemote) {
				world.setBlock(x, y, z, ModBlocks.blockNewSkull, side, 2);

				int angle = 0;
				if (side == 1)
					angle = MathHelper.floor_double(player.rotationYaw * 16.0F / 360.0F + 0.5D) & 15;

				TileEntityBlockSkull tile = Utils.getTileEntity(world, x, y, z, TileEntityBlockSkull.class);

				if (tile != null) {
					GameProfile profile = null;

					if (stack.hasTagCompound()) {
						NBTTagCompound nbt = stack.getTagCompound();
						if (nbt.hasKey("SkullOwner", 10))
							profile = NBTUtil.func_152459_a(nbt.getCompoundTag("SkullOwner"));
						else if (nbt.hasKey("SkullOwner", 8))
							profile = new GameProfile(null, nbt.getString("SkullOwner"));
					}

					tile.setType(stack.getItemDamage(), profile);
					tile.func_145903_a(angle);
					world.notifyBlockChange(x, y, z, ModBlocks.blockNewSkull);
				}

				stack.stackSize--;
			}
			return true;
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
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (SkullTypes skull : SkullTypes.values())
			if (skull.canShow())
				list.add(new ItemStack(item, 1, skull.ordinal()));
	}

	public static ItemStack createHeadFor(String username) {
		return createHeadFor(new GameProfile(UUID.nameUUIDFromBytes(username.getBytes()), username));
	}

	public static ItemStack createHeadFor(EntityPlayer player) {
		return createHeadFor(player.getGameProfile());
	}

	public static ItemStack createHeadFor(GameProfile profile) {
		ItemStack stack = new ItemStack(ModItems.skull, 1, SkullTypes.player.ordinal());
		stack.setTagCompound(new NBTTagCompound());
		NBTTagCompound profileData = new NBTTagCompound();
		NBTUtil.func_152460_a(profileData, profile);
		stack.getTagCompound().setTag("SkullOwner", profileData);

		return stack;
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