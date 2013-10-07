package ganymedes01.ganysend.items;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
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

public class EnderTag extends Item {

	public EnderTag(int id) {
		super(id);
		setMaxStackSize(1);
		setTextureName("paper");
		setCreativeTab(GanysEnd.endTab);
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.ENDER_TAG_NAME));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int meta) {
		return Utils.getColour(26, 75, 75);
	}

	@Override
	public void onCreated(ItemStack item, World world, EntityPlayer player) {
		if (item.stackTagCompound == null)
			item.setTagCompound(new NBTTagCompound());
		item.stackTagCompound.setBoolean("Tagged", false);
	}

	@Override
	public boolean onItemUse(ItemStack item, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if (item.getItem() == this) {
			if (item.stackTagCompound == null)
				item.setTagCompound(new NBTTagCompound());
			int[] coords = { x, y, z };
			int dimension = world.provider.dimensionId;
			item.stackTagCompound.setIntArray("Position", coords);
			item.stackTagCompound.setInteger("Dimension", dimension);
			item.stackTagCompound.setBoolean("Tagged", true);
			item.setTagInfo("ench", new NBTTagList());
			return true;
		}
		return false;
	}

	@Override
	public void addInformation(ItemStack item, EntityPlayer player, List list, boolean bool) {
		if (item.stackTagCompound == null)
			item.setTagCompound(new NBTTagCompound());
		if (item.stackTagCompound.hasKey("Position") && item.stackTagCompound.hasKey("Dimension"))
			list.add(Integer.toString(item.stackTagCompound.getInteger("Dimension")) + " : " + Integer.toString(item.stackTagCompound.getIntArray("Position")[0]) + ", " + Integer.toString(item.stackTagCompound.getIntArray("Position")[1]) + ", " +
			Integer.toString(item.stackTagCompound.getIntArray("Position")[2]));
		else
			list.add(StatCollector.translateToLocal("nottagged"));
	}
}
