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

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class EnderTag extends Item {

	public EnderTag() {
		setMaxStackSize(1);
		setCreativeTab(GanysEnd.endTab);
		setTextureName(Utils.getItemTexture(Strings.ENDER_TAG_NAME));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.ENDER_TAG_NAME));
	}

	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		if (stack.stackTagCompound == null)
			stack.setTagCompound(new NBTTagCompound());
		stack.stackTagCompound.setBoolean("Tagged", false);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if (stack.getItem() == this) {
			if (stack.stackTagCompound == null)
				stack.setTagCompound(new NBTTagCompound());
			stack.stackTagCompound.setIntArray("Position", new int[] { x, y, z });
			stack.stackTagCompound.setInteger("Dimension", world.provider.dimensionId);
			stack.stackTagCompound.setBoolean("Tagged", true);
			stack.setTagInfo("ench", new NBTTagList());
			player.swingItem();
			return true;
		}
		return false;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		if (stack.stackTagCompound == null)
			stack.setTagCompound(new NBTTagCompound());
		if (stack.stackTagCompound.hasKey("Position") && stack.stackTagCompound.hasKey("Dimension"))
			list.add(Integer.toString(stack.stackTagCompound.getInteger("Dimension")) + " : " + Integer.toString(stack.stackTagCompound.getIntArray("Position")[0]) + ", " + Integer.toString(stack.stackTagCompound.getIntArray("Position")[1]) + ", " + Integer.toString(stack.stackTagCompound.getIntArray("Position")[2]));
		else
			list.add(StatCollector.translateToLocal("nottagged"));
	}
}