package ganymedes01.ganysend.items;

import java.util.List;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.IConfigurable;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Reference;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class EnderTag extends Item implements IConfigurable {

	public EnderTag() {
		setMaxStackSize(1);
		setCreativeTab(GanysEnd.enableShifters ? GanysEnd.endTab : null);
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.ENDER_TAG_NAME));
	}

	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setBoolean("Tagged", false);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (stack.getItem() == this) {
			if (!stack.hasTagCompound())
				stack.setTagCompound(new NBTTagCompound());
			NBTTagCompound nbt = stack.getTagCompound();
			nbt.setIntArray("Position", new int[] { pos.getX(), pos.getY(), pos.getZ() });
			nbt.setInteger("Dimension", world.provider.getDimensionId());
			nbt.setBoolean("Tagged", true);
			stack.setTagInfo("ench", new NBTTagList());
			player.swingItem();
			return true;
		}
		return false;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt.hasKey("Position") && nbt.hasKey("Dimension")) {
			int[] pos = nbt.getIntArray("Position");
			list.add(Integer.toString(nbt.getInteger("Dimension")) + " : " + pos[0] + ", " + pos[1] + ", " + pos[2]);
		} else
			list.add(StatCollector.translateToLocal("string." + Reference.MOD_ID + ".nottagged"));
	}

	@Override
	public boolean isEnabled() {
		return GanysEnd.enableShifters;
	}
}