package ganymedes01.ganysend.items;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.ModIDs;
import ganymedes01.ganysend.lib.ModMaterials;
import ganymedes01.ganysend.lib.Strings;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
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

public class EndiumAxe extends ItemAxe {

	public EndiumAxe() {
		super(ModIDs.ENDIUM_AXE_ID, ModMaterials.ENDIUM_TOOLS);
		setCreativeTab(GanysEnd.endTab);
		setTextureName(Utils.getItemTexture(Strings.ENDIUM_AXE_NAME));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.ENDIUM_AXE_NAME));
	}

	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		if (stack.stackTagCompound == null)
			stack.setTagCompound(new NBTTagCompound());
		stack.stackTagCompound.setBoolean("Tagged", false);
	}

	@Override
	public boolean getIsRepairable(ItemStack item, ItemStack material) {
		return material.getItem() == ModItems.endiumIngot;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (tile != null && tile instanceof IInventory)
			if (stack.getItem() == this) {
				if (stack.stackTagCompound == null)
					stack.setTagCompound(new NBTTagCompound());
				stack.stackTagCompound.setIntArray("Position", new int[] { x, y, z });
				stack.stackTagCompound.setInteger("Dimension", world.provider.dimensionId);
				stack.stackTagCompound.setBoolean("Tagged", true);
				stack.setTagInfo("ench", new NBTTagList());
				player.swingItem();
				return false;
			}
		return false;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		if (stack.stackTagCompound == null)
			return;
		if (stack.stackTagCompound.hasKey("Position") && stack.stackTagCompound.hasKey("Dimension"))
			list.add(Integer.toString(stack.stackTagCompound.getInteger("Dimension")) + " : " + Integer.toString(stack.stackTagCompound.getIntArray("Position")[0]) + ", " + Integer.toString(stack.stackTagCompound.getIntArray("Position")[1]) + ", " +
			Integer.toString(stack.stackTagCompound.getIntArray("Position")[2]));
		else
			list.add(StatCollector.translateToLocal("nottagged"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.uncommon;
	}
}