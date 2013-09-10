package ganymedes01.ganysend.items;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.ModMaterials;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class EndiumArmour extends ItemArmor {

	private final int type;
	protected final int maxCoolDown;

	public EndiumArmour(int id, int type) {
		super(id, ModMaterials.ENDIUM_ARMOUR, 0, type);
		this.type = type;
		maxCoolDown = 10;
		setMaxStackSize(1);
		setCreativeTab(GanysEnd.endTab);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.epic;
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}

	@Override
	public boolean getIsRepairable(ItemStack item, ItemStack material) {
		return material.getItem() == ModItems.endiumIngot;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer) {
		stack.setTagInfo("ench", new NBTTagList());
		switch (type) {
			case 0:
				return Utils.getArmourTexture(ModMaterials.ENDIUM_ARMOUR.name(), 1);
			case 1:
				return Utils.getArmourTexture(ModMaterials.ENDIUM_ARMOUR.name(), 1);
			case 2:
				return Utils.getArmourTexture(ModMaterials.ENDIUM_ARMOUR.name(), 2);
			case 3:
				return Utils.getArmourTexture(ModMaterials.ENDIUM_ARMOUR.name(), 1);
			default:
				return null;
		}
	}
}
