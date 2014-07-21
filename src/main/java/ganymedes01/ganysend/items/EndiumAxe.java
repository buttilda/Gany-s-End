package ganymedes01.ganysend.items;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.ModItems;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.IEndiumTool;
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

public class EndiumAxe extends ItemAxe implements IEndiumTool {

	public EndiumAxe() {
		this(ModMaterials.ENDIUM_TOOLS);
		setTextureName(Utils.getItemTexture(Strings.ENDIUM_AXE_NAME));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.ENDIUM_AXE_NAME));
	}

	protected EndiumAxe(ToolMaterial material) {
		super(material);
		setHarvestLevel("axe", 3);
		setCreativeTab(GanysEnd.endTab);
	}

	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		if (stack.stackTagCompound == null)
			stack.setTagCompound(new NBTTagCompound());
		stack.stackTagCompound.setBoolean("Tagged", false);
	}

	@Override
	public boolean getIsRepairable(ItemStack item, ItemStack material) {
		return material.getItem() == ModItems.endiumIngot && material.getItemDamage() == 0;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		IInventory tile = Utils.getTileEntity(world, x, y, z, IInventory.class);
		if (tile != null)
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		if (stack.stackTagCompound == null)
			stack.setTagCompound(new NBTTagCompound());
		if (stack.stackTagCompound.hasKey("Position") && stack.stackTagCompound.hasKey("Dimension"))
			list.add(Integer.toString(stack.stackTagCompound.getInteger("Dimension")) + " : " + Integer.toString(stack.stackTagCompound.getIntArray("Position")[0]) + ", " + Integer.toString(stack.stackTagCompound.getIntArray("Position")[1]) + ", " + Integer.toString(stack.stackTagCompound.getIntArray("Position")[2]));
		else
			list.add(StatCollector.translateToLocal("nottagged"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.uncommon;
	}
}