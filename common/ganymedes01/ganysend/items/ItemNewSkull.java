package ganymedes01.ganysend.items;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.blocks.ModBlocks;
import ganymedes01.ganysend.lib.ModIDs;
import ganymedes01.ganysend.lib.SkullTypes;
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
		super(ModIDs.ITEM_NEW_SKULL_ID);
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
				int angle = 0;

				if (side == 1)
					angle = MathHelper.floor_double(player.rotationYaw * 16.0F / 360.0F + 0.5D) & 15;

				TileEntity tileentity = world.getBlockTileEntity(x, y, z);

				if (tileentity != null && tileentity instanceof TileEntityBlockNewSkull) {
					String playerName = "";

					if (stack.hasTagCompound() && stack.getTagCompound().hasKey("SkullOwner"))
						playerName = stack.getTagCompound().getString("SkullOwner");
					((TileEntityBlockNewSkull) tileentity).setSkullType(stack.getItemDamage(), playerName);
					((TileEntityBlockNewSkull) tileentity).setSkullRotation(angle);
					world.notifyBlockChange(x, y, z, ModBlocks.blockNewSkull.blockID);
				}
				stack.stackSize--;
				return true;
			}
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int meta = stack.getItemDamage();
		return SkullTypes.values()[meta >= SkullTypes.values().length ? 0 : meta].getUnlocalisedName();
	}

	@Override
	public boolean isValidArmor(ItemStack stack, int armorType, Entity entity) {
		return armorType == 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs tab, List list) {
		for (SkullTypes skull : SkullTypes.values())
			if (skull.canShow())
				list.add(new ItemStack(id, 1, skull.ordinal()));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int meta) {
		return itemIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
	}
}